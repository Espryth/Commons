package me.espryth.commons.universal.module;

import me.espryth.commons.universal.module.binder.Binder;
import me.espryth.commons.universal.module.binder.SimpleBinder;
import me.espryth.commons.universal.module.container.Container;
import me.espryth.commons.universal.module.container.SimpleContainer;

public abstract class AbstractModule implements Module{

    protected Container container;
    protected Binder binder;

    public AbstractModule() {
        this.container = new SimpleContainer();
        this.binder = new SimpleBinder(container);
    }

    protected void install(Module module) {
        module.setBinder(binder);
        module.configure();
    }

    @Override
    public Container getContainer() {
        return container;
    }

    @Override
    public void setBinder(Binder binder) {
        this.binder = binder;
    }
}
