package com.solshen.install.history.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.solshen.install.history.logging.Logger;

public class HistoryDatabaseHelper extends SQLiteOpenHelper {
    private static final Logger          LOG       = new Logger(HistoryDatabaseHelper.class);
    private static HistoryDatabaseHelper singleton = null;

    public static synchronized HistoryDatabaseHelper getInstance(Context context) {
        if (singleton == null) {
            singleton = new HistoryDatabaseHelper(context);
        }
        return singleton;
    }

    public HistoryDatabaseHelper(Context context) {
        super(context, HistoryDatabase.DB_NAME, null, HistoryDatabase.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sql;

        // History Table
        sql = new StringBuilder();
        sql.append("CREATE TABLE ").append(HistoryTable.TABLE);
        sql.append(" ( ").append(HistoryTable._ID).append(" INTEGER PRIMARY KEY");
        sql.append(" , ").append(HistoryTable.PACKAGE_NAME).append(" TEXT NOT NULL");
        sql.append(" , ").append(HistoryTable.VERSION_CODE).append(" INTEGER");
        sql.append(" , ").append(HistoryTable.VERSION_NAME).append(" TEXT");
        sql.append(" , ").append(HistoryTable.INSTALL_EVENT).append(" TEXT");
        sql.append(" , ").append(HistoryTable.EVENT_TIME).append(" LONG");
        sql.append(" );");
        db.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            LOG.warn("Upgrading from version %d to %d, which will destroy all old data.", oldVersion, newVersion);
            rebuild(db);
        }
    }

    public void rebuild(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + HistoryTable.TABLE);
        onCreate(db);
    }
}
