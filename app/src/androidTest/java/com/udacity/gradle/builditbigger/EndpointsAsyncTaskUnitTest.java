package com.udacity.gradle.builditbigger;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Pair;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

import okhttp3.mockwebserver.MockWebServer;

import static junit.framework.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class EndpointsAsyncTaskUnitTest {
    private static final String FAKE_JOKE = "fake joke";
    private static final String FAKE_DATA = "{\"data\": \""+ FAKE_JOKE + "\"}\"";

    private MockWebServer webServer;

    @Before
    public void setup() throws Exception {
        webServer = new MockWebServer();
        webServer.start(8080);
    }

    @Test
    public void verifyAsyncTask() {
        webServer.setDispatcher(new MockServerDispatcher(null, 200, FAKE_DATA).new RequestDispatcher());
        try {
            new EndpointsAsyncTask().execute(new Pair(InstrumentationRegistry.getTargetContext(),
                    "http://localhost:8080/_ah/api/")).get();
        } catch (InterruptedException | ExecutionException e) {
            fail("error retrieving joke");
        }
        fail("xxxx");
    }
}
