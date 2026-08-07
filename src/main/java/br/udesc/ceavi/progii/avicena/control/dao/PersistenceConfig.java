package br.udesc.ceavi.progii.avicena.control.dao;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import java.util.HashMap;
import java.util.Map;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;

public final class PersistenceConfig {

    private static final String PERSISTENCE_UNIT = "AvicenaBD";
    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/AvicenaBD";
    private static final String DEFAULT_USER = "postgres";
    private static final String DEFAULT_PASSWORD = "CasaAmarela";

    private PersistenceConfig() {}

    public static EntityManagerFactory createEntityManagerFactory() {
        String url = resolve("AVICENA_DB_URL", DEFAULT_URL);
        String user = resolve("AVICENA_DB_USER", DEFAULT_USER);
        String password = resolve("AVICENA_DB_PASSWORD", DEFAULT_PASSWORD);

        migrate(url, user, password);

        Map<String, String> overrides = new HashMap<>();
        overrides.put("jakarta.persistence.jdbc.url", url);
        overrides.put("jakarta.persistence.jdbc.user", user);
        overrides.put("jakarta.persistence.jdbc.password", password);
        return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT, overrides);
    }

    private static void migrate(String url, String user, String password) {
        try {
            Flyway.configure()
                    .dataSource(url, user, password)
                    .baselineOnMigrate(true)
                    .baselineVersion("1")
                    .load()
                    .migrate();
        } catch (FlywayException e) {
            throw new PersistenceException(e.getMessage(), e);
        }
    }

    private static String resolve(String envVar, String defaultValue) {
        String value = System.getProperty(envVar);
        if (value == null) {
            value = System.getenv(envVar);
        }
        return value != null ? value : defaultValue;
    }
}
