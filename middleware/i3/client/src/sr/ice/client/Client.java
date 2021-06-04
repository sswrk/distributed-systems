package sr.ice.client;

import com.zeroc.Ice.*;
import com.zeroc.Ice.Object;

import java.lang.Exception;

public class Client {

    private final Communicator communicator;
    private final ObjectPrx proxy;

    public Client(String[] args) throws Exception {
        this.communicator = Util.initialize(args);
        this.proxy = communicator.propertyToProxy("Calc1.Proxy");
        if(proxy == null){
            throw new Exception("Proxy is null");
        }
    }

    public int add(int a, int b) throws Exception {
        OutputStream outputStream = new OutputStream(communicator);
        outputStream.startEncapsulation();
        outputStream.writeInt(a);
        outputStream.writeInt(b);
        outputStream.endEncapsulation();
        byte[] inParams = outputStream.finished();
        Object.Ice_invokeResult result = proxy.ice_invoke("add", OperationMode.Normal, inParams);
        if (result.returnValue) {
            InputStream inputStream = new InputStream(communicator, result.outParams);
            inputStream.startEncapsulation();
            int addResult = inputStream.readInt();
            inputStream.endEncapsulation();
            return addResult;
        }
        else{
            throw new Exception("Result empty");
        }
    }

    public int subtract(int a, int b) throws Exception {
        return add(a, -b);
    }
}
