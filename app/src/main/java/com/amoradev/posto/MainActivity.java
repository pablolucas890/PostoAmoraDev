package com.amoradev.posto;

import static android.net.Uri.decode;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.amoradev.posto.R;

//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            //super.onBackPressed();
            webView.loadUrl("https://www.posto.amoradev.com.br/acesso");
        }
    }
    private void enableHTML5AppCache(WebSettings settings) {
        settings.setDomStorageEnabled(true);
//        settings.setAppCachePath(getCacheDir().getAbsolutePath());
//        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.setWebViewClient(new WebViewClient());
        WebSettings settings = webView.getSettings();

        settings.setUseWideViewPort(true);
        //enableHTML5AppCache(settings);
        webView.loadUrl("https://www.posto.amoradev.com.br/acesso");

        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                findViewById(R.id.splash).setVisibility(View.GONE);
                findViewById(R.id.webview).setVisibility(View.VISIBLE);
            }
        });

//       FirebaseMessaging.getInstance().getToken()
//               .addOnCompleteListener(new OnCompleteListener<String>() {
//                   @Override
//                   public void onComplete(@NonNull Task<String> task) {
//                       if (!task.isSuccessful()) {
//                           Log.w("koike", "Fetching FCM registration token failed", task.getException());
//                           return;
//                       }
//
//                       // Get new FCM registration token
//                       String token = task.getResult();
//                       // czovt8_KTYyIk3Mef75e0n:APA91bFRtKOspG0NF_bI8o0KmewNiPUMvIK67TXHD12JPb8ui9vJFz32718fipnC_w8QZB8yZGXw_cxwf6MgWznUJiMI2EdRgE1vibluRC655xNkOFx_vtwhSswYCuBfb-AQkxCiWklO
//                       // Log and toast
//                       //String msg = getString(R.string.msg_token_fmt, token);
//                       Log.d("koike", token);
//                       //Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
//                   }
//               });
    }
}