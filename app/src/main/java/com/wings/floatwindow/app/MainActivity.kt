package com.wings.floatwindow.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val view = findViewById<View>(R.id.bt_enter_second)
        view.setOnClickListener {
            val intent = Intent(this, SecondActivity().javaClass)
            startActivity(intent)
        }
    }
}
