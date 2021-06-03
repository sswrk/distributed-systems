package server.servants;

import com.zeroc.Ice.Current;
import com.zeroc.Ice.Object;

public class Utils {

    public static void logServantRequest(Current current, Object servant, String methodName){
        System.out.println(current.id.category + "/" + current.id.name + "; " +
                servant + ": " + methodName);
    }
}
