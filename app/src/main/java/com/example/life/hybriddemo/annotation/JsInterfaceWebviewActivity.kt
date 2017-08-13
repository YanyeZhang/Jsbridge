package com.example.life.hybriddemo.annotation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.life.hybriddemo.R
import kotlinx.android.synthetic.main.activity_webview.*


/**
 * Created by zhangyanye on 2017/8/5.
 * Description:
 */
class JsInterfaceWebviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        val webviweSetting = webview.settings
        webviweSetting.allowFileAccess = true
        webviweSetting.javaScriptEnabled = true
        webview.addJavascriptInterface(HybridBridge(webview, setOf(baseContext.packageName)),"app")
        webview.loadUrl("file:///android_asset/annotation_demo.html")
    }
}