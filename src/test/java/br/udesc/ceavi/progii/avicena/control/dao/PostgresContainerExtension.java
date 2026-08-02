package br.udesc.ceavi.progii.avicena.control.dao;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.postgresql.PostgreSQLContainer;

public class PostgresContainerExtension implements BeforeAllCallback {

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
}
