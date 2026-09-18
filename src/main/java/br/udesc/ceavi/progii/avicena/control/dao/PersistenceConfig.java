package br.udesc.ceavi.progii.avicena.control.dao;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;

public final class PersistenceConfig {

    private static final String PERSISTENCE_UNIT = "AvicenaBD";

    private PersistenceConfig() {}

    public static EntityManagerFactory createEntityManagerFactory() {
        Map<String, String> dotenv = loadDotenv();
        String url = resolve("AVICENA_DB_URL", dotenv);
        String user = resolve("AVICENA_DB_USER", dotenv);
        String password = resolve("AVICENA_DB_PASSWORD", dotenv);

        migrate(url, user, password);

        Map<String, String> overrides = new HashMap<>();
        overrides.put("jakarta.persistence.jdbc.url", url);
        overrides.put("jakarta.persistence.jdbc.user", user);
        overrides.put("jakarta.persistence.jdbc.password", password);
        return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT, overrides);
    }

    private static Map<String, String> loadDotenv() {
        try {
            return DotenvLoader.load(Paths.get(".env"));
        } catch (IOException e) {
            return Map.of();
        }
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

    private static String resolve(String envVar, Map<String, String> dotenv) {
        String value = System.getProperty(envVar);
        if (value == null) {
            value = System.getenv(envVar);
        }
        if (value == null) {
            value = dotenv.get(envVar);
        }
        if (value == null) {
            throw new IllegalStateException("Missing required configuration: " + envVar);
        }
        return value;
    }
}
