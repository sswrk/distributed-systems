package server;

import com.zeroc.Ice.*;
import com.zeroc.Ice.Object;
import server.servants.*;

import java.util.HashMap;
import java.util.Map;

public class ServantLocatorImpl implements ServantLocator {
    private final Map<String, Object> servants = new HashMap<>();

    @Override
    public LocateResult locate(Current current) throws UserException {
        String category = current.id.category;
        if(!servants.containsKey(category)) {
            Object servant = switch (category) {
                case "car" -> new CarServant();
                case "supercar" -> new SuperCarServant();
                case "offroadcar" -> new OffroadCarServant();
                case "electriccar" -> new ElectricCarServant();
                case "motorbike" -> new MotorbikeServant();
                default -> throw new ObjectNotFoundException();
            };

            servants.put(category, servant);
            System.out.println("Created servant: " + servant);
        }
        return new ServantLocator.LocateResult(servants.get(category), null);
    }

    @Override
    public void finished(Current current, Object object, java.lang.Object o) {

    }

    @Override
    public void deactivate(String s) {

    }
}
