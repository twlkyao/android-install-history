package com.solshen.install.history.model;

public class History {
    private long   dbId;
    private String packageName;
    private int    versionCode;
    private String versionName;
    private String installEvent;
    /**
     * UTC timezone aligned event time.
     */
    private long   eventTime;

    public long getDbId() {
        return dbId;
    }

    public long getEventTime() {
        return eventTime;
    }

    public String getInstallEvent() {
        return installEvent;
    }

    public String getPackageName() {
        return packageName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public void setInstallEvent(String installEvent) {
        this.installEvent = installEvent;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}
