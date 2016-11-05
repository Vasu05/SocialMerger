package com.example.vasuchand.feedgen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Vasu Chand on 10/16/2016.
 */

public class database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mc.db";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_EMAIL = "email";
    public static final String CONTACTS_COLUMN_PHONE = "phone";
    public static final String CONTACTS_COLUMN_Password = "password";

    private Context c;
    public database(Context context)
    {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.c = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table mobilecomputing " +
                        "(id integer primary key autoincrement, name text,phone text,email text, password text)"
        );
    }

    public boolean insertContact  (String name, String phone, String email, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("password", password);
        db.insert("mobilecomputing", null, contentValues);
        return true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);

    }
    public Cursor getData(String user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from mobilecomputing where email= ?", new String[]{user});
        return res;
    }

    public Integer deleteContact (String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("mobilecomputing",
                "email = ? ",
                new String[] { email });
    }

}
