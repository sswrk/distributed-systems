package actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import api.SatelliteAPI;
import queries.Message;
import queries.SatelliteCommunicatorResponse;
import queries.StationRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SatelliteCommunicator extends AbstractBehavior<Message> {

    public SatelliteCommunicator(ActorContext<Message> context) {
        super(context);
    }

    public static Behavior<Message> create() {
        return Behaviors.setup(SatelliteCommunicator::new);
    }

    @Override
    public Receive<Message> createReceive() {
        return newReceiveBuilder()
                .onMessage(StationRequest.class, this::onStationRequest)
                .build();
    }

    private Behavior<Message> onStationRequest(StationRequest stationRequest) throws ExecutionException, InterruptedException {

        class SatelliteTask implements Callable<Map.Entry<Integer, SatelliteAPI.Status>> {
            private final int satelliteId;

            public SatelliteTask(int satelliteId){
                this.satelliteId = satelliteId;
            }

            @Override
            public Map.Entry<Integer, SatelliteAPI.Status> call(){
                long startTime = new Date().getTime();
                SatelliteAPI.Status status = SatelliteAPI.getStatus(satelliteId);
                long responseTime = new Date().getTime() - startTime;
                if (responseTime <= stationRequest.timeout) {
                    return Map.entry(satelliteId, status);
                }
                return null;
            }
        }

        ExecutorService executorService = Executors.newFixedThreadPool(stationRequest.range);
        List<Callable<Map.Entry<Integer, SatelliteAPI.Status>>> tasks = IntStream.range(0, stationRequest.range)
                .mapToObj(i -> new SatelliteTask(stationRequest.firstSatelliteId + i))
                .collect(Collectors.toList());
        Map<Integer, SatelliteAPI.Status> errorMap = new HashMap<>();
        int successCount = 0;
        for (Future<Map.Entry<Integer, SatelliteAPI.Status>> result : executorService.invokeAll(tasks)) {
            if(result.get() != null) {
                successCount++;
                if(result.get().getValue() != SatelliteAPI.Status.OK){
                    errorMap.put(result.get().getKey(), result.get().getValue());
                }
            }
        }
        stationRequest.stationRef.tell(new SatelliteCommunicatorResponse(
                stationRequest.queryId,
                errorMap,
                successCount>0 ? successCount*100/stationRequest.range : 0
        ));
        return Behaviors.stopped();
    }
}
