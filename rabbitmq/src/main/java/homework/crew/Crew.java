package homework.crew;

import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Crew {

    private final String name;

    private Channel channel;
    private Consumer consumer;

    public Crew(String name){
        this.name = name;
    }

    public void start() throws Exception {

        Connection connection = createLocalHostConnection();
        this.channel = connection.createChannel();
        createQueues();
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

    private void createQueues() throws Exception{
        channel.queueDeclare("message_" + name, false, false, false, null);
        channel.exchangeDeclare("message_exchange", BuiltinExchangeType.TOPIC);
        channel.basicQos(2);

        channel.queueBind("message_" + name, "message_exchange", name);
        channel.queueBind("message_" + name, "message_exchange", "#.crews");
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

        channel.basicConsume("message_" + name, false, consumer);

        while(true) {
            System.out.println("ORDER EQUIPMENT");
            String message = reader.readLine().toUpperCase();
            channel.basicPublish("equipment_exchange", name + "." + message, null, (name + "." + message).getBytes());
            System.out.println("ORDERED " + message);
        }
    }

}
