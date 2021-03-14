package chat.server;

import chat.SocketInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class ServerClientTcp implements Runnable{

    private final Socket socket;
    private final String id;
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;
    private final Server server;

    public ServerClientTcp(Socket socket, String id, DataInputStream inputStream, DataOutputStream outputStream, Server server){
        this.socket = socket;
        this.id = id;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.server = server;
    }


    @Override
    public void run() {
        while(true){
            try {
                String messageFromClient = inputStream.readUTF();
                System.out.println("<" + id + "> " + messageFromClient);

                Map<SocketInfo, ServerClientTcp> serverClients = server.getClients();
                for(Map.Entry<SocketInfo, ServerClientTcp> client : serverClients.entrySet()){
                    if(client.getValue()!=this){
                        client.getValue().getOutputStream().writeUTF(messageFromClient);
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
            server.getClients().remove(new SocketInfo(socket.getInetAddress().getHostName(), socket.getPort()));
            System.out.println(id + " został odłączony od serwera");
        } catch (IOException e){
            System.out.println("Problem while disconnecting user " + id);
        }
    }

    public String getId(){
        return id;
    }

    private DataOutputStream getOutputStream(){
        return outputStream;
    }
}
