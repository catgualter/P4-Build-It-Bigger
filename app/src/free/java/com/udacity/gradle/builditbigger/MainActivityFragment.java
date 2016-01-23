package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.anagualter.jokepreview.JokePreviewActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.tasks.AsyncJokeLibraryTask;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {


    private  AdView mAdView = null;
    private InterstitialAd mInterstitialAd = null;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mAdView = (AdView) root.findViewById(R.id.adView);
        requestNewAdBanner();


        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(R.string
                .interstitial_ad_unit_id));
        requestNewInterstitial();
        Button mTellJokeButton = (Button)root.findViewById(R.id.joke_button);
        mTellJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    retrieveJoke();

                }
            }
        });

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();

                // Reload interstitial Ad.
                requestNewInterstitial();

                // Retrieve joke from GCE module.
                retrieveJoke();
            }
        });

        return root;
    }

    private void requestNewAdBanner() {

        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    private void retrieveJoke() {
        AsyncJokeLibraryTask.AsyncJokeDownloadListener listener =
                new AsyncJokeLibraryTask.AsyncJokeDownloadListener() {
                    @Override
                    public void onAsyncJokeDownloadCompleted(String joke) {
                        if(!TextUtils.isEmpty(joke)) {
                            startActivity(JokePreviewActivity.createIntent(getActivity(), joke));
                        }else {

                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                };
        AsyncJokeLibraryTask asyncJokeLibraryTask = new
                AsyncJokeLibraryTask(getActivity());
        asyncJokeLibraryTask.showProgressDialog(true);
        asyncJokeLibraryTask.setAsyncJokeDownloadListener(listener);
        asyncJokeLibraryTask.downloadRandomJoke();
    }


}
