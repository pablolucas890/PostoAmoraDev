package com.amoradev.posto;

import static android.net.Uri.decode;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.amoradev.posto.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            //super.onBackPressed();
            webView.loadUrl("https://www.posto.amoradev.com.br/");
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.setWebViewClient(new WebViewClient());
        WebSettings settings = webView.getSettings();

        settings.setUseWideViewPort(true);
        //enableHTML5AppCache(settings);
        webView.loadUrl("https://www.posto.amoradev.com.br/");

        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                findViewById(R.id.splash).setVisibility(View.GONE);
                findViewById(R.id.webview).setVisibility(View.VISIBLE);
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                String ippo_share_prefix = "https://www.posto.amoradev.com.br/";

                if (url != null && url.startsWith(ippo_share_prefix)) {
                    String message = url.substring(ippo_share_prefix.length());
                    message = decode( message );
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, message);
                    sendIntent.setType("text/plain");

                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    //startActivity(shareIntent);
                    view.getContext().startActivity(shareIntent);
                    return true;
                }
                // open external browser for the following
                else if (url != null && !url.contains("ippo.com.br") && !url.contains("jotfor") &&
                        (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("whatsapp://"))) {
                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                } else {
                    return false;
                }
            }
        });

        FirebaseMessaging.getInstance().getToken()
            .addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {
                  if (!task.isSuccessful()) {
                    Log.w("error token", "Fetching FCM registration token failed", task.getException());
                    return;
                  }

                  // Get new FCM registration token
                  String token = task.getResult();

                  // Log and toast
                  Log.d("token", token);
                }

//                @Override
//                public void onNewToken(@NonNull String token) {
//                    Log.d("new token", "Refreshed token: " + token);
//
//                    // If you want to send messages to this application instance or
//                    // manage this apps subscriptions on the server side, send the
//                    // FCM registration token to your app server.
//                    // sendRegistrationToServer(token);
//                }
            });
    }
}