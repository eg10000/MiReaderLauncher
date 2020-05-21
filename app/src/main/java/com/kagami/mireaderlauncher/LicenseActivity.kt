package com.kagami.mireaderlauncher

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class LicenseActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_license)
        val tv = findViewById<TextView>(R.id.tvLicense)
        tv.movementMethod = ScrollingMovementMethod()
        tv.text = assets.open("license.txt").bufferedReader().readText()
    }
}