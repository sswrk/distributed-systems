package task1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class JavaUdpClient {

    public static void main(String args[])
    {
        System.out.println("JAVA UDP CLIENT");
        DatagramSocket socket = null;
        int portNumber = 9008;

        try {
            socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName("localhost");
            byte[] msg = "Ping Java Udp".getBytes();

            DatagramPacket sendPacket = new DatagramPacket(msg, msg.length, address, portNumber);
            socket.send(sendPacket);

            byte[] buffer = new byte[1024];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            socket.receive(response);
            System.out.println("received msg: " + new String(response.getData()));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
