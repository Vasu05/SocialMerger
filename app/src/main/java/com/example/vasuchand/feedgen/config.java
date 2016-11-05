package com.example.vasuchand.feedgen;

import android.graphics.Bitmap;

/**
 * Created by Vasu Chand on 10/17/2016.
 */

public class config {
    public static final String TAG_CATEGORY = "category";
    public static String[] headline;
    public static String[] category;
    public static String[] bitmaps;
    public static String[] time;
    public static String[] desc;

    public static String[] headline2;
    public static String[] category2;
    public static String[] bitmaps2;
    public static String[] time2;
    public static String[] desc2;

    public static String[] headline3;

    public static String[] bitmaps3;

    public static String[] desc3;

    public static String[] headline4;
    public static String[] youtubeid;
    public static String[] bitmaps4;


    public static final String TAG_JSON_ARRAY = "response";
    public static final String TAG_IMAGE_URL = "image";
    public static final String TAG_HEADING = "heading";
    public static final String TAG_TIME = "time";
    public static final String TAG_DESC = "description";
    //public static final String urls = "value";
    public static final String TAG_JSON_ARRAY2 = "value";
    public static final String TAG_IMAGE_URL2 = "url";
    public static final String TAG_HEADING2 = "name";
    public static final String TAG_MORE2 = "webSearchUrl";
    public static final String TAG_API = "AIzaSyCob8tVBQsw37r6Hp9DbuAh-d-7w-Oy4jE";

    public static final String URL_ROOT_TWITTER_API = "https://api.twitter.com";
    public static final String URL_SEARCH = URL_ROOT_TWITTER_API + "/1.1/search/tweets.json?q=";
    public static final String URL_AUTHENTICATION = URL_ROOT_TWITTER_API + "/oauth2/token";
    public static final String URL_INDIA_TRENDING ="https://api.twitter.com/1.1/trends/place.json?id=23424977";
    public static final String CONSUMER_KEY = "KqojHqfuMOKwFM5Fe9RqKYlDM";
    public static final String CONSUMER_SECRET = "VdLHdvpYgDzAf9p7wcDdOe5BktFsNXCCWFVJFetyJp2dAyCABy";

    public static final String YOUTUBE_VIDEO_CODE = "_oEA18Y8gM0";
    public config(int i)
    {
        headline = new String[i];
        category = new String[i];
        time     = new String[i];
        bitmaps  = new String[i];
        desc = new String[i];

        headline2 = new String[i];
        category2 = new String[i];
        time2    = new String[i];
        bitmaps2  = new String[i];
        desc2 = new String[i];

        headline3 = new String[i];
        bitmaps3  = new String[i];
        desc3 = new String[i];

        headline4 = new String[i];
        bitmaps4  = new String[i];
        youtubeid = new String[i];
    }


}
