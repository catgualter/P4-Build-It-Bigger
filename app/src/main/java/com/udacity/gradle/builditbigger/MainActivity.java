package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.udacity.gradle.builditbigger.tasks.AsyncJokeLibraryTask;


public class MainActivity extends AppCompatActivity implements AsyncJokeLibraryTask.AsyncJokeDownloadListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void tellJoke(View view){
        AsyncJokeLibraryTask asyncJokeLibraryTask = new AsyncJokeLibraryTask(MainActivity.this);
        asyncJokeLibraryTask.showProgressDialog(true);
        asyncJokeLibraryTask.setAsyncJokeDownloadListener(this);
        asyncJokeLibraryTask.downloadRandomJoke();
//        requestInterstitialAd();
    }




    @Override
    public void onAsyncJokeDownloadCompleted(String joke) {

        displayJoke(joke);
    }


    private void displayJoke(String joke) {
        if(!TextUtils.isEmpty(joke)) {
            startActivity(JokesActivity.createIntent(getApplicationContext(),
                    joke));
        }else {

            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT)
                    .show();
        }

    }
}
