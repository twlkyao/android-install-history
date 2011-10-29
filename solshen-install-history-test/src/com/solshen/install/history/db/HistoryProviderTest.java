package com.solshen.install.history.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.ProviderTestCase2;
import android.test.suitebuilder.annotation.MediumTest;

import com.solshen.install.history.model.History;

public class HistoryProviderTest extends ProviderTestCase2<HistoryProvider> {
    private Context           context;
    protected ContentResolver resolver;

    public HistoryProviderTest() {
        super(HistoryProvider.class, HistoryDatabase.AUTHORITY);
    }

    protected void assertDatabaseRowCount(String msg, Cursor c, int expectedCount) {
        assertNotNull(msg + " (cursor should not be null", c);
        assertTrue(msg + " (move to first, we are expecting at least 1 row)", c.moveToFirst());
        assertEquals(msg + " (row count)", expectedCount, c.getCount());
    }

    protected void assertDatabaseUriHasDBId(String type, Uri uri) {
        getDatabaseId(type, uri);
    }

    protected void freshDB() {
        HistoryDatabaseHelper helper = HistoryDatabaseHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        helper.rebuild(db); // force rebuild
    }

    protected long getDatabaseId(String type, Uri uri) {
        assertNotNull(type + " should have had a uri", uri);
        long dbId = Long.parseLong(uri.getLastPathSegment());
        assertTrue(type + " DB Id [" + dbId + "] should be >= 0", dbId >= 0);
        return dbId;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        this.context = getMockContext();
        this.resolver = getMockContentResolver();
        freshDB();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @MediumTest
    public void testInsertHistory() {
        History hist = new History();
        long now = System.currentTimeMillis();
        hist.setEventTime(now);
        hist.setInstallEvent("INSTALL");
        hist.setPackageName("test.package.name");
        hist.setVersionCode(1);
        hist.setVersionName("1.0");

        ContentValues values = HistoryTable.asValues(hist);
        Uri uri = resolver.insert(HistoryTable.CONTENT_URI, values);
        assertDatabaseUriHasDBId("History", uri);
    }
}
