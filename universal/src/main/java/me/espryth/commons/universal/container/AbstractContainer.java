package me.espryth.commons.universal.container;

import me.espryth.commons.universal.container.exception.ContainerAlreadyBindedException;
import me.espryth.commons.universal.container.exception.ContainerValueNotFoundException;
import team.unnamed.reflect.identity.TypeReference;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractContainer implements Container {

    private final Map<TypeReference<?>, Map<String, Object>> values = new ConcurrentHashMap<>();

    public abstract void configure();

    protected void install(Container container) {

        container.configure();

        for(TypeReference<?> key : container.getValues().keySet()) {

            if(!values.containsKey(key)) {
                values.put(key, new ConcurrentHashMap<>());
            }

            Map<String, Object> objects = container.getValues().get(key);
            values.get(key).putAll(objects);

        }

    }

    protected <T> void bind(TypeReference<T> type, T value, String identifier) {

        if(!values.containsKey(type)) {
            values.put(type, new ConcurrentHashMap<>());
        }

        Map<String, Object> objects = values.get(type);

        if(objects.containsKey(identifier)) {
            throw new ContainerAlreadyBindedException(type, identifier);
        }

        objects.put(identifier, value);

    }

    protected <T> void bind(TypeReference<T> type, T value) {
        bind(type, value, DEFAULT);
    }

    protected <T> void bind(Class<T> type, T value, String identifier) {
        bind(TypeReference.of(type), value, identifier);
    }

    protected <T> void bind(Class<T> type, T value) {
        bind(type, value, DEFAULT);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(TypeReference<T> type, String identifier) {

        Map<String, Object> objects = values.get(type);

        if(objects == null) {
            throw new ContainerValueNotFoundException(type);
        }

        Object value = objects.get(identifier);

        if(value == null) {
            throw new ContainerValueNotFoundException(type);
        }

        return (T) value;
    }

    @Override
    public Map<TypeReference<?>, Map<String, Object>> getValues() {
        return values;
    }
}
