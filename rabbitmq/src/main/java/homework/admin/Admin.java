package homework.admin;

import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Admin {

    private Channel channel;
    private Consumer consumer;

    public void start() throws Exception {

        Connection connection = createLocalHostConnection();
        this.channel = connection.createChannel();
        createExchangesAndQueues();
        this.consumer = createConsumer();

        startListening();

        channel.close();
        connection.close();
    }

    private Connection createLocalHostConnection() throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        return factory.newConnection();
    }

    private void createExchangesAndQueues() throws Exception{
        channel.exchangeDeclare("message_exchange", BuiltinExchangeType.TOPIC);
        channel.exchangeDeclare("equipment_exchange", BuiltinExchangeType.TOPIC);
        channel.basicQos(2);

        channel.queueDeclare("admin", false, false, false, null);
        channel.queueBind("admin", "equipment_exchange", "*.*");
        channel.queueBind("admin", "message_exchange", "#");
    }

    private Consumer createConsumer(){
        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println(message);
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
    }

    private void startListening() throws Exception{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        channel.basicConsume("admin", false, consumer);

        while(true) {
            String[] input = reader.readLine().split(":");
            switch (input[0]) {
                case "S" -> channel.basicPublish(
                        "message_exchange",
                        "suppliers",
                        null,
                        ("<ADMIN> " + input[1]).getBytes()
                );
                case "C" -> channel.basicPublish(
                        "message_exchange",
                        "crews",
                        null,
                        ("<ADMIN> " + input[1]).getBytes()
                );
                case "A" -> channel.basicPublish(
                        "message_exchange",
                        "suppliers.crews",
                        null,
                        ("<ADMIN> " + input[1]).getBytes()
                );
            }
        }
    }
}