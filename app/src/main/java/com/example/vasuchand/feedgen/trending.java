package com.example.vasuchand.feedgen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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

/**
 * Created by Vasu Chand on 10/22/2016.
 */

public class trending extends AppCompatActivity
{
    me.kaelaela.verticalviewpager.VerticalViewPager verticalViewPager;
    private static String URL = "http://sellbuybook.com/feedme/mcapp2.php";
    private config config;
    String category;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trending_layout);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        verticalViewPager = (me.kaelaela.verticalviewpager.VerticalViewPager) findViewById(R.id.verticleViewPager);

        //  System.out.println(category);
        getData();
    }

    private void getData() {
        class GetData extends AsyncTask<Object,Void,String> {
            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                //
                //System.out.println(s);
                //
                progressBar.setVisibility(View.GONE);
                parseCategory(s);

            }

            @Override
            protected String doInBackground(Object... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("Category", "Entertainment"));
                String result = null;


                Uri.Builder builder = new Uri.Builder();
                HttpClient client = new DefaultHttpClient();

                // String bingUrl = "https://api.datamarket.azure.com/Bing/SearchWeb/v1/Web?Query=%27" + searchStr + "%27" + numOfResultsStr + "&$format=json";
                builder.scheme("https")
                        .authority("api.cognitive.microsoft.com")
                        .appendPath("bing")
                        .appendPath("v5.0")
                        .appendPath("news")
                        .appendPath("trendingtopics");



                String myUrl = builder.build().toString();
                HttpGet httpget = new HttpGet(myUrl);
                httpget.setHeader("Ocp-Apim-Subscription-Key", "450c2ca4779747b89a9525f2816193f6");
//                    HttpGet request = new HttpGet(myUrl);
                HttpResponse response = null;
                try {
                    response = client.execute(httpget);

                    result = EntityUtils.toString(response.getEntity(), "UTF-8");

                }catch (ClientProtocolException e) {
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

    public void parseCategory(String json){
        try {


            if(json != null) {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray array = jsonObject.getJSONArray(config.TAG_JSON_ARRAY2);


                config = new config(array.length());

                for (int i = 0; i < array.length(); i++) {

                    JSONObject j = array.getJSONObject(i);
                    config.headline3[i] = getHeadline(j);


                    JSONObject phone = j.getJSONObject("image");
                    config.bitmaps3[i] = getURL(phone);

                    config.desc3[i] = getDesc(j);
                    System.out.println(config.bitmaps3[i]);

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

       // System.out.println(config.headline3);
        if(json!=null) {
            verticalViewPager.setAdapter(new pageradapter(trending.this, config.headline3, config.bitmaps3, config.desc3));
            verticalViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
    }

    private String getDesc(JSONObject j) {
        String url = null;
        url = j.optString(config.TAG_MORE2);
        return url;
    }

    private String getHeadline(JSONObject j) {
        String url = null;
        url = j.optString(config.TAG_HEADING2);
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
        url = j.optString(config.TAG_IMAGE_URL2);
        if (url != null && !url.isEmpty()) {
            // doSomething
            s2=url;
        }


        return s2;
    }

}
