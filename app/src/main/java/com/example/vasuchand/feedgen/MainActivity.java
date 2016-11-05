package com.example.vasuchand.feedgen;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.IdRes;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.vasuchand.feedgen.Twitter.LoginWithTwitter;
import com.example.vasuchand.feedgen.Twitter.getTrending;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import me.kaelaela.verticalviewpager.VerticalViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;


public class MainActivity extends AppCompatActivity {

    private static String URL = "http://sellbuybook.com/feedme/mcapp1.php";
    String webTitle = "";
    Button b1,b2,b3,b4,b5,b6,b7;
    private config config;
    ImageLoaderConfiguration configu;
    VerticalViewPager verticalViewPager;
    private Toolbar toolbar;
    private ProgressBar progressBar;

    private List<search_item_getter_setter> dataList = new ArrayList<>();
    SlidingMenu menu;
    ImageView settings;
    TextView t1;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        context = this;

        if(isNetworkAvailable()) {

            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .resetViewBeforeLoading(true)
                    .displayer(new FadeInBitmapDisplayer(300))
                    .build();

            configu = new ImageLoaderConfiguration.Builder(getApplicationContext())
                    .threadPoolSize(3)
                    .denyCacheImageMultipleSizesInMemory()
                    .threadPriority(Thread.MIN_PRIORITY + 3)
                    .memoryCacheSize(1048576 * 15)
                    .diskCacheSize(100 * 1024 * 1024)
                    .diskCacheExtraOptions(480, 320, null)
                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                    .defaultDisplayImageOptions(DisplayImageOptions.createSimple()).build();


            ImageLoader.getInstance().init(configu);

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            // toolbar.getBackground().setAlpha(0);

            verticalViewPager = (VerticalViewPager) findViewById(R.id.verticleViewPager);
            getData();


            //settings = (ImageView)findViewById(R.);

            menu = new SlidingMenu(this);
            menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
            menu.setShadowWidth(16);
            menu.setBehindOffset(100);
            // menu.setBehindWidthRes(300);
            menu.setFadeDegree(0.35f);
            menu.setMode(SlidingMenu.LEFT); // Use SlidingMenu.RIGHT to start the menu from right
            menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
            menu.setMenu(R.layout.sliding_layout);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.menu_frame, SlidingFragment.newInstance())
                    .commit();


            t1 = (TextView) findViewById(R.id.t1);
            b1 = (Button) findViewById(R.id.b1);
            b2 = (Button) findViewById(R.id.b2);
            b3 = (Button) findViewById(R.id.b3);
            b4 = (Button) findViewById(R.id.b4);
            b5 = (Button) findViewById(R.id.b5);
            b6 = (Button) findViewById(R.id.b6);
            b7 = (Button) findViewById(R.id.b7);
//        b1.setOnClickListener(this);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    System.out.println("yes");
                    Intent intent = new Intent(MainActivity.this, fragment_activity.class);
                    intent.putExtra("category", "india");
                    startActivity(intent);

                }

            });
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, fragment_activity.class);
                    intent.putExtra("category", "Sports");
                    startActivity(intent);

                }
            });
            b3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, fragment_activity.class);
                    intent.putExtra("category", "Business");
                    startActivity(intent);

                }
            });
            b4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, fragment_activity.class);
                    intent.putExtra("category", "technology");
                    startActivity(intent);

                }
            });
            b5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, fragment_activity.class);
                    intent.putExtra("category", "politics");
                    startActivity(intent);

                }
            });
            b6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, fragment_activity.class);
                    intent.putExtra("category", "world");
                    startActivity(intent);

                }
            });
            b7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, fragment_activity.class);
                    intent.putExtra("category", "Entertainment");
                    startActivity(intent);

                }
            });
            t1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, LoginWithTwitter.class);
                   // intent.putExtra("category", "trending");
                    startActivity(intent);

                }
            });
        }
        else {
            Toast.makeText(context, "No Internet Connection!",
                    Toast.LENGTH_SHORT).show();
        }





    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            Log.e("Network Testing", "Available");
            return true;
        } Log.e("Network Testing", "Not Available");
        return false;
    }
    private void getData() {

        class GetData extends AsyncTask<Object,Void,String>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
               progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

              //
                // System.out.println(s);
                progressBar.setVisibility(View.GONE);
                parseCategory(s);

            }

            @Override
            protected String doInBackground(Object... params) {

            /*    InputStream is = null;

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
                            .appendPath("search")
                            .appendQueryParameter("count", "10")
                            .appendQueryParameter("Category", "Entertainment");


                    String myUrl = builder.build().toString();
                    HttpGet httpget = new HttpGet(myUrl);
                    httpget.setHeader("Ocp-Apim-Subscription-Key", "450c2ca4779747b89a9525f2816193f6");
//                    HttpGet request = new HttpGet(myUrl);
                    HttpResponse response = null;
                try {
                    response = client.execute(httpget);

                     result = EntityUtils.toString(response.getEntity(), "UTF-8");

                */
                 InputStream is = null;
                 JSONObject jObj = null;
                 String json = "";

                try {
                    // defaultHttpClient
                    DefaultHttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(URL);

                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    is = httpEntity.getContent();

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    json = sb.toString();
                    showLog(json);
                   //System.out.println(json);

                } catch (Exception e) {
                    Log.e("Buffer Error", "Error converting result " + e.toString());
                }



            return json;



            }




        }

        GetData gd = new GetData();
        gd.execute();
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
                    config.headline[i] = getHeadline(j);
                    config.time[i] = getTime(j);
                    config.category[i] = getCategory(j);
                    config.bitmaps[i] = getURL(j);
                    config.desc[i] = getDesc(j);

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(config.headline);
        if(json!=null) {
            verticalViewPager.setAdapter(new VerticlePagerAdapter(MainActivity.this, config.headline, config.bitmaps, config.category, config.time, config.desc));
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


}
