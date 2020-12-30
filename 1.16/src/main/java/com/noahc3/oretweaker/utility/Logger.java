package com.noahc3.oretweaker.utility;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

public class Logger {

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger();

    public static void log(Level logLevel, Object object) {
        LOGGER.log(logLevel, String.valueOf(object));
    }

    public static void info(Object object) {
        log(Level.INFO, object);
    }
}
