package chat.server;

import java.io.IOException;

public class ServerApp {

    public static void main(String[] args) throws IOException {

        int serverPort = 12345;
        Server server = new Server(serverPort);
        server.start();

    }
}
