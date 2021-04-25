package persistence;

import java.sql.*;

public class SqliteDatabaseController implements DatabaseController{

    private Connection connection;

    public void init() throws SQLException {
        String connectionUrl = "jdbc:sqlite:errors.sqlite";
        connection = DriverManager.getConnection(connectionUrl);
        if (connection!=null && !connection.isClosed()) {
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS 'satellites_errors'");
            statement.execute("CREATE TABLE IF NOT EXISTS satellites_errors (id INTEGER PRIMARY KEY, errors int)");
            String insertQuery = "INSERT INTO satellites_errors(id, errors) VALUES(?,?)";
            for (int i=100; i<200; i++) {
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setInt(1, i);
                preparedStatement.setInt(2, 0);
                preparedStatement.executeUpdate();
            }
        }
    }

    public void updateIncrement(int id) throws SQLException {
        String selectQuery = "SELECT errors FROM satellites_errors WHERE id = ?";
        PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
        selectStatement.setInt(1, id);
        ResultSet resultSet = selectStatement.executeQuery();
        resultSet.next();
        int errors = resultSet.getInt(1) + 1;
        String updateQuery = "UPDATE satellites_errors SET errors = ? WHERE id = ?";
        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
        updateStatement.setInt(1, errors);
        updateStatement.setInt(2, id);
        updateStatement.executeUpdate();
    }

    public int readData(int id) throws SQLException {
        String query = "SELECT errors FROM satellites_errors WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }


}
