package chat.client;

import chat.SocketInfo;

import java.io.IOException;

public class ClientApp {

    public static void main(String[] args) throws IOException, InterruptedException {

        if(args.length!=7){
            System.err.println("Użycie parametrów: <nick> <serveraddress> <serverpport> <clientaddress> <clientport> <multicastaddress> <multicastport>");
        }

        String nickname = args[0];
        String serverAddress = args[1];
        int serverPort = Integer.parseInt(args[2]);
        String clientAddress = args[3];
        int clientPort = Integer.parseInt(args[4]);
        String multicastAddress = args[5];
        int multicastPort = Integer.parseInt(args[6]);

        SocketInfo serverSocketInfo = new SocketInfo(serverAddress, serverPort);
        SocketInfo clientSocketInfo = new SocketInfo(clientAddress, clientPort);
        SocketInfo multicastSocketInfo = new SocketInfo(multicastAddress, multicastPort);

        Client client = new Client(nickname, serverSocketInfo, clientSocketInfo, multicastSocketInfo);
        client.start();

    }

}
