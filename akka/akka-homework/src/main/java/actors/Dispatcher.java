package actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import queries.Message;
import queries.StationRequest;

public class Dispatcher extends AbstractBehavior<Message> {

    public Dispatcher(ActorContext<Message> context) {
        super(context);
    }

    public static Behavior<Message> create() {
        return Behaviors.setup(Dispatcher::new);
    }

    private int currentSatelliteCommunicatorId = 0;

    @Override
    public Receive<Message> createReceive() {
        return newReceiveBuilder()
                .onMessage(StationRequest.class, this::onStationRequest)
                .build();
    }

    private Behavior<Message> onStationRequest(StationRequest stationRequest) {
        getContext()
                .spawn(SatelliteCommunicator.create(), "satelliteCommunicator" + currentSatelliteCommunicatorId++)
                .tell(stationRequest);
        return this;
    }
}