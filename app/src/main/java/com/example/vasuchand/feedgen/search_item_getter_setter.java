package com.example.vasuchand.feedgen;

import android.graphics.Bitmap;

/**
 * Created by Vasu Chand on 10/20/2016.
 */

public class search_item_getter_setter {

    private String heading,Category,time,desc,intenturl;
    private int id;
    private Bitmap image;

    public void setHeading( String heading){
        this.heading = heading;
    }

    public String getHeading() {
        return heading;
    }

    public void setCategory(String category) {
        this.Category = category;
    }

    public String getCategory() {
        return Category;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setIntenturl(String intenturl) {
        this.intenturl = intenturl;
    }

    public String getIntenturl() {
        return intenturl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }
}
