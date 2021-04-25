package queries;

import akka.actor.typed.ActorRef;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DatabaseReadRequest implements Message {
    public final ActorRef<Message> stationRef;
    public final int satelliteId;
}
