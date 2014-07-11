package com.arnimaack.servicetestexample.tests;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.test.ServiceTestCase;
import android.util.Log;

import com.arnimaack.servicetestexample.services.DownloadDataService;
import com.arnimaack.servicetestexample.services.ServiceResponseUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by amaack on 7/10/14.
 */
public class DownloadDataServiceTest extends ServiceTestCase<DownloadDataService> {


    public DownloadDataServiceTest() {
        super(DownloadDataService.class);
    }

    public void testGetDateJson(){
        final CountDownLatch latch = new CountDownLatch(1);

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.v(getClass().getSimpleName(), "Test BroadcastReceiver called");
                final String data = intent.getStringExtra(DownloadDataService.RESPONSE_PARAM_DATA);
                assertFalse("".equals(data));
                latch.countDown();
            }
        };
        ServiceResponseUtils.createFilterAndRegisterReceiver(getContext(), broadcastReceiver, DownloadDataService.RESPONSE_SUCCCESS, Intent.CATEGORY_DEFAULT);

        // starts the service
        DownloadDataService.startActionDownloadData(getContext(),"http://date.jsontest.com");
        Log.v(getClass().getSimpleName(), "Service Started");
        try {
            latch.await(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            fail();
        }
        Log.v(getClass().getSimpleName(), "Test completed");

    }




}
