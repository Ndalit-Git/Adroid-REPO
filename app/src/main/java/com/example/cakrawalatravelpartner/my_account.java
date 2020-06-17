package com.example.cakrawalatravelpartner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class my_account extends AppCompatActivity {

    dbHelp dbHelp;
    SharedPreferences shared;
    String search_username;
    Cursor cursor;
    private long backpress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        dbHelp = new dbHelp(this);
        my_acc();
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


    private void my_acc(){
        shared = getSharedPreferences("profile_user", MODE_PRIVATE);
        search_username = shared.getString("username", null);
        String[] usernameArgs = {search_username};
        dbHelp = new dbHelp(this);
        SQLiteDatabase db = dbHelp.getReadableDatabase();
        cursor = db.query("tb_user", null, "username=?", usernameArgs, null, null, null);
    }

    public void back(View view) {
        Intent goback = new Intent(my_account.this,home.class);
        startActivity(goback);
        finish();
    }

    public void logout(View view) {
        message.SHORT(getApplicationContext(), "Sign Out...");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ;
                Intent gologout = new Intent(my_account.this, gestarted.class);
                startActivity(gologout);
                finish();
            }
        }, 1500);
            }

    public void edit_profile(View view) {
        Intent gologout = new Intent(my_account.this,change_password.class);
        startActivity(gologout);
        finish();
    }

    public void delete_acc(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(my_account.this);
        builder.setMessage("Are you sure want to delete account?");
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbHelp.delete_account(search_username);
                message.SHORT(getApplicationContext(), "Deleting...");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ;
                        Intent gologin = new Intent(my_account.this, gestarted.class);
                        startActivity(gologin);
                        finish();
                        message.LONG(getApplicationContext(), "Account Deleted");
                    }
                }, 2000);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
