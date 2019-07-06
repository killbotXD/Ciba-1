package com.example.shashankmohabia.ciba.UserType

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.example.shashankmohabia.ciba.Auth.LoginActivity
import com.example.shashankmohabia.ciba.Core.MenuActivity
import com.example.shashankmohabia.ciba.Core.MerchantActivity

import com.example.shashankmohabia.ciba.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

var isCustomer : Boolean = true
class UserTypeSelectionActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_type_selection)

    val btnCustomer=findViewById<ImageView>(R.id.customer_option) as ImageView
    val btnMerchant =  findViewById<ImageView>(R.id.merchant_option)as ImageView

        //can minimize this code idiot hu mai
        btnCustomer.setOnClickListener {
            isCustomer=true
            val intent= Intent(this, LoginActivity::class.java)
            intent.putExtra("isCustomer",isCustomer)
            startActivity(intent)
            finish()
        }
        btnMerchant.setOnClickListener {
            isCustomer=false
            val intent= Intent(this, LoginActivity::class.java)
            intent.putExtra("isCustomer",isCustomer)
            startActivity(intent)
            finish()
        }


    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        //need to use shared prefrences on
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            updateUI(account)
        }
    }
    fun updateUI(account: GoogleSignInAccount?) {
        if (isCustomer.equals(true)) {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, MerchantActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}