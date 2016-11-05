/*
 * Copyright (c) 2013 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.vasuchand.feedgen.youtubefetch;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.vasuchand.feedgen.Login.Signup;
import com.example.vasuchand.feedgen.Login.credentials;
import com.example.vasuchand.feedgen.R;

import com.example.vasuchand.feedgen.youtube;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.PlusOneButton;
import com.google.android.gms.plus.model.people.Person;

import java.util.List;


public class UploadsListFragment extends Fragment implements ConnectionCallbacks,
        OnConnectionFailedListener {

    private static final String TAG = UploadsListFragment.class.getName();
    private static Context mContext;
    private Callbacks mCallbacks;
    private GoogleApiClient mGoogleApiClient;
    private GridView mGridView;
    private ImageLoader mImageLoader;
    private static final int RC_SIGN_IN = 9001;
    youtube you;

    public UploadsListFragment() {
    }

    @SuppressLint("ValidFragment")
    public UploadsListFragment(Context context) {
        this.mContext = context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(you)
                .enableAutoManage(getActivity(), UploadsListFragment.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(com.google.android.gms.auth.api.Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View listView = inflater.inflate(R.layout.list_fragment, container, false);
        mGridView = (GridView) listView.findViewById(R.id.grid_view);
        TextView emptyView = (TextView) listView.findViewById(android.R.id.empty);
        mGridView.setEmptyView(emptyView);
        return listView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setProfileInfo();
    }

    public void setVideos(List<VideoData> videos) {
        if (!isAdded()) {
            return;
        }

        mGridView.setAdapter(new UploadedVideoAdapter(videos));
    }

    public void setProfileInfo() {
        //not sure if mGoogleapiClient.isConnect is appropriate...
        if (!mGoogleApiClient.isConnected()) {
            ((ImageView) getView().findViewById(R.id.avatar))
                    .setImageDrawable(null);
            ((TextView) getView().findViewById(R.id.display_name))
                    .setText(R.string.not_signed_in);
        } else {
            signIn();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (mGridView.getAdapter() != null) {
            ((UploadedVideoAdapter) mGridView.getAdapter()).notifyDataSetChanged();
        }

       // setProfileInfo();
        //signIn();

        mCallbacks.onConnected("vasu");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            Toast.makeText(getActivity(),
                    R.string.connection_to_google_play_failed, Toast.LENGTH_SHORT)
                    .show();

            Log.e(TAG,
                    String.format(
                            "Connection to Play Services Failed, error: %d, reason: %s",
                            connectionResult.getErrorCode(),
                            connectionResult.toString()));
            try {
                connectionResult.startResolutionForResult(getActivity(), 0);
            } catch (IntentSender.SendIntentException e) {
                Log.e(TAG, e.toString(), e);
            }
        }
    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            handleSignInResult(result);
        }

    }
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {

            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());

           String personName = acct.getDisplayName();
            String email = acct.getEmail();
            ((NetworkImageView) getView().findViewById(R.id.avatar)).setImageUrl(String.valueOf(acct.getPhotoUrl()), mImageLoader);
            ((TextView) getView().findViewById(R.id.display_name))
                    .setText(acct.getDisplayName());

        }
        else
        {

            Toast.makeText(mContext,"Something Bad occur ,please try again" ,
                    Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        Activity a;
        if (!(activity instanceof Callbacks)) {
            throw new ClassCastException("Activity must implement callbacks.");
        }

        mCallbacks = (Callbacks) activity;

        mImageLoader = mCallbacks.onGetImageLoader();
       // mContext = activity;
       // signIn();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
        mImageLoader = null;
    }

    public interface Callbacks {
        public ImageLoader onGetImageLoader();

        public void onVideoSelected(VideoData video);

        public void onConnected(String connectedAccountName);
    }

    private class UploadedVideoAdapter extends BaseAdapter {
        private List<VideoData> mVideos;

        private UploadedVideoAdapter(List<VideoData> videos) {
            mVideos = videos;
        }

        @Override
        public int getCount() {
            return mVideos.size();
        }

        @Override
        public Object getItem(int i) {
            return mVideos.get(i);
        }

        @Override
        public long getItemId(int i) {
            return mVideos.get(i).getYouTubeId().hashCode();
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup container) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(
                        R.layout.list_item, container, false);
            }

            VideoData video = mVideos.get(position);
            ((TextView) convertView.findViewById(android.R.id.text1))
                    .setText(video.getTitle());
            ((NetworkImageView) convertView.findViewById(R.id.thumbnail)).setImageUrl(video.getThumbUri(), mImageLoader);
//            if (mGoogleApiClient.isConnected()) {
//                ((PlusOneButton) convertView.findViewById(R.id.plus_button))
//                        .initialize(video.getWatchUri(), null);
//            }
            convertView.findViewById(R.id.main_target).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mCallbacks.onVideoSelected(mVideos.get(position));
                        }
                    });
            return convertView;
        }
    }
}
