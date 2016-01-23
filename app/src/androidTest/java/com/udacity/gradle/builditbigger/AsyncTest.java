package com.udacity.gradle.builditbigger;


import android.test.AndroidTestCase;
import android.text.TextUtils;

import com.udacity.gradle.builditbigger.tasks.AsyncJokeLibraryTask;



public class AsyncTest extends AndroidTestCase {
    public void testAsyncJokeDownloadResponse(){
        AsyncJokeLibraryTask asyncJokeLibraryTask = new AsyncJokeLibraryTask(getContext());
        asyncJokeLibraryTask.downloadRandomJoke();
        asyncJokeLibraryTask.showProgressDialog(false);
        asyncJokeLibraryTask.setAsyncJokeDownloadListener(new AsyncJokeLibraryTask.AsyncJokeDownloadListener() {
            @Override
            public void onAsyncJokeDownloadCompleted(String joke) {

                assertEquals(true,!TextUtils.isEmpty(joke));
            }
        });
    }
}