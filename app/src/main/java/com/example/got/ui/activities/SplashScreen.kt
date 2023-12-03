package com.example.got.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.got.R
import kotlin.concurrent.thread

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        thread{
            Thread.sleep(2000)
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }
}