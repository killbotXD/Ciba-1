package com.example.shashankmohabia.ciba.Auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.shashankmohabia.ciba.Core.MenuActivity
import com.example.shashankmohabia.ciba.Core.mGoogleSignInClient

import com.example.shashankmohabia.ciba.R
import com.example.shashankmohabia.ciba.UserType.UserTypeSelectionActivity
import com.example.shashankmohabia.ciba.Utils.Constants.currUser
import com.google.firebase.firestore.FirebaseFirestore

class UserPop : Activity() {

    var db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_pop_layout)

        val name = this.findViewById<EditText>(R.id.getInfoName)
        val hostel = findViewById<EditText>(R.id.getInfoHostel)
        val number = findViewById<EditText>(R.id.getInfoNumber)
        val rollNum = findViewById<EditText>(R.id.getInfoRoll)
        val email = intent.getStringExtra("email")
        val picURL = intent.getStringExtra("picURL")

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width: Int = dm.widthPixels
        val height: Int = dm.heightPixels

        window.setLayout((width * 0.75).toInt(), (height * 0.65).toInt())

        val btn = findViewById<Button>(R.id.submit_button)
        btn.setOnClickListener {
            if (name.text.isEmpty() || hostel.text.isEmpty() || number.text.isEmpty() || rollNum.text.isEmpty()) {
                Toast.makeText(this, "DETAILS bharo BSDK", Toast.LENGTH_SHORT).show()
            } else {
                addInfo(name.text.toString(), hostel.text.toString(), number.text.toString(), rollNum.text.toString(), email, picURL)
                updateUI()
            }


        }
    }

    private fun addInfo(name: String, hostel: String, number: String, rollNum: String, email: String, picURL: String) {
        val user = HashMap<String, Any>()
        user["Roll number"] = rollNum
        user["address"] = hostel
        user["email_id"] = email
        user["name"] = name
        user["number"] = number
        user["prof_pic"] = picURL
        user["id"]=""
        currUser.roll = rollNum
        currUser.add = hostel
        currUser.email = email
        currUser.name = name
        currUser.number = number
        currUser.profileUrl = picURL

        db.collection("UserList")
                .add(user).addOnSuccessListener {
                    it.update("id",it.id)
                    currUser.id=it.id

                    Toast.makeText(this, "User Added to list of users", Toast.LENGTH_SHORT).show()
                }

    }

    private fun updateUI() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        logout()
    }

    override fun onDestroy() {

        super.onDestroy()

    }

    fun logout() {
        val intent = Intent(this, UserTypeSelectionActivity::class.java)
        mGoogleSignInClient.signOut().addOnCompleteListener {
            startActivity(intent)
            Toast.makeText(this, "YOU just logged out", Toast.LENGTH_SHORT).show()
            finish()
        }

    }
}
