package me.espryth.commons.universal.module.container;

import me.espryth.commons.universal.module.Module;
import team.unnamed.reflect.identity.TypeReference;

import java.util.Map;

public interface Container {

    <T> T get(TypeReference<T> type, String identifier);

    default <T> T get(TypeReference<T> type) {
        return get(type, Module.DEFAULT);
    }

    default <T> T get(Class<T> type, String identifier) {
        return get(TypeReference.of(type), identifier);
    }

    default <T> T get(Class<T> type) {
        return get(type, Module.DEFAULT);
    }

    Map<TypeReference<?>, Map<String, Object>> getValues();

}
