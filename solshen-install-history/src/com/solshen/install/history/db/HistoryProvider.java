package com.solshen.install.history.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.solshen.install.history.logging.Logger;

public class HistoryProvider extends ContentProvider {
    private static final Logger     LOG                  = new Logger(HistoryProvider.class);

    // URI History Events
    private static final int        ALL_HISTORY_EVENTS   = 10;
    private static final int        SINGLE_HISTORY_EVENT = 11;

    private static final UriMatcher URIMATCHER;

    static {
        URIMATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URIMATCHER.addURI(HistoryDatabase.AUTHORITY, "history", ALL_HISTORY_EVENTS);
        URIMATCHER.addURI(HistoryDatabase.AUTHORITY, "history/#", SINGLE_HISTORY_EVENT);
    }

    private SQLiteDatabase          db;

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String table;
        String where = selection;
        String whereArgs[] = selectionArgs;

        switch (URIMATCHER.match(uri)) {
        case ALL_HISTORY_EVENTS:
            table = HistoryTable.TABLE;
            break;
        case SINGLE_HISTORY_EVENT:
            table = HistoryTable.TABLE;
            where = DBUtil.toPrependedWhere(selection, HistoryTable._ID + " = ?");
            whereArgs = DBUtil.toPrependedWhereArgs(selectionArgs, uri.getLastPathSegment());
            break;
        default:
            throw new IllegalArgumentException("Unsupported content URI: " + uri);
        }

        int count = db.delete(table, where, whereArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        LOG.debug("Deleted %d entries from %s", count, table);
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        switch (URIMATCHER.match(uri)) {
        case ALL_HISTORY_EVENTS:
            return HistoryTable.CONTENT_TYPE;
        case SINGLE_HISTORY_EVENT:
            return HistoryTable.CONTENT_ITEM_TYPE;
        default:
            throw new IllegalArgumentException("Unsupported content URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowid = -1;
        Uri baseUri = null;

        switch (URIMATCHER.match(uri)) {
        case ALL_HISTORY_EVENTS:
        case SINGLE_HISTORY_EVENT:
            rowid = db.insert(HistoryTable.TABLE, "", values);
            baseUri = HistoryTable.CONTENT_URI;
            break;
        default:
            throw new IllegalArgumentException("Unsupported content URI: " + uri);
        }

        if (rowid > 0) {
            Uri nuri = ContentUris.withAppendedId(baseUri, rowid);
            getContext().getContentResolver().notifyChange(nuri, null);
            return nuri;
        }

        throw new SQLException("Failed to insert row into + " + uri);
    }

    @Override
    public boolean onCreate() {
        db = HistoryDatabaseHelper.getInstance(getContext()).getWritableDatabase();
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String tables = null;
        String columns[] = projection;
        String where = selection;
        String whereArgs[] = selectionArgs;
        String groupBy = null;
        String having = null;
        String orderBy = sortOrder;

        switch (URIMATCHER.match(uri)) {
        case ALL_HISTORY_EVENTS:
            tables = HistoryTable.TABLE;
            orderBy = DBUtil.toOrderBy(sortOrder, HistoryTable.DEFAULT_SORT_ORDER);
            break;
        case SINGLE_HISTORY_EVENT:
            tables = HistoryTable.TABLE;
            where = DBUtil.toPrependedWhere(selection, HistoryTable._ID + " = ?");
            whereArgs = DBUtil.toPrependedWhereArgs(selectionArgs, uri.getLastPathSegment());
            orderBy = DBUtil.toOrderBy(sortOrder, HistoryTable.DEFAULT_SORT_ORDER);
            break;
        default:
            throw new IllegalArgumentException("Unsupported content URI: " + uri);
        }

        Cursor c = db.query(tables, columns, where, whereArgs, groupBy, having, orderBy);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String table = null;
        String where = selection;
        String whereArgs[] = selectionArgs;

        switch (URIMATCHER.match(uri)) {
        case ALL_HISTORY_EVENTS:
            table = HistoryTable.TABLE;
            break;
        case SINGLE_HISTORY_EVENT:
            table = HistoryTable.TABLE;
            where = DBUtil.toPrependedWhere(selection, HistoryTable._ID + " = ?");
            whereArgs = DBUtil.toPrependedWhereArgs(selectionArgs, uri.getLastPathSegment());
            break;
        default:
            throw new IllegalArgumentException("Unsupported content URI: " + uri);
        }

        int count = db.update(table, values, where, whereArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

}
