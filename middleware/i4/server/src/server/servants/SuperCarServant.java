package server.servants;

import catalogue.SuperCar;
import com.zeroc.Ice.Current;

public class SuperCarServant implements SuperCar {
    @Override
    public int getSeats(Current current) {
        Utils.logServantRequest(current, this, "getSeats()");
        return 2;
    }

    @Override
    public float getFuelUse(Current current) {
        Utils.logServantRequest(current, this, "getFuelUse()");
        return 100.0f;
    }

    @Override
    public float getHorsepower(Current current) {
        Utils.logServantRequest(current, this, "getHorsepower()");
        return 920.0f;
    }

    @Override
    public float getPrice(Current current) {
        Utils.logServantRequest(current, this, "getPrice()");
        return 940_000.f;
    }
}
