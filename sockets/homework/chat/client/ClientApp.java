package chat.client;

import chat.AddressPort;

import java.io.IOException;

public class ClientApp {

    public static void main(String[] args) throws IOException, InterruptedException {

        AddressPort serverAddressPort = new AddressPort("localhost", 12345);
        AddressPort clientAddressPort = new AddressPort("localhost", 9007);
        AddressPort multicastAddressPort = new AddressPort("239.255.255.255", 1234);

        Client client = new Client(serverAddressPort, clientAddressPort, multicastAddressPort);
        client.start();

    }

}
