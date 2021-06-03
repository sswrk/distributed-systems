package server.servants;

import catalogue.ElectricCar;
import com.zeroc.Ice.Current;

public class ElectricCarServant implements ElectricCar {
    @Override
    public int getBatteries(Current current) {
        Utils.logServantRequest(current, this, "getBatteries()");
        return 1000;
    }

    @Override
    public float getElectricityUse(Current current) {
        Utils.logServantRequest(current, this, "getElectricityUse()");
        return 1000.0f;
    }
}
