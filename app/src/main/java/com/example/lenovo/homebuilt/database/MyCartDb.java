package com.example.lenovo.homebuilt.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.lenovo.homebuilt.model.BSInstallation;

import java.util.ArrayList;


public class MyCartDb extends SQLiteOpenHelper {

    private final String TAG = MyCartDb.class.getSimpleName();
    private static final String DB = "myCartDB";
    private static final int VERSION = 1;
    private static final String TABLE = "myCart";
    private static final String NAME = "name";
    private static final String QUANTITY = "quantity";
    private static final String PRICE = "price";

    public MyCartDb(Context context) {
        super(context, DB,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + "("+ NAME + " VARCHAR(50)," +QUANTITY + " INT(10)," + PRICE + " DOUBLE(100));");
        Log.d(TAG, "created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
    public void addData(String name, int quantity,double price) {

        SQLiteDatabase myDb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(QUANTITY, quantity);
        values.put(PRICE,price);
        myDb.insert(TABLE, null, values);
        myDb.close();
        Log.d(TAG, "inserted "+name+" "+quantity+" "+price);
    }

    public void updateCart(String name, int quantity,double price) {
        Log.d(TAG,"updated "+name+" "+quantity+" "+price);
        SQLiteDatabase myDb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(QUANTITY, quantity);
        values.put(PRICE,price);
        myDb.update(TABLE,values,"name = ?",new String[]{name});

    }

    public ArrayList<BSInstallation> fetchCartList() {
        SQLiteDatabase myDb = this.getReadableDatabase();
        Cursor cursor = myDb.rawQuery("SELECT * FROM " + TABLE, null);
        ArrayList<BSInstallation> al = new ArrayList<>();
        Log.d("get ", "data");
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex(NAME));
                    int quantity = cursor.getInt(cursor.getColumnIndex(QUANTITY));
                    double price = cursor.getDouble(cursor.getColumnIndex(PRICE));

                    al.add(new BSInstallation(name, quantity,price));

                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        return al;
    }

    //---deletes a particular title---
    public boolean deleteParticularCart(String name)
    {
        SQLiteDatabase myDb = this.getReadableDatabase();
        return myDb.delete(TABLE, "name = ?",new String[] {name}) >0;
    }
    public void deleteAllData() {
        SQLiteDatabase myDb = this.getWritableDatabase();
        myDb.execSQL("delete from "+ TABLE);
    }
}
