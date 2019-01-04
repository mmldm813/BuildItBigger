package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask<String, Void, String> {
    private static final String TAG = EndpointsAsyncTask.class.getSimpleName();

    private AsyncTaskCompletion asyncTaskCompletion;
    private MyApi myApiService;

    public EndpointsAsyncTask(AsyncTaskCompletion asyncTaskCompletion) {
        this.asyncTaskCompletion = asyncTaskCompletion;
    }

    @Override
    protected String doInBackground(String... params) {
        String url = params[0];

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
        asyncTaskCompletion.onComplete(result);
    }
}
