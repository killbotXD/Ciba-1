package com.example.shashankmohabia.ciba.UI

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.Toast
import com.example.shashankmohabia.ciba.R
import com.example.shashankmohabia.ciba.UserType.isCustomer
import com.example.shashankmohabia.ciba.Utils.Constants.currUser
import com.example.shashankmohabia.ciba.Utils.Extensions.data
import java.lang.Thread.sleep


class SplashScreen : AppCompatActivity(){


    override fun onCreate(savedInstancestate: Bundle?){
        super.onCreate(savedInstancestate)
        setContentView(R.layout.splashscreen)

        val intent= Intent(this, Info::class.java)
        Thread{
            try {
                sleep(1500)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } finally {
                startActivity(intent)
                finish()
            }
        }.start()


    }


    override fun onStart() {
        super.onStart()
    data.items.clear()

       // searchData.searchItems.clear()
    }

}