package task3;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class JavaUdpServer {

    public static void main(String args[])
    {
        System.out.println("JAVA UDP SERVER");
        DatagramSocket socket = null;
        int portNumber = 9008;

        try{
            socket = new DatagramSocket(portNumber);
            byte[] receiveBuffer = new byte[1024];

            while(true) {
                Arrays.fill(receiveBuffer, (byte)0);
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);

                int received = ByteBuffer.wrap(receivePacket.getData()).order(ByteOrder.LITTLE_ENDIAN).getInt();
                System.out.println("received msg: " + received);

                byte[] incremented = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(received + 1).array();
                DatagramPacket plusOnePacket = new DatagramPacket(incremented, incremented.length,
                receivePacket.getAddress(), receivePacket.getPort());
                socket.send(plusOnePacket);
            }
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
