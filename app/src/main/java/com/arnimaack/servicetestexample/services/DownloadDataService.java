package com.arnimaack.servicetestexample.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
*/

/**
 * helper methods.
*/
public class DownloadDataService extends IntentService {

    public static final String ACTION_DOWNLOAD_DATA = "com.arnimaack.servicetestexample.services.DownloadDataService.ACTION_DOWNLOAD_DATA";
    public static final String EXTRA_URL = "com.arnimaack.servicetestexample.services.DownloadDataService.EXTRA_URL";

    public static final String RESPONSE_SUCCCESS = "com.arnimaack.servicetestexample.services.response.RESPONSE_SUCCCESS";
    public static final String RESPONSE_PARAM_DATA = "com.arnimaack.servicetestexample.services.response.RESPONSE_PARAM_DATA";

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionDownloadData(Context context, String url) {
        Intent intent = new Intent(context, DownloadDataService.class);
        intent.setAction(ACTION_DOWNLOAD_DATA);
        intent.putExtra(EXTRA_URL, url);
        context.startService(intent);
    }


    public DownloadDataService() {
        super("DownloadDataService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DOWNLOAD_DATA.equals(action)) {
                final String url = intent.getStringExtra(EXTRA_URL);
                handleActionDownloadData(url);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionDownloadData(String urlString) {
        //set URL and open connection
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            DataOutputStream output = null;
            InputStream input = null;

            connection.setUseCaches(false);
            connection.setAllowUserInteraction(false);
            //connection.setConnectTimeout(timeout);
            //connection.setReadTimeout(timeout);

            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-length", "0");

            //connect
            connection.connect();

            //read response
            input = connection.getInputStream();

            String data = convertStreamToString(input);
            Log.v(this.getClass().getSimpleName(),data);
            reportDataDownloadSuccess(data);
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //convenience method for when we need human readable access API response
    protected String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private void reportDataDownloadSuccess(String jsonString) {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(RESPONSE_SUCCCESS);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(RESPONSE_PARAM_DATA, jsonString);
        getBaseContext().sendBroadcast(broadcastIntent);
    }

}
