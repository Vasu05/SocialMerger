package com.example.vasuchand.feedgen.Login;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vasuchand.feedgen.MainActivity;
import com.example.vasuchand.feedgen.R;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;



import java.io.IOException;
import java.net.URL;

public class Signup extends AppCompatActivity implements
         GoogleApiClient.OnConnectionFailedListener  {

    private static final String TAG = "Signup";
    ImageView google,facebook;
    Context mContext;
    GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private static  final int RC_ACCESS_CODE =101;
    int resultCode;
    GoogleApiAvailability googleAPI;
    public static final int REQUEST_PERMISSIONS_CODE = 128;
    Session session;
    String personName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        google = (ImageView)findViewById(R.id.goog);
        facebook = (ImageView)findViewById(R.id.face);
        mContext = Signup.this;
        session = new Session(mContext);
        googleAPI = GoogleApiAvailability.getInstance();

        validateServerClientID();

        String serverClientId = getString(R.string.server_client_id);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(serverClientId)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        resultCode = googleAPI.isGooglePlayServicesAvailable(getApplicationContext());
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(isNetworkAvailable()) {
                   signIn();
               }
                else{
                   Toast.makeText(mContext, "No Internet Connection!",
                           Toast.LENGTH_SHORT).show();
               }
            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            Log.e("Network Testing", "Available");
            return true;
        } Log.e("Network Testing", "Not Available");
        return false;
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

            personName = acct.getDisplayName();
            session.setName(mContext, "Name", acct.getDisplayName());
            session.setEmail(mContext, "Email",acct.getEmail());
            session.setToken(mContext,"token",acct.getIdToken());
           // String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();
            System.out.println(acct.getIdToken());
            Log.e(TAG, "Name: " + personName + ", email: " + email
                    + ", Image: " );

           // start(personName);

            ////////////




                /////////////////

            session.setLogin(mContext, "Login", "1");
            startActivity(new Intent(Signup.this, credentials.class));
            finish();



        }
        else
        {

            Toast.makeText(mContext,"Something Bad occur ,please try again" ,
                    Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }





    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void validateServerClientID() {
        String serverClientId = getString(R.string.server_client_id);
        String suffix = ".apps.googleusercontent.com";
        if (!serverClientId.trim().endsWith(suffix)) {
            String message = "Invalid server client ID in strings.xml, must end with " + suffix;

            Log.w(TAG, message);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }
}
