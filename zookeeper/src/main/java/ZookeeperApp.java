import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import java.io.IOException;
import java.util.Scanner;

public class ZookeeperApp {

    private final String zookeeperServers;
    private final String zNode;

    private ZooKeeper zooKeeper;
    private Watcher childrenWatcher;
    private Watcher programWatcher;

    private Process programProcess;
    private final ProcessBuilder programProcessBuilder;

    public ZookeeperApp(String zookeeperServers, String zNode, String program) {
        this.zookeeperServers = zookeeperServers;
        this.zNode = zNode;
        this.programProcessBuilder = new ProcessBuilder(program);
    }

    public void start() throws KeeperException, InterruptedException, IOException {
        zooKeeper = new ZooKeeper(zookeeperServers,10000,null);

        programWatcher = createProgramWatcher();
        while(true) {
            try {
                zooKeeper.exists(zNode, programWatcher);
                break;
            } catch (KeeperException ignore) {}
        }

        childrenWatcher = createChildrenWatcher(zNode);
        watchChildren(zNode, childrenWatcher);

        listenForCommands();
    }

    private void listenForCommands() throws KeeperException, InterruptedException {
        System.out.println("Available commands: children, exit");
        while(true) {
            Scanner scanner = new Scanner(System.in);
            String command = scanner.next();
            if(command.equals("exit")) {
                return;
            }
            if(command.equals("children")) {
                if(zooKeeper.exists(zNode, false) != null) {
                    printChildren(zNode);
                } else {
                    System.out.println("There is no " + zNode);
                }
            } else {
                System.out.println("No such command");
            }
        }
    }

    private void printChildren(String node) throws KeeperException, InterruptedException {
        for (String child : zooKeeper.getChildren(node,false)) {
            System.out.println(node + "/" + child);
            printChildren(node + "/" + child);
        }
    }

    private Watcher createProgramWatcher() throws KeeperException, InterruptedException, IOException {
        if(zooKeeper.exists(zNode, false) != null && (programProcess == null || !programProcess.isAlive())){
            programProcess = programProcessBuilder.start();
            zooKeeper.getChildren(zNode, childrenWatcher);
        }
        return watchedEvent -> {
            try {
                switch (watchedEvent.getType()) {
                    case NodeCreated -> {
                        programProcess = programProcessBuilder.start();
                        zooKeeper.getChildren(zNode, childrenWatcher);
                    }
                    case NodeDeleted -> programProcess.destroy();
                }
                zooKeeper.exists(zNode, programWatcher);
            } catch(IOException | InterruptedException | KeeperException e){
                e.printStackTrace();
            }
        };
    }

    private Watcher createChildrenWatcher(String node){
        return watchedEvent -> {
            try {
                if(watchedEvent.getType().equals(Watcher.Event.EventType.NodeChildrenChanged)) {
                    try {
                        System.out.println(node + " children number: " + zooKeeper.getAllChildrenNumber(node));
                    } catch (KeeperException.NoNodeException e){
                        System.out.println(node + " got deleted");
                    }
                    watchChildren(node, childrenWatcher);
                }
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    private void watchChildren(String node, Watcher watcher) throws KeeperException, InterruptedException {
        if(zooKeeper.exists(node, false) != null) {
            for (String child : zooKeeper.getChildren(node, watcher)) {
                watchChildren(node + "/" + child, watcher);
            }
        }
    }
}
