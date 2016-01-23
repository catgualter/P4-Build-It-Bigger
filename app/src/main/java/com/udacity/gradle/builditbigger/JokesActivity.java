package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokesActivity extends AppCompatActivity {

    private static final String EXTRA_JOKE = "extra_joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        if (getIntent() != null) {
            ((TextView) findViewById(R.id.joke_textView)).setText(getIntent()
                    .getStringExtra(EXTRA_JOKE));
        }
    }


    public static Intent createIntent(Context context, String joke){
        Intent intent = new Intent(context,JokesActivity.class);
        intent.putExtra(EXTRA_JOKE,joke);
        return intent;
    }
}