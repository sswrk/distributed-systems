package chat.client;

import chat.AddressPort;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Client {

    private final AddressPort serverAddressPort;
    private final AddressPort clientAddressPort;

    public Client(AddressPort serverAddressPort, AddressPort clientAddressPort){
        this.serverAddressPort = serverAddressPort;
        this.clientAddressPort = clientAddressPort;
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
                        //TODO: add multicast support
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
        writeMessagesThread.start();

        readMessagesThread.join();
        readMessagesThreadUdp.join();
        writeMessagesThread.join();

        System.out.println("KONIEC PRACY KLIENTA");
    }

}
