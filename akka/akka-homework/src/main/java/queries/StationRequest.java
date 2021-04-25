package queries;

import akka.actor.typed.ActorRef;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StationRequest implements Message {
    public final ActorRef<Message> stationRef;
    public final int queryId;
    public final int firstSatelliteId;
    public final int range;
    public final int timeout;
}
