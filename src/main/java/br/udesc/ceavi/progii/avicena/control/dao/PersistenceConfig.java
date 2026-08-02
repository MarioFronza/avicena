package br.udesc.ceavi.progii.avicena.control.dao;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public final class PersistenceConfig {

    private static final String PERSISTENCE_UNIT = "AvicenaBD";

    private PersistenceConfig() {}

    public static EntityManagerFactory createEntityManagerFactory() {
        return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT, overrides());
    }

    private static Map<String, String> overrides() {
        Map<String, String> overrides = new HashMap<>();
        putIfPresent(overrides, "jakarta.persistence.jdbc.url", "AVICENA_DB_URL");
        putIfPresent(overrides, "jakarta.persistence.jdbc.user", "AVICENA_DB_USER");
        putIfPresent(overrides, "jakarta.persistence.jdbc.password", "AVICENA_DB_PASSWORD");
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
