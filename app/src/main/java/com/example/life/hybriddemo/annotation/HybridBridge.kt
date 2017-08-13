package com.example.life.hybriddemo.annotation

import android.os.Build
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.example.life.hybriddemo.hybrid.HybridAction
import com.example.life.hybriddemo.hybrid.HybridActionDefault
import org.json.JSONObject

/**
 * Created by zhangyanye on 2017/8/5.
 * Description:
 */
class HybridBridge {
    val TAG = "HbbridBridage"
    var webView: WebView? = null
    var classPrefixs: MutableSet<String> = mutableSetOf()

    constructor(webView: WebView, packageNames: Set<String>) {
        this.webView = webView
        addHybridPackage(packageNames);
    }

    fun addHybridPackage(packageNames: Set<String>) {
        for (packageName in packageNames) {
            classPrefixs.add("${packageName}.HybridAction")
        }
    }

    @JavascriptInterface
    fun invoke(arg: String) {
        Log.e(TAG, arg)
        var argJson = JSONObject(arg)
        val messageId = argJson.getInt("id")
        val target = argJson.getString("target")
        val data = argJson.optJSONObject("data") ?: JSONObject()
        val actionClass = getClassForTarget(target)
        val action = actionClass?.newInstance() ?: HybridActionDefault()
        webView?.post {
            (action as HybridAction).doAction(data, webView as WebView, fun(result: Any) {
                //前端为什么要塞入bridgeName
                //var callbackUrl = "javascript:${Config.bridgeName}.callback"
                var callbackUrl = "javascript:callback"
                if (result is String) {
                    callbackUrl += String.format("(%s,'%s')", messageId, result)
                } else {
                    callbackUrl += String.format("(%s, %s)", messageId, result)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    webView?.evaluateJavascript(callbackUrl, null)
                } else {
                    webView?.loadUrl(callbackUrl)
                }

            })
        }
    }

    //todo 支持多包名的情况下不try catch（改进or替换实现）
    fun getClassForTarget(target: String): Class<*>? {
        for (classPrefix in classPrefixs) {
            try {
                return Class.forName("$classPrefix${target.substring(0, 1).toUpperCase()}${target.substring(1)}")
            } catch (e: ClassNotFoundException) {
            }
        }
        return null
    }
}