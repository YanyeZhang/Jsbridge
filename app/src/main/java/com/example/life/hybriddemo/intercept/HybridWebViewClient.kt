package com.example.life.hybriddemo.intercept

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.life.hybriddemo.hybrid.HybridAction

/**
 * Created by zhangyanye on 2017/8/5.
 * Description:
 */
class HybridWebViewClient : WebViewClient() {
    val TAG = "ZCWebViewClient"

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        Log.d(TAG, "webView page start :$url")
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        Log.d(TAG, "webView page finish :$url")
    }

    override fun shouldOverrideUrlLoading(webview: WebView?, url: String?): Boolean {
        webview?.let {
            shouldOverrideUrlLoading(webview.context, url)
            return true
        }
        return false
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun shouldOverrideUrlLoading(webview: WebView?, request: WebResourceRequest?): Boolean {
        webview?.let {
            var url = request?.url.toString()
            shouldOverrideUrlLoading(webview.context, url)
            return true
        }
        return false
    }

    override fun shouldInterceptRequest(webview: WebView, url: String): WebResourceResponse? {

        return null
    }

    private fun shouldOverrideUrlLoading(context: Context, url: String?) {
        var uri = Uri.parse(url)
        val scheme = uri.getScheme()
        if (scheme == "app") {
            if (uri.getAuthority().equals("invoke")) {
                val params = HashMap<String, String>()
                val collection = uri.getQueryParameterNames()
                for (name in collection) {
                    params.put(name, uri.getQueryParameter(name))
                }
            }
            Toast.makeText(context, url, Toast.LENGTH_LONG).show()
        }
    }
}