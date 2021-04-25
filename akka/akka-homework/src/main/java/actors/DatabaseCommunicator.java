package actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import persistence.DatabaseController;
import persistence.SqliteDatabaseController;
import queries.DatabaseReadRequest;
import queries.DatabaseUpdateIncrementRequest;
import queries.DatabaseReadResponse;
import queries.Message;

import java.sql.SQLException;

public class DatabaseCommunicator extends AbstractBehavior<Message> {
    private static final DatabaseController database = new SqliteDatabaseController();

    public DatabaseCommunicator(ActorContext<Message> context) {
        super(context);
    }

    public static Behavior<Message> create() {
        try {
            database.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Behaviors.setup(DatabaseCommunicator::new);
    }

    @Override
    public Receive<Message> createReceive() {
        return newReceiveBuilder()
                .onMessage(DatabaseUpdateIncrementRequest.class, this::onUpdateIncrementRequest)
                .onMessage(DatabaseReadRequest.class, this::onReadRequest)
                .build();
    }

    private Behavior<Message> onUpdateIncrementRequest(DatabaseUpdateIncrementRequest databaseUpdateIncrementRequest) {
        try {
            database.updateIncrement(databaseUpdateIncrementRequest.satelliteId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    private Behavior<Message> onReadRequest(DatabaseReadRequest databaseReadRequest) {
        try {
            databaseReadRequest.stationRef.tell(new DatabaseReadResponse(
                    databaseReadRequest.satelliteId,
                    database.readData(databaseReadRequest.satelliteId)
            ));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }
}
