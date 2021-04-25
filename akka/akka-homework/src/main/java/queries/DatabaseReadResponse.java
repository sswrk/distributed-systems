package queries;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DatabaseReadResponse implements Message {
    public final int satelliteId;
    public final int errorCount;
}
