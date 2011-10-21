package com.solshen.install.history.db;

import android.net.Uri;

public final class HistoryDatabase {
    public static final String AUTHORITY   = "com.solshen.history";

    /**
     * The content:// style URL for this database
     */
    protected static final Uri CONTENT_URI = Uri.parse("content://" + HistoryDatabase.AUTHORITY);

    public static final String DB_NAME     = "history.db";
    public static final int    DB_VERSION  = 1;
}
