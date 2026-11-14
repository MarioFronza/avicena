package br.udesc.ceavi.progii.avicena.support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** Plain JDBC access to the test Postgres, for assertions the repositories can't express. */
public final class Database {

    private Database() {}

    public static int count(String sql) {
        try (Connection connection = DriverManager.getConnection(
                        System.getProperty("AVICENA_DB_URL"),
                        System.getProperty("AVICENA_DB_USER"),
                        System.getProperty("AVICENA_DB_PASSWORD"));
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
