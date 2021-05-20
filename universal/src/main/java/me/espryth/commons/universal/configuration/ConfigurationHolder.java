package me.espryth.commons.universal.configuration;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public final class ConfigurationHolder {

    private final ConfigClass configClass;
    private final Map<String, String[]> headers;
    private final Map<String, ConfigValue<?>> defaultValues;

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

                field.setAccessible(true);

                if(field.isAnnotationPresent(ConfigIgnore.class)) {
                    continue;
                }

                ConfigValue<?> value = (ConfigValue<?>) field.get(configClass);

                if(field.isAnnotationPresent(Comment.class)) {
                    Comment comment = field.getAnnotation(Comment.class);
                    headers.put(value.getPath(), comment.value());
                }

                defaultValues.put(value.getPath(), value);
            }

        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }
    }

    private void writeDefaultValues() {

        FileWriter fileWriter = null;

        try {

            ConfigurationBase base = configClass.getBase();
            fileWriter = new FileWriter(base.getFile());

            for(String path : defaultValues.keySet()) {

                if(base.contains(path)) {

                    ConfigValue<?> configValue = defaultValues.get(path);
                    configValue.setValue(base.get(path));

                }

                StringBuilder dataToWrite = new StringBuilder();

                if(headers.containsKey(path)) {

                    for(String headerLine : headers.get(path)) {
                        dataToWrite
                                .append("# ")
                                .append(headerLine)
                                .append("\n");
                    }
                }

                fileWriter.write(dataToWrite.toString());
                fileWriter.flush();

                base.set(path, defaultValues.get(path).getValue());
                base.save();
                base.reload();

                System.out.println("COLA");

            }

        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            if(fileWriter != null) {
                try {
                    fileWriter.close();
                } catch(IOException ignored) {
                }
            }
        }
    }

    public void reload() {
        writeDefaultValues();
    }

}
