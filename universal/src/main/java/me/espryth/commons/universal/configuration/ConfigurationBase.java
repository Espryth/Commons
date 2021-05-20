package me.espryth.commons.universal.configuration;

import java.io.File;

public interface ConfigurationBase {

    Object get(String path);

    void set(String path, Object value);

    File getFile();

}
