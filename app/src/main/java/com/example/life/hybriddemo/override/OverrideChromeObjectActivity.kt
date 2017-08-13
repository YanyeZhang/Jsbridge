package com.example.life.hybriddemo.override

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.life.hybriddemo.R
import kotlinx.android.synthetic.main.activity_webview.*

/**
 * Created by zhangyanye on 2017/8/5.
 * Description:
 */
class OverrideChromeObjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        val webviweSetting = webview.settings
        webviweSetting.allowFileAccess = true
        webviweSetting.javaScriptEnabled = true
        webview.setWebChromeClient(HybridWebChromeClient())
        webview.loadUrl("file:///android_asset/override_demo.html")
    }
}