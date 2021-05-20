package me.espryth.commons.universal.module.binder;

import me.espryth.commons.universal.module.Module;
import team.unnamed.reflect.identity.TypeReference;

public interface Binder {

    <T> void bind(TypeReference<T> type, T value, String identifier);

    default <T> void bind(TypeReference<T> type, T value) {
        bind(type, value, Module.DEFAULT);
    }

    default <T> void bind(Class<T> type, T value, String identifier) {
        bind(TypeReference.of(type), value, identifier);
    }

    default <T> void bind(Class<T> type, T value) {
        bind(type, value, Module.DEFAULT);
    }


}
