package ml.dent.net;

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public class ConnectionManager {
	private ConcurrentHashMap<SocketAddress, Connection> channels;

	public ConnectionManager() {
		channels = new ConcurrentHashMap<>();
	}

	public Connection put(SocketAddress address, Connection connection) {
		return channels.put(address, connection);
	}

	public Connection put(Connection connection) {
		return put(connection.remoteAddress(), connection);
	}

	public Connection get(SocketAddress address) {
		return channels.get(address);
	}

	public Connection remove(SocketAddress address) {
		return channels.remove(address);
	}

	public Connection remove(Connection connection) {
		return channels.remove(connection.remoteAddress());
	}

	public void writeToAll(Object msg) {
		for (Connection connection : channels.values()) {
			connection.writeAndFlush(msg);
		}
	}

	public void writeToAll(String msg) {
		writeToAll(Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
	}
}