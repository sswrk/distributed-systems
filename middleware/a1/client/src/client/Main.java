package client;

import grpc.model.ObserveRequest;
import io.grpc.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter your ID");
        String userId = bufferedReader.readLine();
        System.out.println("Enter creators you want to subscribe");
        List<String> creators = new ArrayList<>();
        while(true) {
            String input = bufferedReader.readLine();
            if (input.equals("")) {
                break;
            } else {
                creators.add(input);
            }
        }
        System.out.println("Creators entered!");

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        Controller controller = new Controller(channel);

        creators.forEach(company -> {
            ObserveRequest request = ObserveRequest.newBuilder()
                    .setCreatorName(company)
                    .setClientId(userId)
                    .build();
            NotificationListener handler = new NotificationListener(request, controller, channel);
            Thread thread = new Thread(handler);
            controller.addSubscription(request);
            controller.addThread(thread);
            controller.addHandler(handler);
            thread.start();
        });

        System.out.println("Starting to observe creators");
    }
}
