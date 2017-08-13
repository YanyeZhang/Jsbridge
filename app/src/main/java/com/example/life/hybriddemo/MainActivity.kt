package com.example.life.hybriddemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.example.life.hybriddemo.annotation.JsInterfaceWebviewActivity
import com.example.life.hybriddemo.intercept.InterceptUrlActivity
import com.example.life.hybriddemo.override.OverrideChromeObjectActivity
import org.jetbrains.anko.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linearLayout {
            orientation = LinearLayout.VERTICAL
            lparams {
                height = matchParent
                width = matchParent
            }
            button {
                padding = 12
                text = "注解方式"
                lparams {
                    height = 200
                    width = matchParent
                }
                onClick { startActivity(Intent(this@MainActivity, JsInterfaceWebviewActivity::class.java)) }
            }

            button {
                padding = 12
                text = "重写浏览器对象"
                lparams {
                    height = 200
                    width = matchParent
                }
                onClick { startActivity(Intent(this@MainActivity, OverrideChromeObjectActivity::class.java)) }
            }
            button {
                padding = 12
                text = "拦截请求Url"
                lparams {
                    height = 200
                    width = matchParent
                }
                onClick { startActivity(Intent(this@MainActivity, InterceptUrlActivity::class.java)) }
            }
        }

    }


}
