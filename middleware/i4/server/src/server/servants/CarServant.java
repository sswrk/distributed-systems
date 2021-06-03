package server.servants;

import catalogue.Car;
import com.zeroc.Ice.Current;

public class CarServant implements Car {
    @Override
    public float getHorsepower(Current current) {
        Utils.logServantRequest(current, this, "getHorsepower()");
        return 230.0f;
    }

    @Override
    public float getPrice(Current current) {
        Utils.logServantRequest(current, this, "getPrice()");
        return 99000.0f;
    }
}
