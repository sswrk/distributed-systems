package chat.server;

import chat.AddressPort;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class ServerClientTcp implements Runnable{

    private final Socket socket;
    private final String name;
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;
    private final Server server;

    public ServerClientTcp(Socket socket, String name, DataInputStream inputStream, DataOutputStream outputStream, Server server){
        this.socket = socket;
        this.name = name;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.server = server;
    }


    @Override
    public void run() {
        while(true){
            try {
                System.out.println("Oczekiwanie na wiadomosc");
                String messageFromClient = inputStream.readUTF();
                System.out.println(name + ": " + messageFromClient);

                Map<AddressPort, ServerClientTcp> serverClients = server.getClients();
                for(Map.Entry<AddressPort, ServerClientTcp> client : serverClients.entrySet()){
                    if(client.getValue()!=this){
                        client.getValue().getOutputStream().writeUTF(name + ": " + messageFromClient);
                    }
                }

            } catch (IOException e) {
                disconnect();
                break;
            }
        }
    }

    private void disconnect(){
        try {
            socket.close();
            inputStream.close();
            outputStream.close();
            //TODO: remove client from clients map in Server class
            System.out.println(name + " has disconnected");
        } catch (IOException e){
            System.out.println("Problem while disconnecting user " + name);
        }
    }

    public String getName(){
        return name;
    }

    private DataOutputStream getOutputStream(){
        return outputStream;
    }
}
