package com.example.shashankmohabia.ciba.Auth

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.shashankmohabia.ciba.Core.MenuActivity
import com.example.shashankmohabia.ciba.Core.MerchantActivity
import com.example.shashankmohabia.ciba.Core.MerchantList
import com.example.shashankmohabia.ciba.R
import com.example.shashankmohabia.ciba.Utils.Constants.currMerchant
import com.example.shashankmohabia.ciba.Utils.Constants.currUser
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

var account: GoogleSignInAccount? = null
val auth = FirebaseAuth.getInstance()
val dbref = FirebaseFirestore.getInstance()


class LoginActivity : AppCompatActivity() {

    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var gso: GoogleSignInOptions
    val RC_SIGN_IN: Int = 1
    var isCustomer: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        val signin = findViewById<View>(R.id.gsignin) as SignInButton
        isCustomer = intent.getBooleanExtra("isCustomer", false)
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)



        signin.setOnClickListener {
            signInGoogle()
        }
    }

    private fun signInGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            account = completedTask.getResult(ApiException::class.java)
            userExists(account, account!!.email.toString())
        } catch (e: ApiException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }
    }


    //checks if a person is a first time user or not
    fun userExists(account: GoogleSignInAccount?, email: String) {
        var tempEmail: String? = null
        var n = 0

        var query = dbref.collection("UserList")
        if (isCustomer.equals(false)) {
            query = dbref.collection("MerchantList")
        }
        query.get().addOnSuccessListener {
            for (collection in it) {
                tempEmail = collection.data["email_id"].toString()
                if (tempEmail.equals(email)) {
                    n++
                }
            }
            if (n > 0) {
                if (isCustomer.equals(true)) {
                    //adds current logged in users' data to an object current user
                    addCurrentUserData()
                } else {
                    //adds current logged in merchants data
                    addCurrentMerchantData()
                }
                /* val navigationViewHeader2=nav_view.getHeaderView(0)
                 val username = navigationViewHeader2.findViewById<TextView>(R.id.UserName)
                 val useremail = navigationViewHeader2.findViewById<TextView>(R.id.UserEmail)
                 username.text= currUser.name
                 username.isAllCaps=true
                 useremail.text= currUser.email*/
                updateUI(account)
                Toast.makeText(this, "user Exitsts", Toast.LENGTH_SHORT).show()


            } else {
                Toast.makeText(this, "User is not registered please register", Toast.LENGTH_SHORT).show()

                if (isCustomer.equals(true)) {
                    val int = Intent(this, UserPop::class.java)
                    int.putExtra("email", account!!.email.toString())
                    int.putExtra("picURL", account.photoUrl.toString())
                    startActivity(int)
                } else {
                    val int = Intent(this, MerchantPop::class.java)
                    int.putExtra("email", account!!.email.toString())
                    int.putExtra("picURL", account.photoUrl.toString())
                    startActivity(int)
                }
            }
        }


    }

    private fun addCurrentMerchantData() {
        val query = dbref.collection("MerchantList").whereEqualTo("email_id", account!!.email.toString())
        query.get()
                .addOnSuccessListener {
                    for (doc in it) {
                        currMerchant.name = doc["name"].toString()
                        currMerchant.email = doc["email_id"].toString()
                        currMerchant.paytmNumber = doc["paytmNumber"].toString()
                        currMerchant.profileUrl = doc["profileUrl"].toString()
                        currMerchant.id=doc.id
                    }
                }
    }

    private fun addCurrentUserData() {
        val query = dbref.collection("UserList").whereEqualTo("email_id", account!!.email.toString())
        query.get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        currUser.roll = document["Roll number"].toString()
                        currUser.add = document["address"].toString()
                        currUser.email = document["email_id"].toString()
                        currUser.name = document["name"].toString()
                        currUser.number = document["number"].toString()
                        currUser.profileUrl = document["prof_pic"].toString()
                    }
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

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            updateUI(account)
        }
    }
}