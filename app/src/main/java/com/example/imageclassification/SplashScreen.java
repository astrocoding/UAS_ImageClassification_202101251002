package com.example.imageclassification;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 3000; // 3 detik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        // Atur tindakan setelah splash screen ditampilkan
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Intent untuk berpindah ke MainActivity setelah SPLASH_TIME_OUT
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish(); // Menutup splash screen agar tidak bisa kembali
            }
        }, SPLASH_TIME_OUT);

    }
}