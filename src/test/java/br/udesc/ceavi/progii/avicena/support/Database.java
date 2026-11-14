package br.udesc.ceavi.progii.avicena.support;

import br.udesc.ceavi.progii.avicena.control.dao.PersistenceConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** Plain JDBC access to the test Postgres (migrated first), for assertions the repositories can't express. */
public final class Database {

    private Database() {}

    public static void execute(String sql) {
        try (Connection connection = connect();
                Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public static List<String> strings(String sql) {
        try (Connection connection = connect();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {
            List<String> values = new ArrayList<>();
            while (resultSet.next()) values.add(resultSet.getString(1));
            return values;
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public static int count(String sql) {
        try (Connection connection = connect();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private static Connection connect() throws SQLException {
        PersistenceConfig.entityManagerFactory();
        return DriverManager.getConnection(
                System.getProperty("AVICENA_DB_URL"),
                System.getProperty("AVICENA_DB_USER"),
                System.getProperty("AVICENA_DB_PASSWORD"));
    }
}
