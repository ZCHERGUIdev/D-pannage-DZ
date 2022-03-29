package com.zcdev.dpannagedz.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.zcdev.dpannagedz.MainActivity
import com.zcdev.dpannagedz.R
import com.zcdev.dpannagedz.activities.accounts.AccountActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar!!.hide()
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this,AccountActivity::class.java))
            finish()
        },3000)
    }
}