package com.example.life.hybriddemo.intercept

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebViewClient
import com.example.life.hybriddemo.R
import kotlinx.android.synthetic.main.activity_webview.*

/**
 * Created by zhangyanye on 2017/8/5.
 * Description:
 */
class InterceptUrlActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        val webviweSetting = webview.settings
        webviweSetting.allowFileAccess = true
        webviweSetting.javaScriptEnabled = true
        webview.loadUrl("file:///android_asset/intercept_url.html")
        webview.setWebViewClient(HybridWebViewClient())
    }
}