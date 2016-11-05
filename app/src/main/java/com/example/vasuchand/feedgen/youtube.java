
package com.example.vasuchand.feedgen;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.vasuchand.feedgen.Login.Session;

import com.example.vasuchand.feedgen.youtubefetch.Auth;
import com.example.vasuchand.feedgen.youtubefetch.Constants;
import com.example.vasuchand.feedgen.youtubefetch.Utils;
import com.example.vasuchand.feedgen.youtubefetch.VideoData;
import com.example.vasuchand.feedgen.youtubefetch.youadapter;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.common.collect.Lists;
import com.nostra13.universalimageloader.core.ImageLoader;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;



import java.io.BufferedReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


public class youtube extends AppCompatActivity{
    private YouTubePlayerView youTubePlayerView;
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    Session session;
    GoogleAccountCredential credential;
    com.google.api.services.youtube.YouTube service;
    Context context;
    final JsonFactory jsonFactory = new GsonFactory();

    private static final String PREF_ACCOUNT_NAME = "vasu chand";
    public static final String[] SCOPES = {Scopes.PROFILE, YouTubeScopes.YOUTUBE};

    private ImageLoader mImageLoader;
    private String mChosenAccountName;
    private Uri mFileURI = null;
    int resultCode;
    GoogleApiAvailability googleAPI;
    public static final String ACCOUNT_KEY = "accountName";
    private static final int REQUEST_GOOGLE_PLAY_SERVICES = 0;
    private static final int REQUEST_GMS_ERROR_DIALOG = 1;
    private static final int REQUEST_ACCOUNT_PICKER = 2;
    private static final int REQUEST_AUTHORIZATION = 3;
    private RecyclerView.Adapter adapter2;
    private RecyclerView recyclerView;
    public static YouTube y1;
    public config config;

    final HttpTransport transport = AndroidHttp.newCompatibleTransport();
    List<String> scopes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        context = youtube.this;
        session = new Session(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        credential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(Auth.SCOPES));
        // set exponential backoff policy
        credential.setBackOff(new ExponentialBackOff());
        scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");

        if (savedInstanceState != null) {
            mChosenAccountName = savedInstanceState.getString(ACCOUNT_KEY);
        } else {
            chooseAccount();
            loadAccount();
           // System.out.println("im runungug");
        }

        credential.setSelectedAccountName(mChosenAccountName);
       // loadUploadedVideos();
        System.out.println(mChosenAccountName);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode == Activity.RESULT_OK) {
                    haveGooglePlayServices();
                } else {
                    checkGooglePlayServicesAvailable();
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode != Activity.RESULT_OK) {
                    chooseAccount();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == Activity.RESULT_OK && data != null
                        && data.getExtras() != null) {
                    String accountName = data.getExtras().getString(
                            AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        mChosenAccountName = accountName;
                        credential.setSelectedAccountName(accountName);
                        saveAccount();
                       loadUploadedVideos();
                        //getData();
                        System.out.println("we are done");
                    }
                }
                break;

        }
    }
    private void saveAccount() {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(this);
        sp.edit().putString(ACCOUNT_KEY, mChosenAccountName).commit();
    }
    public void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        runOnUiThread(new Runnable() {
            public void run() {
                Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
                        connectionStatusCode, youtube.this,
                        REQUEST_GOOGLE_PLAY_SERVICES);
                dialog.show();
            }
        });
    }


    private boolean checkGooglePlayServicesAvailable() {
        final int connectionStatusCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
            return false;
        }
        return true;
    }

    private void haveGooglePlayServices() {
        // check if there is already an account selected
        if (credential.getSelectedAccountName() == null) {
            // ask user to choose account
            chooseAccount();
        }
    }

    private void loadUploadedVideos() {
        if (mChosenAccountName == null) {
            return;
        }

       // setProgressBarIndeterminateVisibility(true);
        new AsyncTask<Void, Void, List<VideoData>>() {
            @Override
            protected List<VideoData> doInBackground(Void... voids) {

                YouTube youtube = new YouTube.Builder(transport, jsonFactory,
                        credential).setApplicationName(Constants.APP_NAME)
                        .build();

                try {


                    List<VideoData> videos = new ArrayList<VideoData>();


                    VideoListResponse videoListResponse = youtube.videos().
                            list("snippet,statistics").setChart("mostPopular").setMaxResults((long)10).setRegionCode("IN").execute();

                    List<Video> videoList = videoListResponse.getItems();
                    if (videoList.isEmpty()) {
                        System.out.println("Can't find a video with ID: ");
                        return null;
                    }

                    for (Video video : videoListResponse.getItems()) {

                        // System.out.println(video.getId());
                        // System.out.println(video.getSnippet().getThumbnails().getDefault().getUrl());
                        VideoData videoData = new VideoData();
                        videoData.setVideo(video);
                        videos.add(videoData);

                    }
                    System.out.println(videoList.size());
                    System.out.println(videoList);

                    return videos;

                } catch (final GooglePlayServicesAvailabilityIOException availabilityException) {
                    showGooglePlayServicesAvailabilityErrorDialog(availabilityException
                            .getConnectionStatusCode());
                } catch (UserRecoverableAuthIOException userRecoverableException) {
                    startActivityForResult(
                            userRecoverableException.getIntent(),
                            REQUEST_AUTHORIZATION);
                } catch (IOException e) {
                    Utils.logAndShow(youtube.this, Constants.APP_NAME, e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<VideoData> videos) {
                setProgressBarIndeterminateVisibility(false);

                if (videos == null) {
                    return;
                }

                adapter2 = new youadapter(context,videos);
                recyclerView.setAdapter(adapter2);

                //mUploadsListFragment.setVideos(videos);
            }

        }.execute((Void) null);
    }








    /** Check that Google Play services APK is installed and up to date. */

    private void loadAccount() {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(this);
        mChosenAccountName = sp.getString(ACCOUNT_KEY, null);
        invalidateOptionsMenu();
    }

    private void chooseAccount() {
        startActivityForResult(credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
    }


}
