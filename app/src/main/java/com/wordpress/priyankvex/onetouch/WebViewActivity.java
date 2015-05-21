package com.wordpress.priyankvex.onetouch;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by priyank on 1/3/15.
 * Class to show the webview. All the links of the app should be opened in this web view.
 */
public class WebViewActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_webview);

        WebView myWebView = (WebView) findViewById(R.id.webView);
        myWebView.setWebViewClient(new MyBrowser());

        Bundle b = getIntent().getExtras();
        String url = b.getString("url");

        if (!url.equals("")){
            // load the web view
            myWebView.loadUrl(url);
        }

    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("https://dl.dropboxusercontent")){
                // Otherwise, give the default behavior (open in browser)
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
            else{
                view.loadUrl(url);
                return true;
            }
        }
    }


}
