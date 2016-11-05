package com.example.vasuchand.feedgen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import me.kaelaela.verticalviewpager.*;
import me.kaelaela.verticalviewpager.VerticalViewPager;

/**
 * Created by Vasu Chand on 10/21/2016.
 */

public class fragment_activity extends AppCompatActivity {
    me.kaelaela.verticalviewpager.VerticalViewPager verticalViewPager;
    private static String URL = "http://sellbuybook.com/feedme/mcapp2.php";
    private config config;
    String category;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_layout);
        Intent intent = getIntent();
        progressBar = (ProgressBar)findViewById(R.id.progress);
        verticalViewPager = (VerticalViewPager) findViewById(R.id.verticleViewPager);
        category = intent.getStringExtra("category");
        System.out.println(category);
        getData();
    }

    private void getData() {


        class GetData extends AsyncTask<Object,Void,String>{
            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                parseCategory(s);
                progressBar.setVisibility(View.GONE);


            }

            @Override
            protected String doInBackground(Object... params) {

                InputStream is = null;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("category", category));
                String result = null;

                try {
                    java.net.URL url = new URL(URL);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(URL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();


                } catch (ClientProtocolException e) {
                    e.printStackTrace();


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }
        }

        GetData gd = new GetData();
        gd.execute(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    public static final int LOGCAT_MAX_LINE_LIMIT = 4000;

    public static final String TAG = "log_tag";

    private void showLog(String message) {
        if (message.length() > LOGCAT_MAX_LINE_LIMIT) {
            int chunkCount = message.length() / LOGCAT_MAX_LINE_LIMIT;
            for (int i = 0; i <= chunkCount; i++) {
                int max = LOGCAT_MAX_LINE_LIMIT * (i + 1);
                if (max >= message.length()) {
                    Log.d(TAG, message.substring(LOGCAT_MAX_LINE_LIMIT * i));
                } else {
                    Log.d(TAG, message.substring(LOGCAT_MAX_LINE_LIMIT * i, max));
                }
            }
        } else {
            Log.d(TAG, message);
        }
    }

    public void parseCategory(String json){
        try {


            if(json != null) {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray array = jsonObject.getJSONArray(config.TAG_JSON_ARRAY);


                config = new config(array.length());

                for (int i = 0; i < array.length(); i++) {
                    JSONObject j = array.getJSONObject(i);
                    config.headline2[i] = getHeadline(j);
                    config.time2[i] = getTime(j);
                    config.category2[i] = getCategory(j);
                    config.bitmaps2[i] = getURL(j);
                    config.desc2[i] = getDesc(j);

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(config.headline);
        if(json!=null) {
            verticalViewPager.setAdapter(new VerticlePagerAdapter(fragment_activity.this, config.headline2, config.bitmaps2, config.category2, config.time2, config.desc2));
            verticalViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
    }

    private String getDesc(JSONObject j) {
        String url = null;
        url = j.optString(config.TAG_DESC);
        return url;
    }

    private String getCategory(JSONObject j) {
        String url = null;
        url = j.optString(config.TAG_CATEGORY);
        return url;
    }

    private String getTime(JSONObject j) {
        String url = null;
        url = j.optString(config.TAG_TIME);
        return url;
    }

    private String getHeadline(JSONObject j) {
        String url = null;
        url = j.optString(config.TAG_HEADING);
        return url;
    }

    private String getURL(JSONObject j) {
        String url = null;
        String s2=null;
//        try {
//            url = j.getString(category_config.TAG_IMAGE_URL);
//            s2= "http://sellbuybook.com/"+url;
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        url = j.optString(config.TAG_IMAGE_URL);
        if (url != null && !url.isEmpty()) {
            // doSomething
            s2= "http://sellbuybook.com/"+url;
        }


        return s2;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
