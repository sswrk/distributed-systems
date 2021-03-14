package chat.server;

import chat.AddressPort;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;

public class Server {

    private final int portNumber;
    private ServerSocket serverSocketTcp;
    private DatagramSocket serverSocketUdp;

    private Map<AddressPort, ServerClientTcp> clients;

    public Server(int portNumber){
        this.portNumber = portNumber;
    }

    public void start() throws IOException {

        if(serverSocketTcp != null){
            throw new RuntimeException("Server is already running!");
        }

        serverSocketTcp = new ServerSocket(portNumber);
        serverSocketUdp = new DatagramSocket(portNumber);

        clients = new Hashtable<>();

        run();

    }

    public void run() throws IOException {

        Thread udpThread = new Thread(this::receiveUdp);
        udpThread.start();

        while(true) {
            Socket clientSocket = serverSocketTcp.accept();

            System.out.println("Podlaczenie klienta " + clients.size() + " ...");

            ServerClientTcp clientTcp = new ServerClientTcp(
                    clientSocket,
                    "C" + clients.size(),
                    new DataInputStream(clientSocket.getInputStream()),
                    new DataOutputStream(clientSocket.getOutputStream()),
                    this
            );

            clients.put(new AddressPort(clientSocket.getInetAddress().getHostAddress(), clientSocket.getPort()), clientTcp);

            Thread clientThread = new Thread(clientTcp);
            clientThread.start();

            System.out.println("... Klient " + (clients.size() - 1) + " podlaczony!");
        }

    }

    public Map<AddressPort, ServerClientTcp> getClients(){
        return clients;
    }

    private void receiveUdp(){
        byte[] receiveBuffer = new byte[1024];
        try {
            while (true) {
                Arrays.fill(receiveBuffer, (byte) 0);
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocketUdp.receive(receivePacket);
                String msg = new String(receivePacket.getData());
                String username = clients.get(new AddressPort(receivePacket.getAddress().getHostName(), receivePacket.getPort())).getId();

                msg = username + ": " + msg;
                System.out.println(msg);

                for(Map.Entry<AddressPort, ServerClientTcp> client : clients.entrySet()){
                    if(client.getKey().getPort()!=receivePacket.getPort()){
                        byte[] message = msg.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(
                                message,
                                message.length,
                                client.getKey().getAddress(),
                                client.getKey().getPort()
                        );
                        serverSocketUdp.send(sendPacket);
                    }
                }

            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
