package com.example.cakrawalatravelpartner;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.Nullable;

import java.util.Date;

public class dbHelp extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mybus.db";
    private static final String TABLE_NAME = "tb_user";
    private static final String TABLE_NAME1 = "tb_booking";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME +
            " (username VARCHAR(25) PRIMARY KEY," +
            " full_name VARCHAR(150)," +
            " gender VARCHAR(10)," +
            " phone VARCHAR(25)," +
            " password VARCHAR(25));";
    private static final String CREATE_TABLE1 = "CREATE TABLE "+ TABLE_NAME1 +
            " (id_booking INTEGER PRIMARY KEY AUTOINCREMENT," +
            " user VARCHAR(25)," +
            " book_from VARCHAR(50)," +
            " book_to VARCHAR(50)," +
            " passenger VARCHAR(2)," +
            " seat VARCHAR(20)," +
            " date VARCHAR(15)," +
            " fare INTEGER," +
            " total_fare INTEGER," +
            " status VARCHAR(10));";        //payment status, paid/unpaid
    private static final String DROP_TABLE="DROP TABLE IF EXISTS "+ TABLE_NAME;
    private static final String DROP_TABLE1="DROP TABLE IF EXISTS "+ TABLE_NAME1;
    private Context context;

    public dbHelp(@Nullable Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(CREATE_TABLE);
            db.execSQL(CREATE_TABLE1);
        }catch (Exception e){
           message.SHORT(context, ""+e);
        }

        //db.execSQL("insert into " + TABLE_NAME + " values ('dalit','NDALIT','Male','123456789','passdalit');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            message.SHORT(context,"OnUpgrade");
            db.execSQL(DROP_TABLE);
            db.execSQL(DROP_TABLE1);
            onCreate(db);
        }catch (Exception e){
            message.SHORT(context," "+e);
        }
    }

    public long insert_user(String username, String full_name, String gender, String phone, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("full_name", full_name);
        values.put("gender", gender);
        values.put("phone", phone);
        values.put("password", password);
        long id = db.insert(TABLE_NAME, null, values);
        return id;
    }

    public long insert_booking(String user, String book_from, String book_to, String passenger, String seat, Integer fare, Integer total_fare, String date, String status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values1 = new ContentValues();
        values1.put("user", user);
        values1.put("book_from", book_from);
        values1.put("book_to", book_to);
        values1.put("passenger", passenger);
        values1.put("seat", seat);
        values1.put("fare", fare);
        values1.put("total_fare", total_fare);
        values1.put("date", date);
        values1.put("status", status);
        long id = db.insert(TABLE_NAME1, null, values1);
        return id;
    }


    public int delete_account(String username){
        SQLiteDatabase db = getWritableDatabase();
        String[] whereArgs={username};
        int count = db.delete(TABLE_NAME, "username = ?", whereArgs);
        return count;
    }
    public int delete_booking(String id_booking){
        SQLiteDatabase db = getWritableDatabase();
        String[] whereArgs={id_booking};
        int count = db.delete(TABLE_NAME1, "id_booking = ?", whereArgs);
        return count;
    }

    public int update_account(String username, String full_name, String phone, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("full_name", full_name);
        values.put("phone", phone);
        values.put("password", password);
        String[] whereArgs = {username};
        int count = db.update(TABLE_NAME, values, "username = ?", whereArgs);
        return count;
    }

    public int update_Newacc(String username, String full_name, String phone, String password, String old_username) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("full_name", full_name);
        values.put("phone", phone);
        values.put("password", password);
        String[] whereArgs = {old_username};
        int count = db.update(TABLE_NAME, values, "username = ?", whereArgs);
        return count;
    }

    public int update_payment(String id_booking ,String status){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values_pay = new ContentValues();
        values_pay.put("status",status);
        String[] whereArgs = {id_booking};
        int count = db.update(TABLE_NAME1, values_pay,"id_booking=?",whereArgs);
        return count;
    }

    public int update_userbooking(String user, String old_user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user",user);
        String[] whereArgs = {old_user};
        int count = db.update(TABLE_NAME1, values,"user=?",whereArgs);
        return count;
    }

    public boolean signin (String username, String password) throws SQLException{
        SQLiteDatabase db = getWritableDatabase();
        Cursor mCursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME + " WHERE username=? AND password=? ",new String[]{username,password});
        if (mCursor !=null){
            if (mCursor.getCount()>0){
                return true;
            }
        }return false;
    }

    Cursor read_booking(){
        String query = "SELECT * FROM " + TABLE_NAME1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);
        } return cursor;
    }
}
