package com.example.shashankmohabia.ciba.Auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.shashankmohabia.ciba.Core.MenuActivity
import com.example.shashankmohabia.ciba.Core.MerchantActivity
import com.example.shashankmohabia.ciba.Core.mGoogleSignInClient
import com.example.shashankmohabia.ciba.Core.myGoogleSignInClient
import com.example.shashankmohabia.ciba.R
import com.example.shashankmohabia.ciba.UserType.UserTypeSelectionActivity
import com.example.shashankmohabia.ciba.Utils.Constants.currMerchant
import com.google.firebase.firestore.FirebaseFirestore

class MerchantPop:Activity(){
    var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.merchant_pop_layout)

        val name= this.findViewById<EditText>(R.id.getInfoNameMerchant)
        val paytmNumber=findViewById<EditText>(R.id.getInfoNumberMerchant)
        val button=findViewById<Button>(R.id.submit_button_merchant)
        val email = intent.getStringExtra("email")
        val picURL = intent.getStringExtra("picURL")

        val dm= DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width:Int=dm.widthPixels
        val height:Int=dm.heightPixels

        window.setLayout((width*0.75).toInt(), (height*0.5).toInt())
        button.setOnClickListener {
            if(name.text.isEmpty()||paytmNumber.text.isEmpty()){
                Toast.makeText(this,"Enter details",Toast.LENGTH_SHORT).show()
            }else{
                addInfo(name.text.toString(),paytmNumber.text.toString(),picURL,email)
                updateUI()
            }

        }
    }

    private fun updateUI() {
        val intent = Intent(this, AddMenuForFirstTime::class.java)
        startActivity(intent)
        finish()
    }

    private fun addInfo(name: String, paytmNumber: String,picURL:String,email:String) {
        val merchant = HashMap<String, Any>()
        merchant["email_id"] = email
        merchant["name"] = name
        merchant["paytmNumber"] = paytmNumber
        merchant["prof_pic"] = picURL
        merchant["id"]=""
        currMerchant.email = email
        currMerchant.name = name
        currMerchant.paytmNumber = paytmNumber
        currMerchant.profileUrl = picURL

        db.collection("MerchantList")
                .add(merchant).addOnSuccessListener {
                    it.update("id",it.id)
                    currMerchant.id=it.id
                    Toast.makeText(this, "User Added to list of users", Toast.LENGTH_SHORT).show()
                    currMerchant.id=it.id
                }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        logout()
    }
    fun logout() {
        val intent = Intent(this, UserTypeSelectionActivity::class.java)
        myGoogleSignInClient.signOut().addOnCompleteListener {
            startActivity(intent)
            Toast.makeText(this, "YOU just logged out", Toast.LENGTH_SHORT).show()
            finish()
        }

    }
}