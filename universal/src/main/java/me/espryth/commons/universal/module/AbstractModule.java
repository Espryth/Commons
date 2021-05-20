package me.espryth.commons.universal.module;

import me.espryth.commons.universal.module.binder.Binder;
import team.unnamed.reflect.identity.TypeReference;

public abstract class AbstractModule implements Module {

    protected Binder binder;

    protected void install(Module module) {
        module.setBinder(binder);
        module.configure();
    }

    protected <T> void bind(TypeReference<T> type, T value, String identifier) {
        binder.bind(type, value, identifier);
    }

    protected <T> void bind(TypeReference<T> type, T value) {
        binder.bind(type, value, Module.DEFAULT);
    }

    protected <T> void bind(Class<T> type, T value, String identifier) {
        binder.bind(TypeReference.of(type), value, identifier);
    }

    protected <T> void bind(Class<T> type, T value) {
        binder.bind(type, value, Module.DEFAULT);
    }


    @Override
    public void setBinder(Binder binder) {
        this.binder = binder;
    }
}
