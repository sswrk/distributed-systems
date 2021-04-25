package queries;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DatabaseUpdateIncrementRequest implements Message {
    public final int satelliteId;
}