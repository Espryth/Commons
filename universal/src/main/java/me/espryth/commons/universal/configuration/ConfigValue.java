package me.espryth.commons.universal.configuration;

public final class ConfigValue<T extends Object> {

    private final String path;

    private final T defaultValue;

    private T value;

    public ConfigValue(String path,
                       T defaultValue) {
        this.path = path;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    public String getPath() {
        return path;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public T getValue() {
        return value;
    }

    @SuppressWarnings("unchecked")
    public void setValue(Object value) {
        this.value = (T) value;
    }
}
