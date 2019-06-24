package com.example.shashankmohabia.ciba.Auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import com.example.shashankmohabia.ciba.Core.MerchantActivity
import com.example.shashankmohabia.ciba.R
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.add_menu.*

class AddMenuForFirstTime:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_menu)
        setSupportActionBar(toolbar_add_menu)

        add_btn.setOnClickListener {
            if(item_name.text.isNullOrEmpty()){item_name.setError("This Field Cannot Be Empty")}
            else if(item_preptime.text.isNullOrEmpty()){item_preptime.setError("This Field Cannot Be Empty")}
            else if(item_price.text.isNullOrEmpty()){item_price.setError("This Field Cannot Be Empty")}
            else{
                addItem(item_name.text.toString(),item_preptime.text.toString(),item_price.text.toString())
                Toasty.success(this,"one item added").show()}

        }

    }

    private fun addItem(name: String, prepTime: String, price: String) {


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.add_menu_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val intent = Intent(this,MerchantActivity::class.java)
       // startActivity(intent)

        finish()
        return super.onOptionsItemSelected(item)
    }
}