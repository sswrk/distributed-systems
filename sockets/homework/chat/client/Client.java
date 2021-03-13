package chat.client;

import chat.AddressPort;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

public class Client {

    private final AddressPort serverAddressPort;
    private final AddressPort clientAddressPort;
    private final AddressPort multicastAddressPort;

    public Client(AddressPort serverAddressPort, AddressPort clientAddressPort, AddressPort multicastAdressPort){
        this.serverAddressPort = serverAddressPort;
        this.clientAddressPort = clientAddressPort;
        this.multicastAddressPort = multicastAdressPort;
    }

    public void start() throws IOException, InterruptedException {

        Socket serverSocket = new Socket(
                serverAddressPort.getAddress(),
                serverAddressPort.getPort(),
                clientAddressPort.getAddress(),
                clientAddressPort.getPort()
        );

        DataInputStream inputStream = new DataInputStream(serverSocket.getInputStream());
        DataOutputStream outputStream = new DataOutputStream(serverSocket.getOutputStream());

        DatagramSocket serverSocketUdp = new DatagramSocket(clientAddressPort.getPort());

        MulticastSocket multicastSocket = new MulticastSocket(multicastAddressPort.getPort());
        SocketAddress socketAddress = new InetSocketAddress(
                multicastAddressPort.getAddress(),
                multicastAddressPort.getPort()
        );
        NetworkInterface networkInterface = NetworkInterface.getByInetAddress(multicastAddressPort.getAddress());
        multicastSocket.joinGroup(socketAddress, networkInterface);

        Thread readMessagesThread = new Thread(() -> {
            while (true) {
                try {
                    String msg = inputStream.readUTF();
                    System.out.println(msg);
                } catch (IOException e) {
                    System.out.println("UWAGA: Utracono połączenie z serwerem (TCP)");
                    break;
                }
            }
        });

        Thread readMessagesThreadUdp = new Thread(() -> {
            byte[] receiveBuffer = new byte[1024];
            while (true) {
                try {
                    Arrays.fill(receiveBuffer, (byte) 0);
                    DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    serverSocketUdp.receive(receivePacket);
                    String msg = new String(receivePacket.getData());
                    System.out.println(msg);
                } catch (IOException e){
                    System.out.println("UWAGA: Utracono połączenie z serwerem (UDP)");
                    break;
                }
            }
        });

        Thread readMessagesThreadMulticast = new Thread(() -> {
            byte[] receiveBuffer = new byte[1024];
            while (true) {
                try {
                    Arrays.fill(receiveBuffer, (byte) 0);
                    DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    multicastSocket.receive(receivePacket);
                    String msg = new String(receivePacket.getData());
                    System.out.println(msg);
                } catch (IOException e){
                    System.out.println("UWAGA: Utracono połączenie z kanałem multicast");
                    break;
                }
            }
        });

        Thread writeMessagesThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                try {
                    String msg = scanner.nextLine();
                    if(msg.startsWith("U ") && msg.length()>2){
                        byte[] msgUdp = msg.substring(2).getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(
                                msgUdp,
                                msgUdp.length,
                                serverAddressPort.getAddress(),
                                serverAddressPort.getPort()
                        );
                        serverSocketUdp.send(sendPacket);
                    }
                    else if(msg.startsWith("M ") && msg.length()>2){
                        byte[] msgMulticast = msg.substring(2).getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(
                                msgMulticast,
                                msgMulticast.length,
                                socketAddress
                        );
                        multicastSocket.leaveGroup(socketAddress, networkInterface);
                        multicastSocket.send(sendPacket);
                        multicastSocket.joinGroup(socketAddress, networkInterface);
                    }
                    else {
                        outputStream.writeUTF(msg);
                    }
                } catch (IOException e) {
                    System.out.println("UWAGA: Wiadomość nie została wysłana");
                }
            }
        });

        readMessagesThread.start();
        readMessagesThreadUdp.start();
        readMessagesThreadMulticast.start();
        writeMessagesThread.start();

        readMessagesThread.join();
        readMessagesThreadUdp.join();
        readMessagesThreadMulticast.join();
        writeMessagesThread.join();

        System.out.println("KONIEC PRACY KLIENTA");
    }

}