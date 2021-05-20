package me.espryth.commons.universal.container;

import team.unnamed.reflect.identity.TypeReference;

import java.util.Map;

public interface Container {

    String DEFAULT = "DEFAULT";

    void configure();

    <T> T get(TypeReference<T> type, String identifier);

    default <T> T get(TypeReference<T> type) {
        return get(type, DEFAULT);
    }

    default <T> T get(Class<T> type, String identifier) {
        return get(TypeReference.of(type), identifier);
    }

    default <T> T get(Class<T> type) {
        return get(type, DEFAULT);
    }

    Map<TypeReference<?>, Map<String, Object>> getValues();

}
