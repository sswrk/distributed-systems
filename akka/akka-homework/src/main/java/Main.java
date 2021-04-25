import actors.DatabaseCommunicator;
import actors.Dispatcher;
import actors.Station;
import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.Terminated;
import akka.actor.typed.javadsl.Behaviors;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import queries.DatabaseReadRequest;
import queries.Message;
import queries.StationQuery;

import java.io.File;

public class Main {
    public static Behavior<Void> create() {
        return Behaviors.setup(
                context -> {
                    ActorRef<Message> dispatcherRef = context.spawn(Dispatcher.create(), "Dispatcher");
                    ActorRef<Message> databaseRef = context.spawn(DatabaseCommunicator.create(), "Database");
                    ActorRef<Message> station1Ref = context.spawn(Station.create(), "Station1");
                    ActorRef<Message> station2Ref = context.spawn(Station.create(), "Station2");
                    ActorRef<Message> station3Ref = context.spawn(Station.create(), "Station3");

                    Thread.sleep(2000);

                    station1Ref.tell(new StationQuery(dispatcherRef, databaseRef, 100, 50, 300));
                    station2Ref.tell(new StationQuery(dispatcherRef, databaseRef,100, 50, 300));
                    station3Ref.tell(new StationQuery(dispatcherRef, databaseRef,100, 50, 300));

                    Thread.sleep(1000);

                    for(int i=100; i<200; i++){
                        station1Ref.tell(new DatabaseReadRequest(databaseRef, i));
                    }

                    return Behaviors.receive(Void.class)
                            .onSignal(Terminated.class, sig -> Behaviors.stopped())
                            .build();
                }
        );
    }

    public static void main(String[] args) {
        Config config = ConfigFactory.parseFile(new File("/src/main/resources/dispatcher.conf"));
        ActorSystem.create(Main.create(), "SatelliteSystem", config);
    }
}