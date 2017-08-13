虽然当前Rect Natvie、Weex等动态化解决方案大行其道，不过最主流、稳定的混合开发还是Hybrid，毕竟iOS能不能使用动态化还要看苹果。因此对Hybrid的了解还是很有必要的。而搭建Hybrid框架首先要做的就是JsBridge,让H5页面拥有客户端的能力，能大大提升用页面性能。
而本文的目的就是把当前的所有的JsBridge方案进行一次简单评估。

在Android中，JsBridge的实现大体分为以下三种

1. google推荐方案 JsInterface
2. 拦截并重写Js消息框
3. 对请求url进行拦截

以下我对上述的方案进行一定的比较，所有的方案没有好坏，只有适不适合，所以具体采取哪一种方案还是要各取所需。
 
### JsInterface
作为官方提供的解决方案，却并不被广大开发者采纳，因为在android 4.2以下手机webview存在安全漏洞，不过此问题已经在android 4.2以上修复。因此在使用过程中会产生兼容性与安全问题。按照现如今的操作系统的分布来看，假如app的Mini Target在4.2之上，就无需考虑此问题。

其实现方案是通过给js注册一个命名空间，通过反射来执行相对应的操作。

```
//kotlin for android
class JsBridge() {
	@JavascriptInterface
	fun invoke(msg:String) {
		//todo something
	}
 }
webview.addJavascriptInterface(JsBridge(), "app")  

//js 
function invoke(str){
	app.invoke(str)
}
```
**优点:**

* 作为官方推荐的方案，自然在后续会有不断的维护与迭代
* 实现方便简单

**缺点:**

* 如上所诉，需考虑兼容性与安全问题
* 回调方式需要自行定义


### 拦截并重写Js消息框
在android中假如没有调用以下方案，js的消息框是不会被弹出的

```
//kotlin for android
webview.setWebChromeClient(object : WebChromeClient())
```
对应js中的三中方式

```scirpt
alert("文本")
```
```
confirm("文本")
```
```
prompt("文本","默认值")
```
android的 **WebChromeClient**也会有对应的拦截方式

```scirpt
override fun onJsPrompt(view: WebView?, url: String?, message: String?, defaultValue: String?, result: JsPromptResult?): Boolean 
```

```scirpt
override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean 
```
```scirpt
override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean
```
所以只需要定义好拦截的方式就可以对其进行hook，而一般情况下，会选择对prompt进行拦截，因为与其他两个比prompt是可以自定义返回值的。针对这样的实现会设计一套协议，举个例子：
> app://invoke?content='hello'

客户端只有发现以app开头的参数才会进行拦截，并对其进行解析。否则就调用正常的prompt。这个方案为很多公司采取的方案

**优点:**

* 已提供回调方式；
* 实现方便简单；
* 无安全与兼容性问题；

**缺点:**

* 就个人而言，我并不喜欢在一件事中穿插其他事的设计；


###  对请求url进行拦截
Android的WebViewClient中有以下方法

```
override fun shouldOverrideUrlLoading(webview: WebView?, url: String?): Boolean 
```
即可以获取webview上的请求url，其实现方案与第二种一样，制定一种路由协议，客户端对所有请求进行拦截，符合规范的交友native处理。

**优点:**

* native 拦截不了的url可以交给h5本身去操作，增加了灵活性；
* 无安全与兼容性问题；
* 设计合理

**缺点:**

* 实现成本较高；
* 回调方式需要自行定义；

