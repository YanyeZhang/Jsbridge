package com.example.life.hybriddemo.hybrid

import android.webkit.WebView
import android.widget.Toast
import org.json.JSONObject

/**
 * Created by zhangyanye on 2017/8/5.
 * Description:
 */
class HybridActionDefault : HybridAction {
    override fun doAction(params: JSONObject, webView: WebView, callback: (result: Any) -> Unit) {
        Toast.makeText(webView.context, params.toString(), Toast.LENGTH_SHORT).show()
        callback("result")
    }
}