package com.example.androidlibrary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


public class JokeActivity extends AppCompatActivity {

    private static final String JOKE_EXTRA = "jokeExtra";

    public static void startWith(Context context, String joke) {
        Intent intent = new Intent(context, JokeActivity.class);
        intent.putExtra(JOKE_EXTRA, joke);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        String joke = getIntent().getStringExtra(JOKE_EXTRA);
        Toast.makeText(this, joke, Toast.LENGTH_SHORT).show();
    }
}
