package com.wings.floatwindow.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wings.floatwindow.FloatImageView
import com.wings.floatwindow.GlobalViewManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        GlobalViewManager.addView(
//            application,
//            R.layout.activity_float
//        );
    }
}
