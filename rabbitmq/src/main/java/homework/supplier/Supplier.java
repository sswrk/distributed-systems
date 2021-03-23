package homework.supplier;

import com.rabbitmq.client.*;

import homework.Equipment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Supplier {

    private final String name;
    private List<Equipment> availableEquipment;

    private Channel channel;

    private Consumer messageConsumer;
    private Consumer equipmentConsumer;

    private long currentOrderNumber;

    public Supplier(String name){
        this.name = name;
    }

    public void start() throws Exception {
        this.availableEquipment = getAvailableEquipmentFromInput();
        this.channel = createLocalHostChannel();
        createQueues();
        this.equipmentConsumer = createEquipmentConsumer();
        this.messageConsumer = createMessageConsumer();
        this.currentOrderNumber = 0;
        startListening();
    }

    private List<Equipment> getAvailableEquipmentFromInput() throws Exception {
        List<Equipment> availableEquipment = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("AVAILABLE EQUIPMENT: ");
        String equipmentList = reader.readLine();

        for(String equipment : equipmentList.split(" ")){
            if(Equipment.getEquipment(equipment).isPresent()) {
                availableEquipment.add(Equipment.getEquipment(equipment).get());
            }
        }

        return availableEquipment;
    }

    private Channel createLocalHostChannel() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        return connection.createChannel();
    }

    private void createQueues() throws Exception{
        channel.exchangeDeclare("equipment_exchange", BuiltinExchangeType.TOPIC);
        channel.exchangeDeclare("message_exchange", BuiltinExchangeType.TOPIC);

        channel.basicQos(availableEquipment.size() + 1);

        channel.queueDeclare("message_" + name, false, false, false, null);

        channel.queueBind("message_" + name, "message_exchange", name);
        channel.queueBind("message_" + name, "message_exchange", "suppliers.#");

        for (Equipment equipment : availableEquipment) {
            channel.queueDeclare("equipment_" + equipment.name(), false, false, false, null);
            channel.queueBind("equipment_" + equipment.name(), "equipment_exchange", "*." + equipment.name());
        }
    }

    private Consumer createEquipmentConsumer(){
        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                String[] crewOrder = message.split("\\.");
                if (crewOrder.length == 2){
                    String crewName = crewOrder[0];
                    String equipment = crewOrder[1];

                    System.out.println("ORDER " + currentOrderNumber +": " + equipment);

                    String response = "ORDER " + currentOrderNumber + ": " + equipment + " DONE";
                    channel.basicPublish("message_exchange", crewName, null, response.getBytes());

                    if (currentOrderNumber == Long.MAX_VALUE) {
                        currentOrderNumber = 0;
                    } else {
                        currentOrderNumber++;
                    }
                }
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
    }

    private Consumer createMessageConsumer(){
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
        channel.basicConsume("message_" + name, false, messageConsumer);

        for (Equipment equipment : availableEquipment) {
            channel.basicConsume("equipment_" + equipment.name(), false, equipmentConsumer);
        }
    }
}
