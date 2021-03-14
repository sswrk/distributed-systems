package chat;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

public class SocketInfo {

    private final InetAddress address;
    private final int port;

    public SocketInfo(String address, int port) throws UnknownHostException {
        this.address = InetAddress.getByName(address);
        this.port = port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SocketInfo that = (SocketInfo) o;
        return port == that.port && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, port);
    }
}
