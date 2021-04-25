package actors;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import api.SatelliteAPI;
import queries.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Station extends AbstractBehavior<Message> {

    private final String name;

    private final Map<Integer, Long> timesSent = new HashMap<>();
    private ActorRef<Message> databaseCommunicatorRef = null;

    private int currentQueryId = 0;

    public Station(ActorContext<Message> context) {
        super(context);
        this.name = context.getSelf().path().name();
    }

    public static Behavior<Message> create() {
        return Behaviors.setup(Station::new);
    }

    @Override
    public Receive<Message> createReceive() {
        return newReceiveBuilder()
                .onMessage(StationQuery.class, this::onStationQuery)
                .onMessage(SatelliteCommunicatorResponse.class, this::onSatelliteCommunicatorResponse)
                .onMessage(DatabaseReadRequest.class, this::onDatabaseReadRequest)
                .onMessage(DatabaseReadResponse.class, this::onDatabaseReadResponse)
                .build();
    }

    private Behavior<Message> onStationQuery(StationQuery stationQuery) {
        if (this.databaseCommunicatorRef == null) {
            this.databaseCommunicatorRef = stationQuery.databaseCommunicatorRef;
        }
        StationRequest stationRequest = new StationRequest(
                this.getContext().getSelf(),
                currentQueryId,
                stationQuery.firstSatelliteId,
                stationQuery.range,
                stationQuery.timeout);
        timesSent.put(currentQueryId, new Date().getTime());
        stationQuery.dispatcherRef.tell(stationRequest);
        currentQueryId++;
        return this;
    }

    private Behavior<Message> onSatelliteCommunicatorResponse(SatelliteCommunicatorResponse satelliteCommunicatorResponse){
        StringBuilder responseStringBuilder = new StringBuilder();
        responseStringBuilder.append("\nStation ").append(this.name).append(" response:\n")
                .append("\tTime of response: ")
                .append(new Date().getTime() - timesSent.remove(satelliteCommunicatorResponse.queryId))
                .append(" milliseconds").append("\n")
                .append("\t").append(satelliteCommunicatorResponse.successfulPercent).append("% satellites responded\n")
                .append("\tNumber of errors: ").append(satelliteCommunicatorResponse.errorMap.size()).append("\n")
                .append("\tError list:\n");
        for(Map.Entry<Integer, SatelliteAPI.Status> entry : satelliteCommunicatorResponse.errorMap.entrySet()){
            this.databaseCommunicatorRef.tell(new DatabaseUpdateIncrementRequest(entry.getKey()));
            responseStringBuilder.append("\t\tSatellite ").append(entry.getKey()).append(" error: ").append(entry.getValue())
                    .append("\n");
        }
        System.out.println(responseStringBuilder.toString());
        return this;
    }

    private Behavior<Message> onDatabaseReadResponse(DatabaseReadResponse databaseReadResponse) {
        if (databaseReadResponse.errorCount > 0) {
            System.out.println("Satellite " + databaseReadResponse.satelliteId + " errors: " + databaseReadResponse.errorCount);
        }
        return this;
    }

    private Behavior<Message> onDatabaseReadRequest(DatabaseReadRequest databaseReadRequest) {
        databaseReadRequest.stationRef.tell(new DatabaseReadRequest(
                this.getContext().getSelf(),
                databaseReadRequest.satelliteId
        ));
        return this;
    }
}