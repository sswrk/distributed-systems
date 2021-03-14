package chat.server;

import java.io.IOException;

public class ServerApp {

    public static void main(String[] args) throws IOException {

        if(args.length != 1){
            System.err.println("Użyj parametrów: <serverport>");
        }

        int serverPort = Integer.parseInt(args[0]);
        Server server = new Server(serverPort);
        server.start();

    }
}
