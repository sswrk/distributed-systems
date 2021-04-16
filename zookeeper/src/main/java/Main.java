import org.apache.zookeeper.KeeperException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {

        String zookeeperServers = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
        String zNode = "/z";
        String program = args[0];

        ZookeeperApp zookeeperApp = new ZookeeperApp(zookeeperServers, zNode, program);
        zookeeperApp.start();

    }

}
