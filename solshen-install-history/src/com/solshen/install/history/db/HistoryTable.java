package com.solshen.install.history.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.solshen.install.history.model.History;

public class HistoryTable implements BaseColumns {
    /**
     * The content:// style URL for this table
     */
    public static final Uri    CONTENT_URI        = Uri.withAppendedPath(HistoryDatabase.CONTENT_URI, "history");

    /**
     * The MIME type of {@link #CONTENT_URI} providing a list of my history entries.
     */
    public static final String CONTENT_TYPE       = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.solshen.history";

    /**
     * The MIME type of a {@link #CONTENT_URI} sub-directory of a single history entry.
     */
    public static final String CONTENT_ITEM_TYPE  = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.solshen.history";

    public static final String DEFAULT_SORT_ORDER = "packageName ASC";
    public static final String TABLE              = "history";

    // The individual fields
    public static final String PACKAGE_NAME       = "packageName";
    public static final String VERSION_CODE       = "versionCode";
    public static final String VERSION_NAME       = "versionEvent";
    public static final String INSTALL_EVENT      = "installEvent";
    public static final String EVENT_TIME         = "eventTime";

    public static final String PROJECTION_FULL[]  = new String[] {
            PACKAGE_NAME, VERSION_CODE, VERSION_NAME, INSTALL_EVENT, EVENT_TIME
                                                  };

    public static ContentValues asValues(History history) {
        ContentValues values = new ContentValues();

        if (history.getDbId() >= 0) {
            values.put(_ID, history.getDbId());
        }

        values.put(PACKAGE_NAME, history.getPackageName());
        values.put(VERSION_CODE, history.getVersionCode());
        values.put(VERSION_NAME, history.getVersionName());
        values.put(INSTALL_EVENT, history.getInstallEvent());
        values.put(EVENT_TIME, history.getEventTime());

        return values;
    }

    public static History fromCursor(Cursor cursor) {
        History history = new History();

        DataCursor c = new DataCursor(cursor);
        history.setDbId(c.getLong(_ID));
        history.setPackageName(c.getString(PACKAGE_NAME));
        history.setVersionCode(c.getInt(VERSION_CODE));
        history.setVersionName(c.getString(VERSION_NAME));
        history.setInstallEvent(c.getString(INSTALL_EVENT));
        history.setEventTime(c.getUtcTimestamp(EVENT_TIME));

        return history;
    }
}
