package me.espryth.commons.universal;

import java.util.logging.Level;

public final class Logger extends java.util.logging.Logger {

    private static final Logger RAW_LOGGER = new Logger();

    public Logger() {
        super("", null);
    }

    public static void log(String message, Level level) {
        RAW_LOGGER.log(level, message);
    }

}
