package persistence;

import java.sql.SQLException;

public interface DatabaseController {
    void init() throws SQLException;
    void updateIncrement(int id) throws SQLException;
    int readData(int id) throws SQLException;
}
