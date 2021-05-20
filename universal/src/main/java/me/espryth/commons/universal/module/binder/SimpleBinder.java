package me.espryth.commons.universal.module.binder;

import me.espryth.commons.universal.module.container.Container;
import me.espryth.commons.universal.module.exception.ContainerAlreadyBindedException;
import team.unnamed.reflect.identity.TypeReference;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleBinder implements Binder {

    private final Container container;

    public SimpleBinder(Container container) {
        this.container = container;
    }

    @Override
    public <T> void bind(TypeReference<T> type, T value, String identifier) {
        if(!container.getValues().containsKey(type)) {
            container.getValues().put(type, new ConcurrentHashMap<>());
        }

        Map<String, Object> objects = container.getValues().get(type);

        if(objects.containsKey(identifier)) {
            throw new ContainerAlreadyBindedException(type, identifier);
        }

        objects.put(identifier, value);
    }
}
