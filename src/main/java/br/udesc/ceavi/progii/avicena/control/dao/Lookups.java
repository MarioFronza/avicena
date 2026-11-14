package br.udesc.ceavi.progii.avicena.control.dao;

import jakarta.persistence.EntityManager;

/** Resolves a seeded lookup row (marital status, urgency status, ...) from the domain enum it mirrors. */
public final class Lookups {

    private Lookups() {}

    public static <T> T byCode(EntityManager entityManager, Class<T> type, Enum<?> code) {
        if (code == null) return null;
        return entityManager
                .createQuery("SELECT e FROM " + type.getSimpleName() + " e WHERE e.code = :code", type)
                .setParameter("code", code.name())
                .getResultStream()
                .findFirst()
                .orElseThrow(
                        () -> new IllegalStateException("No " + type.getSimpleName() + " with code " + code.name()));
    }
}
