package br.udesc.ceavi.progii.avicena.control.dao;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class PersistenceConfig {

    private static final String PERSISTENCE_UNIT = "AvicenaBD";

    private PersistenceConfig() {
    }

    public static EntityManagerFactory createEntityManagerFactory() {
        return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT, overrides());
    }

    private static Map<String, String> overrides() {
        Map<String, String> overrides = new HashMap<>();
        putIfPresent(overrides, "javax.persistence.jdbc.url", "AVICENA_DB_URL");
        putIfPresent(overrides, "javax.persistence.jdbc.user", "AVICENA_DB_USER");
        putIfPresent(overrides, "javax.persistence.jdbc.password", "AVICENA_DB_PASSWORD");
        return overrides;
    }

    private static void putIfPresent(Map<String, String> overrides, String property, String envVar) {
        String value = System.getProperty(envVar);
        if (value == null) {
            value = System.getenv(envVar);
        }
        if (value != null) {
            overrides.put(property, value);
        }
    }
}
