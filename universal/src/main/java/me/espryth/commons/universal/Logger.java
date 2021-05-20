package me.espryth.commons.universal;

public final class Logger {

    public static void log(Level level, String prefix, String message) {

        StringBuilder builder = new StringBuilder();

        if(!level.getPrefix().isEmpty()) {
            builder.append(level.getPrefix())
                    .append("\\s+");
        }
        if(!prefix.isEmpty()) {
            builder.append(prefix)
                    .append("\\s+");
        }

        builder.append(message);

        System.out.println(builder.toString());
    }

    public static void log(Level level, String message) {
        log(level, "", message);
    }
    public static void log(String prefix, String message) {
        log(Level.NONE, prefix, message);
    }

    public static void log(String message) {
        log(Level.NONE, message);
    }

    public enum Level {
        NONE(""),
        WARNING("[WARNING]"),
        INFO("[INFO]"),
        SEVERE("[SEVERE]");

        private final String prefix;

        Level(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return prefix;
        }
    }
}
