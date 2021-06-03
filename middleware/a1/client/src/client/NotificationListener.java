package client;

import grpc.model.*;
import io.grpc.Channel;

import java.util.Iterator;

public class NotificationListener implements Runnable {

    private final CreatorPlatformInformatorGrpc.CreatorPlatformInformatorBlockingStub blockingStub;
    private final Controller controller;
    private final ObserveRequest observeRequest;

    public NotificationListener(ObserveRequest observeRequest, Controller controller, Channel channel) {
        this.blockingStub = CreatorPlatformInformatorGrpc.newBlockingStub(channel);
        this.controller = controller;
        this.observeRequest = observeRequest;
    }

    @Override
    public void run() {
        Iterator<ContentInfo> notificationIterator = this.blockingStub.observe(this.observeRequest);
        try {
            while(true) {
                ContentInfo contentInfo = notificationIterator.next();
                StringBuilder stringBuilder = new StringBuilder()
                        .append("Creator: ")
                        .append(contentInfo.getCreatorName())
                        .append("\n")
                        .append("Time: ")
                        .append(contentInfo.getTimestamp())
                        .append("\n")
                        .append("Title: ")
                        .append(contentInfo.getTitle())
                        .append("\n")
                        .append("Categories: ");
                contentInfo.getCategoriesList().forEach(
                        category -> stringBuilder.append(category).append(" "));
                stringBuilder.append("\n")
                        .append("Price: ")
                        .append(contentInfo.getPrice())
                        .append("\n")
                        .append("Content type: ")
                        .append(contentInfo.getContentType())
                        .append("\n");

                System.out.println(stringBuilder);
            }
        } catch (Exception e) {
            if (!this.controller.reconnecting)
                this.controller.reportError();
        }
    }
}
