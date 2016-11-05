package com.example.vasuchand.feedgen.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.vasuchand.feedgen.MainActivity;
import com.example.vasuchand.feedgen.R;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

public class splashscreen extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "KqojHqfuMOKwFM5Fe9RqKYlDM";
    private static final String TWITTER_SECRET = "VdLHdvpYgDzAf9p7wcDdOe5BktFsNXCCWFVJFetyJp2dAyCABy";


    private static int SPLASH_TIME_OUT = 3000;
    Session session;
    private Context context = splashscreen.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_splashscreen);

        getWindow().getDecorView().setBackgroundColor(ContextCompat.getColor(context, R.color.white));


        session = new Session(context);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {

                String status = session.getPreferences(splashscreen.this, "Login");
                Log.d("Login", status);

                if(session.isFirstTimeLaunch()){

                    Intent i = new Intent(splashscreen.this, launch.class);
                    startActivity(i);
                }
                else if (status.equals("1")) {
                    Intent i = new Intent(splashscreen.this, MainActivity.class);
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(splashscreen.this, Signup.class);
                    startActivity(i);
                }

                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
