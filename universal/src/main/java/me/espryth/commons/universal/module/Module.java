package me.espryth.commons.universal.module;

import me.espryth.commons.universal.module.binder.Binder;

public interface Module {

    String DEFAULT = "DEFAULT";

    void configure();

    void setBinder(Binder binder);

}
