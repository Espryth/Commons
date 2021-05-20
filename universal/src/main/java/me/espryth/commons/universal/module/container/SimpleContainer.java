package me.espryth.commons.universal.module.container;

import me.espryth.commons.universal.module.exception.ContainerValueNotFoundException;
import team.unnamed.reflect.identity.TypeReference;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public  class SimpleContainer implements Container {

    private final Map<TypeReference<?>, Map<String, Object>> values;


    public SimpleContainer() {
        values = new ConcurrentHashMap<>();
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
