package com.solshen.install.history.db;

import android.database.Cursor;

public class DataCursor {
    private Cursor c;

    public DataCursor(Cursor cursor) {
        this.c = cursor;
    }

    public int getInt(String colName) {
        int colIdx = c.getColumnIndexOrThrow(colName);
        return c.getInt(colIdx);
    }

    public long getLong(String colName) {
        int colIdx = c.getColumnIndexOrThrow(colName);
        return c.getLong(colIdx);
    }

    public String getString(String colName) {
        int colIdx = c.getColumnIndexOrThrow(colName);
        return c.getString(colIdx);
    }

    public long getUtcTimestamp(String colName) {
        int colIdx = c.getColumnIndexOrThrow(colName);
        return c.getLong(colIdx);
    }
}
