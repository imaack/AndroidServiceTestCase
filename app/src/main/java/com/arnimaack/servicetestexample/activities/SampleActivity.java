package com.arnimaack.servicetestexample.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.arnimaack.servicetestexample.R;
import com.arnimaack.servicetestexample.services.DownloadDataService;
import com.arnimaack.servicetestexample.services.ServiceResponseUtils;


public class SampleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.v(getClass().getSimpleName(), "Activity BroadcastReceiver called");
                final String data = intent.getStringExtra(DownloadDataService.RESPONSE_PARAM_DATA);
            }
        };
        ServiceResponseUtils.createFilterAndRegisterReceiver(this, broadcastReceiver, DownloadDataService.RESPONSE_SUCCCESS, Intent.CATEGORY_DEFAULT);

        DownloadDataService.startActionDownloadData(this,"http://date.jsontest.com");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sample, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
