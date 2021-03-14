package chat.client;

import chat.SocketInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

public class Client {

    private final String nickname;

    private final SocketInfo serverSocketInfo;
    private final SocketInfo clientSocketInfo;
    private final SocketInfo multicastSocketInfo;

    private Socket socketTcp;
    private DataInputStream inputStreamTcp;
    private DataOutputStream outputStreamTcp;

    private DatagramSocket socketUdp;

    private MulticastSocket socketMulticast;
    private SocketAddress socketAddressMulticast;
    private NetworkInterface networkInterfaceMulticast;

    public Client(String nickname, SocketInfo serverSocketInfo, SocketInfo clientSocketInfo, SocketInfo multicastSocketInfo) throws IOException {
        this.nickname = nickname;

        this.serverSocketInfo = serverSocketInfo;
        this.clientSocketInfo = clientSocketInfo;
        this.multicastSocketInfo = multicastSocketInfo;

        setTcpSocket();
        setUdpSocket();
        setMulticastSocket();
    }

    public void start() throws IOException, InterruptedException {

        Thread readMessagesThreadTcp = new Thread(() -> {
            while (true) {
                try {
                    String msg = inputStreamTcp.readUTF();
                    System.out.println(msg);
                } catch (IOException e) {
                    System.err.println("UWAGA: Utracono połączenie z serwerem (TCP)");
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
                    socketUdp.receive(receivePacket);
                    String msg = new String(receivePacket.getData());
                    System.out.println(msg);
                } catch (IOException e){
                    System.err.println("UWAGA: Utracono połączenie z serwerem (UDP)");
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
                    socketMulticast.receive(receivePacket);
                    String msg = new String(receivePacket.getData());
                    System.out.println(msg);
                } catch (IOException e) {
                    System.err.println("UWAGA: Utracono połączenie z kanałem multicast");
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
                        byte[] msgUdp = (nickname + ": " + msg.substring(2)).getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(
                                msgUdp,
                                msgUdp.length,
                                serverSocketInfo.getAddress(),
                                serverSocketInfo.getPort()
                        );
                        socketUdp.send(sendPacket);
                    }
                    else if(msg.startsWith("M ") && msg.length()>2){
                        byte[] msgMulticast = (nickname + ": " + msg.substring(2)).getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(
                                msgMulticast,
                                msgMulticast.length,
                                socketAddressMulticast
                        );
                        socketMulticast.leaveGroup(socketAddressMulticast, networkInterfaceMulticast);
                        socketMulticast.send(sendPacket);
                        socketMulticast.joinGroup(socketAddressMulticast, networkInterfaceMulticast);
                    }
                    else {
                        outputStreamTcp.writeUTF(nickname + ": " + msg);
                    }
                } catch (IOException e) {
                    System.err.println("UWAGA: Wiadomość nie została wysłana");
                }
            }
        });

        readMessagesThreadTcp.start();
        readMessagesThreadUdp.start();
        readMessagesThreadMulticast.start();
        writeMessagesThread.start();

        readMessagesThreadTcp.join();
        socketTcp.close();
        readMessagesThreadUdp.join();
        socketUdp.close();
        readMessagesThreadMulticast.join();
        socketMulticast.close();
        writeMessagesThread.join();

        System.out.println("KONIEC PRACY KLIENTA");
    }

    private void setTcpSocket() throws IOException {
        socketTcp = new Socket(
                serverSocketInfo.getAddress(),
                serverSocketInfo.getPort(),
                clientSocketInfo.getAddress(),
                clientSocketInfo.getPort()
        );

        DataInputStream inputStreamTcp = new DataInputStream(socketTcp.getInputStream());
        DataOutputStream outputStreamTcp = new DataOutputStream(socketTcp.getOutputStream());
    }

    private void setUdpSocket() throws IOException{
        socketUdp = new DatagramSocket(clientSocketInfo.getPort());
    }

    private void setMulticastSocket() throws IOException{
        socketMulticast = new MulticastSocket(multicastSocketInfo.getPort());
        socketAddressMulticast = new InetSocketAddress(
                multicastSocketInfo.getAddress(),
                multicastSocketInfo.getPort()
        );
        networkInterfaceMulticast = NetworkInterface.getByInetAddress(multicastSocketInfo.getAddress());
        socketMulticast.joinGroup(socketAddressMulticast, networkInterfaceMulticast);
    }

}