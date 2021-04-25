package queries;

import api.SatelliteAPI;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class SatelliteCommunicatorResponse implements Message {
    public final int queryId;
    public final Map<Integer, SatelliteAPI.Status> errorMap;
    public final int successfulPercent;
}
