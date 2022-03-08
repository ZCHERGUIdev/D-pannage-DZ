package com.zcdev.dpannagedz.activities.accounts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.zcdev.dpannagedz.OurDriversActivity
import com.zcdev.dpannagedz.R

class AccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        supportActionBar!!.hide()



    }


    fun onClick(view:View){
        var intent=Intent(this,OurDriversActivity::class.java)
        startActivity(intent)
    }
}