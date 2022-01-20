package com.multitips;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WebViewActivity extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = findViewById(R.id.webView);
        //webView.setBackgroundColor(Color.TRANSPARENT);
        String baseUrl = "http://bet.hibatest.com/multi-tips-privacy.html";
        webView.loadUrl(baseUrl);

        FloatingActionButton btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}