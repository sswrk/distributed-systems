package chat.client;

import chat.AddressPort;

import java.io.IOException;

public class ClientApp {

    public static void main(String[] args) throws IOException, InterruptedException {

        if(args.length!=7){
            System.err.println("Użycie parametrów: <nick> <serveraddress> <serverpport> <clientaddress> <clientport> <multicastaddress> <multicastport>");
        }

        String name = args[0];
        String serverAddress = args[1];
        int serverPort = Integer.parseInt(args[2]);
        String clientAddress = args[3];
        int clientPort = Integer.parseInt(args[4]);
        String multicastAddress = args[5];
        int multicastPort = Integer.parseInt(args[6]);

        AddressPort serverAddressPort = new AddressPort(serverAddress, serverPort);
        AddressPort clientAddressPort = new AddressPort(clientAddress, clientPort);
        AddressPort multicastAddressPort = new AddressPort(multicastAddress, multicastPort);

        Client client = new Client(name, serverAddressPort, clientAddressPort, multicastAddressPort);
        client.start();

    }

}
