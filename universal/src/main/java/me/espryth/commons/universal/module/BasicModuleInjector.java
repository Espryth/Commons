package me.espryth.commons.universal.module;

import me.espryth.commons.universal.module.binder.Binder;
import me.espryth.commons.universal.module.binder.SimpleBinder;
import me.espryth.commons.universal.module.container.Container;
import me.espryth.commons.universal.module.container.SimpleContainer;

public class BasicModuleInjector implements ModuleInjector {

    private final Container container;
    private final Binder binder;

    public BasicModuleInjector() {
        this.container = new SimpleContainer();
        this.binder = new SimpleBinder(container);
    }

    @Override
    public void install(Module module) {
        module.setBinder(binder);
        module.configure();
    }

    @Override
    public Container getContainer() {
        return container;
    }
}
