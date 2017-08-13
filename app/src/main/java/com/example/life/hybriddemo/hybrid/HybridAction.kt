package com.example.life.hybriddemo.hybrid

import android.webkit.WebView
import org.json.JSONObject

/**
 * Created by zhangyanye on 2017/8/5.
 * Description:
 */
interface HybridAction {
    fun doAction(params: JSONObject, webView: WebView, callback: (result: Any) -> Unit)
}