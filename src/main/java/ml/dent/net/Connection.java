package ml.dent.net;

import java.net.SocketAddress;

import io.netty.channel.Channel;

public class Connection {
	private Channel channel;
	private String closeReason;

	public Connection(Channel channel) {
		this.channel = channel;
		closeReason = "Closed by remote host";
	}

	public void writeAndFlush(Object o) {
		channel.writeAndFlush(o);
	}

	public Channel getChannel() {
		return channel;
	}

	public SocketAddress remoteAddress() {
		return channel.remoteAddress();
	}

	public void close() {
		close("No Reason");
	}

	public void close(String reason) {
		channel.close();
		closeReason = reason;
	}

	public String getCloseReason() {
		return closeReason;
	}

	@Override
	public int hashCode() {
		return remoteAddress().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Connection) {
			Connection o = (Connection) obj;
			return remoteAddress().equals(o.remoteAddress());
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return remoteAddress().toString();
	}
}