package com.example.life.hybriddemo.override

import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.example.life.hybriddemo.MainActivity
import android.content.Intent
import android.net.Uri
import android.widget.Toast


/**
 * Created by zhangyanye on 2017/8/5.
 * Description:
 */
class HybridWebChromeClient : WebChromeClient() {

    override fun onJsPrompt(view: WebView?, url: String?, message: String?, defaultValue: String?, result: JsPromptResult?): Boolean {
        val uri = Uri.parse(message)
        val scheme = uri.getScheme()
        if (scheme == "app") {
            if (uri.getAuthority().equals("invoke")) {
                val params = HashMap<String,String>()
                val collection = uri.getQueryParameterNames()
                for (name in collection) {
                    params.put(name, uri.getQueryParameter(name))
                }
                Toast.makeText(view?.context,"MESSAGE:${message},DEFAULT:${defaultValue}",Toast.LENGTH_LONG).show()
                result?.confirm("success")
            }
            return true
        }
        return super.onJsPrompt(view, url, message, defaultValue, result)
    }

    override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
        return super.onJsAlert(view, url, message, result)
    }

    override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
        return super.onJsConfirm(view, url, message, result)
    }
}
