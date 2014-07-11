package com.arnimaack.servicetestexample.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

/**
 * Created by amaack on 7/10/14.
 */
public class ServiceResponseUtils {
    public static void createFilterAndRegisterReceiver(Context context, BroadcastReceiver broadCastReceiver, String action, String category) {
        //Register listener on success of ActivateUser Service
        IntentFilter mStatusIntentFilter = new IntentFilter();
        mStatusIntentFilter.addAction(action);
        mStatusIntentFilter.addCategory(category);

        // Registers the ResponseReceiver and its intent filters
        context.registerReceiver(
                broadCastReceiver,
                mStatusIntentFilter);
    }
}
