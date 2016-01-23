package com.udacity.gradle.builditbigger.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.anagualter.jokes.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.R;

import java.io.IOException;


public class AsyncJokeLibraryTask {

    private Context mContext;
    private AsyncJokeDownloadListener mAsyncJokeDownloadListener;
    private boolean isProgressDialogActivated = false;

    public AsyncJokeLibraryTask(Context context){
        this.mContext = context;
    }

    public void showProgressDialog(boolean activateProgressDialog){
        this.isProgressDialogActivated = activateProgressDialog;
    }
    public void downloadRandomJoke(){
        JokeAsyncTask jokeAsyncTask = new JokeAsyncTask();
        jokeAsyncTask.execute();
    }

    private class JokeAsyncTask extends AsyncTask<Void,Void,String> {

        private MyApi mService = null;
        private ProgressDialog progressDialog;

        public JokeAsyncTask(){
            progressDialog = new ProgressDialog(mContext);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(isProgressDialogActivated){
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage(mContext.getString(R.string
                        .download_message));
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            if(mService == null) {
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport()
                        , new AndroidJsonFactory(), null)
                        .setRootUrl("https://p4-builditbigger-1198.appspot" +
                                ".com/_ah/api/")


                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                builder.setApplicationName(mContext.getString(R.string
                        .app_name));
                mService = builder.build();
            }


            try {
                return mService.randomJoke().execute().getData();
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            mAsyncJokeDownloadListener.onAsyncJokeDownloadCompleted(s);
            if(isProgressDialogActivated)
                progressDialog.dismiss();
        }
    }

    public void setAsyncJokeDownloadListener(AsyncJokeDownloadListener mAsyncJokeDownloadListener) {
        this.mAsyncJokeDownloadListener = mAsyncJokeDownloadListener;
    }

    public interface AsyncJokeDownloadListener {
        void onAsyncJokeDownloadCompleted(String joke);
    }

}