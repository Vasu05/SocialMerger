package com.example.vasuchand.feedgen.youtubefetch;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.vasuchand.feedgen.MainActivity;
import com.example.vasuchand.feedgen.R;
import com.example.vasuchand.feedgen.youtube;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnFullscreenListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;


public class PlayActivity extends Activity implements
        PlayerStateChangeListener, OnFullscreenListener {

    private static final String YOUTUBE_FRAGMENT_TAG = "youtube";
    final HttpTransport transport = AndroidHttp.newCompatibleTransport();
    final JsonFactory jsonFactory = new GsonFactory();
    GoogleAccountCredential credential;
    private YouTubePlayer mYouTubePlayer;
    private boolean mIsFullScreen = false;
    private Intent intent;

    public PlayActivity() {
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void directLite(View view) {
        this.setResult(RESULT_OK, intent);
        finish();
    }

    public void panToVideo(final String youtubeId) {
        popPlayerFromBackStack();
        YouTubePlayerFragment playerFragment = YouTubePlayerFragment
                .newInstance();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.detail_container, playerFragment,
                        YOUTUBE_FRAGMENT_TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null).commit();
        playerFragment.initialize(Auth.KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(
                            YouTubePlayer.Provider provider,
                            YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.loadVideo(youtubeId);
                        mYouTubePlayer = youTubePlayer;
                        youTubePlayer
                                .setPlayerStateChangeListener(PlayActivity.this);
                        youTubePlayer
                                .setOnFullscreenListener(PlayActivity.this);
                    }

                    @Override
                    public void onInitializationFailure(
                            YouTubePlayer.Provider provider,
                            YouTubeInitializationResult result) {
                        showErrorToast(result.toString());
                    }
                });
    }

    public boolean popPlayerFromBackStack() {
        if (mIsFullScreen) {
            mYouTubePlayer.setFullscreen(false);
            return false;
        }
        if (getFragmentManager().findFragmentByTag(YOUTUBE_FRAGMENT_TAG) != null) {
            getFragmentManager().popBackStack();
            return false;
        }
        return true;
    }

    @Override
    public void onAdStarted() {
    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {
        showErrorToast(errorReason.toString());
    }

    private void showErrorToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onLoaded(String arg0) {
    }

    @Override
    public void onLoading() {
    }

    @Override
    public void onVideoEnded() {
        // popPlayerFromBackStack();
    }

    @Override
    public void onVideoStarted() {
    }

    @Override
    public void onFullscreen(boolean fullScreen) {
        mIsFullScreen = fullScreen;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_play);
        intent = getIntent();
       // Button submitButton = (Button) findViewById(R.id.submit_button);

        String youtubeId = intent.getStringExtra("youtubeId");
        panToVideo(youtubeId);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //NavUtils.navigateUpFromSameTask(this);
    }


}
