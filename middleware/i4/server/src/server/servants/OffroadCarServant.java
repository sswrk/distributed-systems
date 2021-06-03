package server.servants;

import catalogue.OffroadCar;
import com.zeroc.Ice.Current;


public class OffroadCarServant implements OffroadCar {

    @Override
    public int getExtraWheels(Current current) {
        Utils.logServantRequest(current, this, "getExtraWheels()");
        return 1;
    }

    @Override
    public float getHorsepower(Current current) {
        Utils.logServantRequest(current, this, "getHorsepower()");
        return 330.0f;
    }

    @Override
    public float getPrice(Current current) {
        Utils.logServantRequest(current, this, "getPrice()");
        return 120_000f;
    }
}
