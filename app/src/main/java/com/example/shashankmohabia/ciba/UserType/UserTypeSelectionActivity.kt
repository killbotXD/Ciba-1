package com.example.shashankmohabia.ciba.UserType

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.example.shashankmohabia.ciba.Auth.LoginActivity

import com.example.shashankmohabia.ciba.R
import com.google.android.gms.auth.api.signin.GoogleSignIn

class UserTypeSelectionActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_type_selection)

    val btnCustomer=findViewById<ImageView>(R.id.customer_option) as ImageView
    val btnMerchant =  findViewById<ImageView>(R.id.merchant_option)as ImageView
        var isCustomer : Boolean = false
        //can minimize this code idiot hu mai
        btnCustomer.setOnClickListener {
            isCustomer=true
            val intent= Intent(this, LoginActivity::class.java)
            intent.putExtra("isCustomer",isCustomer)
            startActivity(intent)
            finish()
        }
        btnMerchant.setOnClickListener {
            val intent= Intent(this, LoginActivity::class.java)
            intent.putExtra("isCustomer",isCustomer)
            startActivity(intent)
            finish()
        }


    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val account = GoogleSignIn.getLastSignedInAccount(this)
        //if (account != null) {
           // updateUI(account)
        //}
    }
}