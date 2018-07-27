package clover.studio.clovernews.features.headline.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import android.webkit.WebView
import android.webkit.WebViewClient
import clover.studio.clovernews.R
import kotlinx.android.synthetic.main.activity_headline.*


const val HEADLINE_URL = "headline_url"
const val HEADLINE_TITLE = "headline_title"

fun Context.HeadlineActivity(url: String, title: String): Intent {
    val intent = Intent(this, HeadlineActivity::class.java)
    intent.putExtra(HEADLINE_URL, url)
    intent.putExtra(HEADLINE_TITLE, title)
    return intent
}

class HeadlineActivity : AppCompatActivity() {

    private var headlineUrl: String = ""

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_headline)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = this.intent.extras.getString(HEADLINE_TITLE)
        toolbar.setTitleTextColor(Color.WHITE)
        this.headlineUrl = this.intent.extras.getString(HEADLINE_URL)
        headlineWebView.settings.javaScriptEnabled = true
        headlineWebView.loadUrl(headlineUrl)
        headlineWebView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                loaderView.visibility = GONE
            }
        }
    }
}
