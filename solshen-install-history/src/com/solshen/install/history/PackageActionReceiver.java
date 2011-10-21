package com.solshen.install.history;

import java.util.HashSet;
import java.util.Set;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PackageActionReceiver extends BroadcastReceiver {
    private Set<String> interestingActions;

    public PackageActionReceiver() {
        super();
        interestingActions = new HashSet<String>();
        interestingActions.add(Intent.ACTION_PACKAGE_ADDED);
        interestingActions.add(Intent.ACTION_PACKAGE_CHANGED);
        interestingActions.add(Intent.ACTION_PACKAGE_DATA_CLEARED);
        interestingActions.add(Intent.ACTION_PACKAGE_INSTALL);
        interestingActions.add(Intent.ACTION_PACKAGE_REMOVED);
        interestingActions.add(Intent.ACTION_PACKAGE_REPLACED);
        interestingActions.add(Intent.ACTION_PACKAGE_RESTARTED);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action.equals(Intent.ACTION_PACKAGE_ADDED)) {

        }
    }
}
