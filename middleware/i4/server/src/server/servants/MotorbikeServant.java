package server.servants;

import catalogue.Motorbike;
import com.zeroc.Ice.Current;

public class MotorbikeServant implements Motorbike {
    @Override
    public float getAcceleration(Current current) {
        Utils.logServantRequest(current, this, "getAcceleration()");
        return 2.0f;
    }

    @Override
    public int getWheels(Current current) {
        Utils.logServantRequest(current, this, "getWheels()");
        return 2;
    }
}
