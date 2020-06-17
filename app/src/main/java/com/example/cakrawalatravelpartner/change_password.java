package com.example.cakrawalatravelpartner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

public class change_password extends AppCompatActivity {

    SharedPreferences shared;
    private dbHelp dbHelp;
    private Cursor cursor;
    private String search_username, username, full_name, phone, password, confirm, old_username;
    private EditText edit_user, edit_full, edit_phone, edit_password, edit_confirm;
    private long backpress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        edit_user = findViewById(R.id.new_username);
        edit_full = findViewById(R.id.new_fullname);
        edit_phone = findViewById(R.id.new_phone);
        edit_password = findViewById(R.id.new_password);
        edit_confirm = findViewById(R.id.new_confirm_pw);

        dbHelp = new dbHelp(this);
        account();
    }
    @Override
    public void onBackPressed() {
        if (backpress + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        } else {
            message.SHORT(getApplicationContext(),"Press Again To Exit Application");
        }
        backpress = System.currentTimeMillis();
    }


    private void account() {
        shared = getSharedPreferences("profile_user", MODE_PRIVATE);
        search_username = shared.getString("username", null);
        String[] usernameArgs = {search_username};
        dbHelp = new dbHelp(this);
        SQLiteDatabase db = dbHelp.getReadableDatabase();
        cursor = db.query("tb_user", null, "username=?", usernameArgs, null, null, null);
        int i = 0;
        try {
            while (cursor.moveToNext()) {
                cursor.moveToPosition(i);
                username = cursor.getString(cursor.getColumnIndex("username"));
                full_name = cursor.getString(cursor.getColumnIndex("full_name"));
                phone = cursor.getString(cursor.getColumnIndex("phone"));
                password = cursor.getString(cursor.getColumnIndex("password"));
                i++;
            }

        } catch (Exception e) {
            message.LONG(getApplicationContext(), "Error " + e);
        } finally {
            edit_user.setText(username);
            edit_full.setText(full_name);
            edit_phone.setText(phone);
            edit_password.setText(password);
        }
    }

    public void back(View view) {
        Intent goback = new Intent(change_password.this, my_account.class);
        startActivity(goback);
        finish();
    }

    public void bt_change(View view) {
        old_username = this.shared.getString("username", null);
        username = edit_user.getText().toString();
        full_name = edit_full.getText().toString();
        phone = edit_phone.getText().toString();
        password = edit_password.getText().toString();
        confirm = edit_confirm.getText().toString();

        if (username.isEmpty() || full_name.isEmpty() || phone.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            message.SHORT(getApplicationContext(), "Please Fill All Column");
        } else {
            if (password.matches(confirm)) {
                if (username.matches(old_username)) {
                    int a = dbHelp.update_account(username, full_name, phone, password);
                    if (a<=0) {
                        message.SHORT(getApplicationContext(), "Username didn't available");
                        edit_user.setText("");
                    } else {
                        message.SHORT(getApplicationContext(), "Updating...");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ;
                                Intent gologin = new Intent(change_password.this, signinpage.class);
                                startActivity(gologin);
                                finish();
                                message.LONG(getApplicationContext(), " Update Success \nPlease Re-Sign In");
                            }
                        }, 2000);
                    }
                } else {
                    dbHelp.update_userbooking(username,old_username);
                    int b = dbHelp.update_Newacc(username,full_name,phone,password,old_username);
                    if(b<=0){
                        message.SHORT(getApplicationContext(), "Username " + old_username +" didn't available");
                        edit_user.setText("");
                    } else {
                        message.SHORT(getApplicationContext(), "Updating...");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ;
                                Intent gologin = new Intent(change_password.this, signinpage.class);
                                startActivity(gologin);
                                finish();
                                message.LONG(getApplicationContext(), " Update Success \nPlease Re-Sign In");
                            }
                        }, 2000);
                    }
                }
            } else {
                message.SHORT(getApplicationContext(), "Password aren't matching");
            }
        }
    }
}
