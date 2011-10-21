package com.solshen.install.history.logging;

import android.util.Log;

/**
 * Logging with formatting! \o/
 */
public class Logger {
    private String name;
    private String tag;

    public Logger(Class<?> clazz) {
        this.name = clazz.getName();
        int lastDot = name.lastIndexOf('.');
        if (lastDot > 0) {
            this.tag = name.substring(lastDot);
        } else {
            this.tag = name;
        }
    }

    public void debug(String format, Object... args) {
        Log.d(tag, String.format(format, args));
    }

    public void info(String format, Object... args) {
        Log.i(tag, String.format(format, args));
    }

    public void warn(String format, Object... args) {
        Log.w(tag, String.format(format, args));
    }

    public void warn(Throwable t, String format, Object... args) {
        Log.w(tag, String.format(format, args), t);
    }
}
