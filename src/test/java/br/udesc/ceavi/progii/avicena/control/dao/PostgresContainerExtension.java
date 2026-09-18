package br.udesc.ceavi.progii.avicena.control.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.postgresql.PostgreSQLContainer;

public class PostgresContainerExtension implements BeforeAllCallback, BeforeEachCallback {

    private static final Set<String> SEEDED_TABLES = Set.of("marital_statuses", "roles", "flyway_schema_history");

    private static final PostgreSQLContainer CONTAINER = new PostgreSQLContainer("postgres:16-alpine")
            .withDatabaseName("AvicenaBD")
            .withUsername("postgres")
            .withPassword("CasaAmarela");

    @Override
    public void beforeAll(ExtensionContext context) {
        if (!CONTAINER.isRunning()) {
            CONTAINER.start();
        }
        System.setProperty("AVICENA_DB_URL", CONTAINER.getJdbcUrl());
        System.setProperty("AVICENA_DB_USER", CONTAINER.getUsername());
        System.setProperty("AVICENA_DB_PASSWORD", CONTAINER.getPassword());
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        try (Connection connection =
                DriverManager.getConnection(CONTAINER.getJdbcUrl(), CONTAINER.getUsername(), CONTAINER.getPassword())) {
            List<String> tables = transactionalTables(connection);
            if (tables.isEmpty()) {
                return;
            }
            try (Statement statement = connection.createStatement()) {
                statement.execute("TRUNCATE TABLE " + String.join(", ", tables) + " RESTART IDENTITY CASCADE");
            }
        }
    }

    private List<String> transactionalTables(Connection connection) throws Exception {
        List<String> tables = new ArrayList<>();
        try (Statement statement = connection.createStatement();
                ResultSet resultSet =
                        statement.executeQuery("SELECT tablename FROM pg_tables WHERE schemaname = 'public'")) {
            while (resultSet.next()) {
                String table = resultSet.getString("tablename");
                if (!SEEDED_TABLES.contains(table)) {
                    tables.add(table);
                }
            }
        }
        return tables;
    }
}
