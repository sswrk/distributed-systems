package client;

import grpc.model.ObserveRequest;
import io.grpc.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Enter creators you want to subscribe");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        List<String> companies = new ArrayList<>();
        while(true) {
            String input = bufferedReader.readLine();
            if (input.equals("")) {
                break;
            } else {
                companies.add(input);
            }
        }
        System.out.println("Creators entered!");

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        Controller controller = new Controller(channel);

        companies.forEach(company -> {
            ObserveRequest request = ObserveRequest.newBuilder()
                    .setCreatorName(company)
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
