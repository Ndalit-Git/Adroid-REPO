package com.example.cakrawalatravelpartner;

import android.content.Context;
import android.widget.Toast;

public class message {
    public static void SHORT(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static void LONG(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}

