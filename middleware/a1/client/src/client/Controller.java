package client;

import grpc.model.*;
import io.grpc.Channel;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Controller {
    public ReentrantLock lock;

    private final Channel channel;
    private final CreatorPlatformInformatorGrpc.CreatorPlatformInformatorBlockingStub blockingStub;

    public volatile boolean reconnecting;

    private final List<ObserveRequest> subscriptions;
    private List<Thread> threads;
    private List<NotificationListener> listeners;

    public Controller(Channel channel) {
        this.lock = new ReentrantLock();

        this.channel = channel;
        this.blockingStub = CreatorPlatformInformatorGrpc.newBlockingStub(channel);

        this.reconnecting = false;

        this.subscriptions = new LinkedList<>();
        this.threads = new ArrayList<>();
        this.listeners = new ArrayList<>();
    }

    public void addSubscription(ObserveRequest request) {
        this.subscriptions.add(request);
    }

    public void addThread(Thread thread) {
        this.threads.add(thread);
    }

    public void addHandler(NotificationListener handler) {
        this.listeners.add(handler);
    }

    public void reportError() {
        if (this.lock.tryLock()) {
            if (!this.reconnecting) {
                this.reconnecting = true;
                reconnect();
            }
            this.lock.unlock();
        }
    }

    void reconnect() {
        this.lock.lock();
        if (this.subscriptions.isEmpty()) {
            return;
        }
        System.out.println("Disconnected from server");

        int retries = 1;
        while (retries < 10) {
            try {
                Thread.sleep(3000);
                this.blockingStub.ping(Empty.newBuilder().build());
                break;
            } catch (Exception e) {
                retries++;
                System.out.println("Trying to reconnect...");
            }
        }

        if (retries == 10) {
            System.out.println("Couldn't reconnect. Quitting...");
            System.exit(1);
        }

        this.threads = new ArrayList<>();
        this.listeners = new ArrayList<>();
        for (ObserveRequest request: this.subscriptions) {
            NotificationListener handler = new NotificationListener(request, this, this.channel);
            Thread thread = new Thread(handler);
            this.threads.add(thread);
            this.listeners.add(handler);
        }

        this.reconnecting = false;
        System.out.println("Reconnected!");
        this.lock.unlock();

        threads.forEach(Thread::start);
    }
}
