package service;

import model.annotation.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Repository<T> {

    private final Map<String, T> storage = new HashMap<>();

    public T save(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Cannot save null entity.");
        }

        String id = extractId(entity);
        if (id == null) {
            throw new IllegalStateException("Entity does not contain an @Id field or id is null: " + entity.getClass().getName());
        }

        storage.put(id, entity);
        return entity;
    }

    public T findById(String id) {
        return storage.get(id);
    }

    public ArrayList<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    public boolean delete(String id) {
        return storage.remove(id) != null;
    }

    @SafeVarargs
    public final void saveAll(T... items) {
        if (items != null) {
            for (T item : items) {
                save(item);
            }
        }
    }

    // Bounded generic method calculating average of any Number array
    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0) {
            return 0.0;
        }
        double sum = 0.0;
        for (N val : values) {
            if (val != null) {
                sum += val.doubleValue();
            }
        }
        return sum / values.length;
    }

    private String extractId(T entity) {
        Class<?> current = entity.getClass();
        while (current != null && current != Object.class) {
            for (Field field : current.getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true);
                    try {
                        Object val = field.get(entity);
                        return val != null ? val.toString() : null;
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Cannot access @Id field: " + field.getName(), e);
                    }
                }
            }
            current = current.getSuperclass();
        }
        return null;
    }
}

