package com.schoolshieldparent_ui.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.schoolshieldparent_ui.R;

public class Activity_TandC extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_tandc );
        webView = (WebView) findViewById( R.id.webView1 );
        webView.getSettings().setJavaScriptEnabled( true );
        webView.setWebChromeClient( new WebChromeClient() );
        webView.loadUrl( "file:///android_asset/applicationterms/tandc.html" );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition( 0, 0 );

    }
}
