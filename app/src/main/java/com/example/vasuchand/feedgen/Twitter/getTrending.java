package com.example.vasuchand.feedgen.Twitter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.example.vasuchand.feedgen.R;
import com.example.vasuchand.feedgen.config;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Vasu Chand on 11/5/2016.
 */

public class getTrending extends AppCompatActivity {
    public static final String TAG = "TwitterUtils";
    String jsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        new Auth().execute();
        new LongRunning().execute();

    }

    /*
       public static String appAuthentication() {

           HttpURLConnection httpConnection = null;
           OutputStream outputStream = null;
           BufferedReader bufferedReader = null;
           StringBuilder response = null;

           try {
               URL url = new URL(config.URL_AUTHENTICATION);
               httpConnection = (HttpURLConnection) url.openConnection();
               httpConnection.setRequestMethod("POST");
               httpConnection.setDoOutput(true);
               httpConnection.setDoInput(true);

               String accessCredential = config.CONSUMER_KEY + ":"
                       + config.CONSUMER_SECRET;
               String authorization = "Basic "
                       + Base64.encodeToString(accessCredential.getBytes(),
                       Base64.NO_WRAP);
               String param = "grant_type=client_credentials";

               httpConnection.addRequestProperty("Authorization", authorization);
               httpConnection.setRequestProperty("Content-Type",
                       "application/x-www-form-urlencoded;charset=UTF-8");
               httpConnection.connect();

               outputStream = httpConnection.getOutputStream();
               outputStream.write(param.getBytes());
               outputStream.flush();
               outputStream.close();
               // int statusCode = httpConnection.getResponseCode();
               // String reason =httpConnection.getResponseMessage();

               bufferedReader = new BufferedReader(new InputStreamReader(
                       httpConnection.getInputStream()));
               String line;
               response = new StringBuilder();

               while ((line = bufferedReader.readLine()) != null) {
                   response.append(line);
               }

               Log.d(TAG,
                       "POST response code: "
                               + String.valueOf(httpConnection.getResponseCode()));
               Log.d(TAG, "JSON response: " + response.toString());

           } catch (Exception e) {
               Log.e(TAG, "POST error: " + Log.getStackTraceString(e));

           } finally {
               if (httpConnection != null) {
                   httpConnection.disconnect();
               }
           }
           return response.toString();
       }


       public static String getTimelineForSearchTerm(
                                                     Context context) {
           HttpURLConnection httpConnection = null;
           BufferedReader bufferedReader = null;
           StringBuilder response = new StringBuilder();

           try {
               URL url = new URL(config.URL_INDIA_TRENDING);
               httpConnection = (HttpURLConnection) url.openConnection();
               httpConnection.setRequestMethod("GET");

               String jsonString = appAuthentication();
               JSONObject jsonObjectDocument = new JSONObject(jsonString);
               String token = jsonObjectDocument.getString("token_type") + " "
                       + jsonObjectDocument.getString("access_token");
               httpConnection.setRequestProperty("Authorization", token);

               httpConnection.setRequestProperty("Authorization", token);
               httpConnection.setRequestProperty("Content-Type",
                       "application/json");
               httpConnection.connect();

               bufferedReader = new BufferedReader(new InputStreamReader(
                       httpConnection.getInputStream()));

               String line;
               while ((line = bufferedReader.readLine()) != null) {
                   response.append(line);
               }

               Log.d(TAG,
                       "GET response code: "
                               + String.valueOf(httpConnection
                               .getResponseCode()));
               Log.d(TAG, "JSON response: " + response.toString());

           } catch (Exception e) {
               Log.e(TAG, "GET error: " + Log.getStackTraceString(e));

           } finally {
               if (httpConnection != null) {
                   httpConnection.disconnect();

               }
           }


       return response.toString();
   }
    */
    public class Auth extends AsyncTask<Void, Void, String> {
        protected void onPreExecute() {
            //UI updating goes here before background thread..
        }
        @Override
        protected String doInBackground(Void... params) {

            HttpURLConnection httpConnection = null;
            OutputStream outputStream = null;
            BufferedReader bufferedReader = null;
            StringBuilder response = null;

            try {
                URL url = new URL(config.URL_AUTHENTICATION);
                httpConnection = (HttpURLConnection) url.openConnection();
                httpConnection.setRequestMethod("POST");
                httpConnection.setDoOutput(true);
                httpConnection.setDoInput(true);

                String accessCredential = config.CONSUMER_KEY + ":"
                        + config.CONSUMER_SECRET;
                String authorization = "Basic "
                        + Base64.encodeToString(accessCredential.getBytes(),
                        Base64.NO_WRAP);
                String param = "grant_type=client_credentials";

                httpConnection.addRequestProperty("Authorization", authorization);
                httpConnection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded;charset=UTF-8");
                httpConnection.connect();

                outputStream = httpConnection.getOutputStream();
                outputStream.write(param.getBytes());
                outputStream.flush();
                outputStream.close();
                // int statusCode = httpConnection.getResponseCode();
                // String reason =httpConnection.getResponseMessage();

                bufferedReader = new BufferedReader(new InputStreamReader(
                        httpConnection.getInputStream()));
                String line;
                response = new StringBuilder();

                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }

                Log.d(TAG,
                        "POST response code: "
                                + String.valueOf(httpConnection.getResponseCode()));
                Log.d(TAG, "JSON response: " + response.toString());

            } catch (Exception e) {
                Log.e(TAG, "POST error: " + Log.getStackTraceString(e));

            } finally {
                if (httpConnection != null) {
                    httpConnection.disconnect();
                }
            }
            return response.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            //update your UI after background thread

            System.out.println(result);
            jsonString = result;
        }

    }

    public class LongRunning extends AsyncTask<Void, Void, String> {
        protected void onPreExecute() {
            //UI updating goes here before background thread..
        }
        @Override
        protected String doInBackground(Void... params) {

            HttpURLConnection httpConnection = null;
            BufferedReader bufferedReader = null;
            StringBuilder response = new StringBuilder();

            try {
                URL url = new URL(config.URL_INDIA_TRENDING);
                httpConnection = (HttpURLConnection) url.openConnection();
                httpConnection.setRequestMethod("GET");


                JSONObject jsonObjectDocument = new JSONObject(jsonString);
                String token = jsonObjectDocument.getString("token_type") + " "
                        + jsonObjectDocument.getString("access_token");
                httpConnection.setRequestProperty("Authorization", token);

                httpConnection.setRequestProperty("Authorization", token);
                httpConnection.setRequestProperty("Content-Type",
                        "application/json");
                httpConnection.connect();

                bufferedReader = new BufferedReader(new InputStreamReader(
                        httpConnection.getInputStream()));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }

                Log.d(TAG,
                        "GET response code: "
                                + String.valueOf(httpConnection
                                .getResponseCode()));
                Log.d(TAG, "JSON response: " + response.toString());

            } catch (Exception e) {
                Log.e(TAG, "GET error: " + Log.getStackTraceString(e));

            } finally {
                if (httpConnection != null) {
                    httpConnection.disconnect();

                }
            }


            return response.toString();

        }

        @Override
        protected void onPostExecute(String result) {
            //update your UI after background thread
            System.out.println(result);
        }

    }

}
