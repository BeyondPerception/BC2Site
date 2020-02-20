package ml.dent.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NetworkServer {

	private final int port;

	private final ConnectionManager connectionManager;

//	private final CommandProcessor commandProcessor;

	public NetworkServer(int bindPort) {
		port = bindPort;
		connectionManager = new ConnectionManager();
//		commandProcessor = new CommandProcessor();
	}

	/**
	 * 
	 * Synchronous method that binds the server to the port.
	 * 
	 * @throws InterruptedException if the thread is interrupted while the server is
	 *                              trying to bind.
	 */
	public void bind() throws InterruptedException {
		EventLoopGroup masterGroup = new NioEventLoopGroup();
		EventLoopGroup slaveGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap boot = new ServerBootstrap();
			boot.group(masterGroup, slaveGroup).channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new SiteHandler());
						}

					}).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);

			System.out.println("Starting server on port [" + port + "]...");
			ChannelFuture future = boot.bind(port).sync();
			System.out.println("Server started. Waiting for connections");

			future.channel().closeFuture().sync();
		} finally {
			slaveGroup.shutdownGracefully();
			masterGroup.shutdownGracefully();
		}
	}

	class SiteHandler extends ChannelInboundHandlerAdapter {

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			connectionManager.put(new Connection(ctx.channel()));
			super.channelActive(ctx);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			ByteBuf buf = (ByteBuf) msg;
			byte[] bytes = new byte[buf.readableBytes()];
			buf.readBytes(bytes);

//			commandProcessor.addBytes(bytes, connectionManager.get(ctx.channel().remoteAddress()));
//			commandProcessor.processBytes();

			super.channelRead(ctx, msg);
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			System.err.println(cause);
			ctx.close();
			super.exceptionCaught(ctx, cause);
		}
	}
}
