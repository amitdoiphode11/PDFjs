package com.example.pdfjs

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    private var webView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webView = findViewById<View>(R.id.webview) as WebView
        webView!!.settings.allowContentAccess = true
        webView!!.settings.allowFileAccess = true

        val settings = webView!!.settings
        settings.javaScriptEnabled = true
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) //required for running javascript on android 4.1 or later
        {
            settings.allowFileAccessFromFileURLs = true
            settings.allowUniversalAccessFromFileURLs = true
        }*/
        settings.builtInZoomControls = true
        webView!!.webChromeClient = WebChromeClient()
        val path = Uri.parse(Environment.getExternalStorageDirectory().toString() + "/data/test.pdf")
        try {
            val ims = assets.open("pdfviewer/index.html")
            var line = getStringFromInputStream(ims)
            if (line.contains("THE_FILE")) {
                line = line.replace("THE_FILE", path.toString())
                val fileOutputStream = openFileOutput("index.html", MODE_PRIVATE)
            }
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            Log.e("TAG", "onCreate: ",e )
        }
        webView!!.loadUrl("file://$filesDir/index.html")
    }


    /* override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         super.onCreateOptionsMenu(menu)
         val inflater = menuInflater
         inflater.inflate(R.menu.pdf_viewer, menu)
         return true
     }

     override fun onOptionsItemSelected(item: MenuItem): Boolean {
         return when (item.itemId) {
             R.id.action_settings -> true
             R.id.action_next -> {
                 webView!!.loadUrl("javascript:onNextPage()")
                 super.onOptionsItemSelected(item)
             }
             R.id.action_previous -> {
                 webView!!.loadUrl("javascript:onPrevPage()")
                 super.onOptionsItemSelected(item)
             }
             else -> super.onOptionsItemSelected(item)
         }
     }*/

    // convert InputStream to String
    private fun getStringFromInputStream(`is`: InputStream): String {
        var br: BufferedReader? = null
        val sb = StringBuilder()
        var line: String?
        try {
            br = BufferedReader(InputStreamReader(`is`))
            while (br.readLine().also { line = it } != null) {
                sb.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (br != null) {
                try {
                    br.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return sb.toString()
    }

}