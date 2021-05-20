package me.espryth.commons.universal.module;

import me.espryth.commons.universal.module.binder.Binder;
import me.espryth.commons.universal.module.container.Container;

public interface Module {

    String DEFAULT = "DEFAULT";

    void configure();

    Container getContainer();

    void setBinder(Binder binder);

}
