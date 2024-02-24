package com.example.autohaven1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class splash extends AppCompatActivity {
    ImageView log;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        log = findViewById(R.id.logo);

        Animation animation = AnimationUtils.loadAnimation(splash.this,R.anim.anim);
        log.startAnimation(animation);
        mAuth = FirebaseAuth.getInstance();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mAuth.getCurrentUser() != null){
                    startActivity(new Intent(splash.this, gooMapsActivity.class));
                }else{
                    startActivity(new Intent(splash.this, MainActivity.class));
                }
                finish();
            }
        },2000);
    }
}