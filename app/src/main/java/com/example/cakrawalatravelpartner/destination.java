package com.example.cakrawalatravelpartner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class destination extends AppCompatActivity {

    private long backpress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
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

    public void back(View view) {
        Intent goback = new Intent(destination.this,home.class);
        startActivity(goback);
        finish();
    }

    public void taman_sari(View view) {
        Intent tamansari = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.indonesia.travel/gb" +
                "/en/destinations/java/yogyakarta/tamansari-water-castle"));
        startActivity(tamansari);
    }

    public void merapi(View view) {
        Intent merapi = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.indonesia.travel/gb" +
                "/en/trip-ideas/climb-majestic-mt-merapi-volcano-at-the-center-of-java"));
        startActivity(merapi);
    }

    public void prambanan(View view) {
        Intent prambanan = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.indonesia.travel/gb" +
                "/en/destinations/java/yogyakarta/prambanan"));
        startActivity(prambanan);
    }

    public void ratu_bokoh(View view) {
        Intent ratubokoh = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.indonesia-tourism.com/blog/" +
                "ratu-boko-temple-yogyakarta/"));
        startActivity(ratubokoh);
    }

    public void indrayanti(View view) {
        Intent indrayanti = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.indonesia.travel/gb" +
                "/en/destinations/java/yogyakarta/indrayanti-beach"));
        startActivity(indrayanti);
    }

    public void kalibiru(View view) {
        Intent kalibiru = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.indonesia-tourism.com/forum/" +
                "showthread.php?48125-Kalibiru-Tourism-Village-in-Manoreh-Hill-Kulonprogo-Yogyakarta"));
        startActivity(kalibiru);
    }
}
