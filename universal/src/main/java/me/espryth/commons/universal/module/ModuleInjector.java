package me.espryth.commons.universal.module;

import me.espryth.commons.universal.module.container.Container;

public interface ModuleInjector {

    void install(Module module);

    Container getContainer();
}
