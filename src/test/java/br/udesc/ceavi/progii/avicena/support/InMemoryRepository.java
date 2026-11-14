package br.udesc.ceavi.progii.avicena.support;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Behaves like the JPA repositories: assigns ids on insert, keeps insertion order, ignores unknown deletes. */
public abstract class InMemoryRepository<T> {

    private final Map<Long, T> rows = new LinkedHashMap<>();
    private long nextId = 1;

    protected abstract Long idOf(T item);

    protected abstract T withId(T item, Long id);

    public T save(T item) {
        T stored = idOf(item) == null ? withId(item, nextId++) : item;
        nextId = Math.max(nextId, idOf(stored) + 1);
        rows.put(idOf(stored), stored);
        return stored;
    }

    public List<T> findAll() {
        return List.copyOf(rows.values());
    }

    public Optional<T> findById(Long id) {
        return Optional.ofNullable(rows.get(id));
    }

    public void delete(T item) {
        if (idOf(item) != null) rows.remove(idOf(item));
    }
}
