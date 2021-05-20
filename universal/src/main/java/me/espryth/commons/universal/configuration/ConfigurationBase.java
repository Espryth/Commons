package me.espryth.commons.universal.configuration;

import java.io.File;

public interface ConfigurationBase {

    void save();

    void reload();

    Object get(String path);

    void set(String path, Object value);

    boolean contains(String path);

    File getFile();

}
