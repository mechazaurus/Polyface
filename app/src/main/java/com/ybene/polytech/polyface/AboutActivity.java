package com.ybene.polytech.polyface;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class AboutActivity extends AppCompatActivity {

    private Button websiteButton;
    private ImageButton closeButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Init
        closeButton = findViewById(R.id.about_activity_close_button);
        websiteButton = findViewById(R.id.about_activity_website_button);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        websiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://polytech.univ-tours.fr";

                Intent websiteRedirection = new Intent(Intent.ACTION_VIEW);
                websiteRedirection.addCategory(Intent.CATEGORY_BROWSABLE);
                websiteRedirection.setData(Uri.parse(url));
                startActivity(websiteRedirection);
            }
        });
    }
}