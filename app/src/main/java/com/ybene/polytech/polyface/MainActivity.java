package com.ybene.polytech.polyface;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView smileyImageView;
    private ImageButton aboutImageButton, caputreImageButton, realtimeImageButton;
    private AnimationDrawable frameAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init
        smileyImageView = findViewById(R.id.activiy_main_smiley_face);
        aboutImageButton = findViewById(R.id.activity_main_about_button);
        caputreImageButton = findViewById(R.id.activity_main_picture_mode);
        realtimeImageButton = findViewById(R.id.activity_main_realtime_mode);

        // Smiley faces animation setup
        smileyImageView.setImageDrawable(getDrawable(R.drawable.smiley_blink));
        frameAnimation = (AnimationDrawable) smileyImageView.getDrawable();
        frameAnimation.start();

        // Go to "about" activity
        aboutImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutActivity = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(aboutActivity);
            }
        });

        // Go to caputre mode activity
        caputreImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraActivity = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(cameraActivity);
            }
        });

        // Go to realtime mode activity
        realtimeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent realtimeActivity = new Intent(MainActivity.this, RealtimeActivity.class);
                startActivity(realtimeActivity);
            }
        });

        ViewGroup.LayoutParams params = smileyImageView.getLayoutParams();
        params.height = 512;
        params.width = 32;
        smileyImageView.setLayoutParams(params);
    }

    @Override
    protected void onPause() {
        super.onPause();
        frameAnimation.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        frameAnimation.start();
    }
}
