package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.example.androidlibrary.JokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private static final String TAG = EndpointsAsyncTask.class.getSimpleName();

    private MyApi myApiService;
    private Context context;

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        context = params[0].first;
        String url = params[0].second;

        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(url)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            myApiService = builder.build();
        }

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            Log.e(TAG, "Error executing asynctask", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            JokeActivity.startWith(context, result);
        } else {
            Toast.makeText(context, "Error getting joke", Toast.LENGTH_LONG).show();
        }
    }
}
