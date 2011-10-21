package com.solshen.install.history.db;

import android.database.Cursor;
import android.text.TextUtils;

import com.solshen.install.history.logging.Logger;

/**
 * Backport of some useful methods introduced in Honeycomb version of {@link android.database.DatabaseUtils}
 */
public class DBUtil {
    private static final Logger LOG = new Logger(DBUtil.class);

    /**
     * Similar to Honeycomb {@link android.database.DatabaseUtils}.appendSelectionArgs(String[], String[])
     */
    public static String[] appendColumn(String[] columns, String extraColumn) {
        String ret[] = new String[columns.length + 1];
        System.arraycopy(columns, 0, ret, 0, columns.length);
        ret[columns.length] = extraColumn;
        return ret;
    }

    public static void close(Cursor c) {
        if (c == null) {
            return;
        }
        try {
            c.close();
        } catch (Throwable t) {
            LOG.warn(t, "while closing cursor: " + c);
        }
    }

    public static String toOrderBy(String sortOrder, String defaultSortOrder) {
        if (TextUtils.isEmpty(sortOrder)) {
            return defaultSortOrder;
        }
        return sortOrder;
    }

    /**
     * Similar of to Honeycomb {@link android.database.DatabaseUtils}.concatenateWhere(String, String), but with support
     * for java formatting with args
     */
    public static String toPrependedWhere(String original, String extra, Object... args) {
        StringBuilder where = new StringBuilder();
        where.append(String.format(extra, args));
        if (!TextUtils.isEmpty(original)) {
            where.append(" AND ( ").append(original).append(" ) ");
        }
        return where.toString();
    }

    /**
     * Similar to Honeycomb {@link android.database.DatabaseUtils}.appendSelectionArgs(String[], String[]), but with
     * support for java formatting with args
     */
    public static String[] toPrependedWhereArgs(String[] originalArgs, String... extraArgs) {
        String whereArgs[];

        if (originalArgs == null) {
            whereArgs = new String[extraArgs.length];
        } else {
            whereArgs = new String[originalArgs.length + extraArgs.length];
            System.arraycopy(originalArgs, 0, whereArgs, extraArgs.length, originalArgs.length);
        }
        System.arraycopy(extraArgs, 0, whereArgs, 0, extraArgs.length);
        return whereArgs;
    }
}
