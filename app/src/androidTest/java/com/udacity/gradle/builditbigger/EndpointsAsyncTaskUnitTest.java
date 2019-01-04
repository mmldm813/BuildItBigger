package com.udacity.gradle.builditbigger;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import okhttp3.mockwebserver.MockWebServer;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class EndpointsAsyncTaskUnitTest {
    private static final String FAKE_JOKE = "fake joke";
    private static final String FAKE_DATA = "{\"data\": \""+ FAKE_JOKE + "\"}\"";

    private MockWebServer webServer;
    private boolean isComplete;

    @Before
    public void setup() throws Exception {
        webServer = new MockWebServer();
        webServer.start(8080);
    }

    @Test
    public void verifyAsyncTask() {
        isComplete = false;
        webServer.setDispatcher(new MockServerDispatcher(null, 200, FAKE_DATA).new RequestDispatcher());

        new EndpointsAsyncTask(new AsyncTaskCompletion() {
            @Override
            public void onComplete(String str) {
                assertEquals("unexpected value", FAKE_JOKE, str);
                isComplete = true;
            }
        }).execute("http://localhost:8080/_ah/api/");

        while(isComplete == false) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                fail("error retrieving joke");
            }
        }
    }
}
