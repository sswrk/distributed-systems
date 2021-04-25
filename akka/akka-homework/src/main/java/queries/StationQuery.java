package queries;

import akka.actor.typed.ActorRef;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StationQuery implements Message {
    public final ActorRef<Message> dispatcherRef;
    public final ActorRef<Message> databaseCommunicatorRef;
    public final int firstSatelliteId;
    public final int range;
    public final int timeout;
}
