package me.espryth.commons.universal.configuration;

import me.espryth.commons.universal.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public final class ConfigurationHolder {

    private final ConfigClass configClass;
    private final Map<String, String[]> headers;
    private final Map<String, Object> defaultValues;

    public ConfigurationHolder(ConfigClass configClass) {
        this.configClass = configClass;
        this.headers = new HashMap<>();
        this.defaultValues = new HashMap<>();

        getDefaultValues();
        writeDefaultValues();

    }

    private void getDefaultValues() {
        try {
            for(Field field : configClass.getClass().getDeclaredFields()) {
                if(field.isAnnotationPresent(Config.class)) {
                    field.setAccessible(true);

                    Config config = field.getAnnotation(Config.class);

                    String path = config.path();
                    Object defaultValue = field.get(configClass);

                    defaultValues.put(path, defaultValue);

                    if(config.desc().length > 0) {
                        headers.put(path, config.desc());
                    }
                }
            }

        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }
    }

    private void writeDefaultValues() {

        try {

            File file = configClass.getBase().getFile();
            FileWriter fileWriter = new FileWriter(file);

            StringBuilder dataToWrite = new StringBuilder();

            for(String path : defaultValues.keySet()) {

                if(configClass.getBase().get(path) != null) {
                    continue;
                }

                if(headers.containsKey(path)) {

                    for(String headerLine : headers.get(path)) {
                        dataToWrite
                                .append("# ")
                                .append(headerLine)
                                .append("\n");
                    }
                }

                dataToWrite
                        .append(path)
                        .append(": ")
                        .append(defaultValues.get(path))
                        .append("\n");
            }

            fileWriter.write(dataToWrite.toString());
            fileWriter.flush();

        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }
}
