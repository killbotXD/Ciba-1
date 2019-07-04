package com.example.shashankmohabia.ciba.Core

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.shashankmohabia.ciba.Fragments.MerchantMenuFragment
import com.example.shashankmohabia.ciba.Fragments.MerchantOrdersFragment
import com.example.shashankmohabia.ciba.Fragments.MerchantProfileFragment
import com.example.shashankmohabia.ciba.R
import com.example.shashankmohabia.ciba.UserType.UserTypeSelectionActivity
import com.example.shashankmohabia.ciba.Utils.Constants.currMerchant
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.menu_activity.*
import kotlinx.android.synthetic.main.merchant_activity.*
import kotlinx.android.synthetic.main.toolbar_merchant.*


lateinit var myGoogleSignInClient: GoogleSignInClient
lateinit var mgso: GoogleSignInOptions
val dbmerch = FirebaseFirestore.getInstance()
var menuRef= db.collection("MerchantList").document(currMerchant.id.toString()).collection("Menu")

class MerchantActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

   override fun onNavigationItemSelected(p0: MenuItem): Boolean {
       var fragment:Fragment=MerchantOrdersFragment()
        when(p0.itemId){
            R.id.profile->{
                removeFragment(fragment)
               fragment=MerchantProfileFragment()
               replaceFragment(fragment,R.id.merchant_fragment)

            }
            R.id.menu ->{
                removeFragment(fragment)
                supportFragmentManager.beginTransaction().remove(MerchantProfileFragment()).commit()
                fragment=MerchantMenuFragment()
                replaceFragment(fragment,R.id.merchant_fragment)

            }
            R.id.orders->{
                removeFragment(fragment)
                fragment=MerchantOrdersFragment()
                replaceFragment(fragment,R.id.merchant_fragment)
            }
            R.id.logout->{               logout()
            }
            R.id.contactUs->{                Toast.makeText(this,"CONTACTus",Toast.LENGTH_SHORT).show()
            }
        }
        drawer_layout_merchant.closeDrawer(GravityCompat.START)
        return true
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.merchant_activity)
        val toolbar=findViewById<Toolbar>(R.id.toolbar_menu_merchant)
        setSupportActionBar(toolbar)
        val toggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(this, drawer_layout_merchant, toolbar_menu_merchant,
                R.string.Navigation_drawer_open, R.string.Navigation_drawer_close) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                // Toast.makeText(drawerView.context,"working",Toast.LENGTH_SHORT).show()
                setupFragment()
                invalidateOptionsMenu()
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
               // Toast.makeText(drawerView.context, "working", Toast.LENGTH_SHORT).show()

                setupFragment()
                invalidateOptionsMenu()
            }
        }

        drawer_layout_merchant.addDrawerListener(toggle)
        toggle.syncState()


        nav_view_merchant.setNavigationItemSelectedListener(this)

        //GSO
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        myGoogleSignInClient = GoogleSignIn.getClient(this, gso)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.merchant_menu, menu)

        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun setupFragment() {
        val navigationViewHeader = nav_view_merchant.getHeaderView(0)
        val userdp = navigationViewHeader.findViewById<ImageView>(R.id.userDP)
        val username = navigationViewHeader.findViewById<TextView>(R.id.UserName)
        val useremail = navigationViewHeader.findViewById<TextView>(R.id.UserEmail)
        Glide.with(this).load(currMerchant.profileUrl).override(330, 330).apply(RequestOptions.circleCropTransform()).into(userdp)
        username.text = currMerchant.name
        username.isAllCaps = true
        useremail.text = currMerchant.email
        //CHecking if the text view is null
        if (currMerchant.name.equals("")) {
            Toast.makeText(this, "EMPTY", Toast.LENGTH_SHORT).show()
        }
        username.text = currMerchant.name

    }

    fun logout() {
        val intent = Intent(this, UserTypeSelectionActivity::class.java)
        myGoogleSignInClient.signOut().addOnCompleteListener {
            startActivity(intent)
            Toast.makeText(this, "YOU just logged out", Toast.LENGTH_SHORT).show()
            finish()
        }

    }

    override fun onStart() {
        super.onStart()
        setupFragment()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        addCurrentMerchantData(account!!.email.toString())
        nav_view_merchant.menu.getItem(2).setChecked(true)
    }
    private fun addCurrentMerchantData(email:String) {
        val query = dbmerch.collection("MerchantList").whereEqualTo("email_id",email)
        query.get()
                .addOnSuccessListener {
                    for (doc in it) {
                        currMerchant.name = doc["name"].toString()
                        currMerchant.email = doc["email_id"].toString()
                        currMerchant.paytmNumber = doc["paytmNumber"].toString()
                        currMerchant.profileUrl = doc["prof_pic"].toString()
                        currMerchant.id=doc.id
                    }
                }

    }

    override fun onStop() {
        super.onStop()

    }
}
inline fun FragmentManager.inTransaction(func:FragmentTransaction.() -> FragmentTransaction){
    beginTransaction().func().commit()
}
fun AppCompatActivity.addFragment(fragment:Fragment,Id:Int){
    supportFragmentManager.inTransaction { add(Id,fragment) }
}
fun AppCompatActivity.replaceFragment(fragment:Fragment,Id:Int){
    supportFragmentManager.inTransaction { replace(Id,fragment) }
}
fun AppCompatActivity.removeFragment(fragment: Fragment){
    supportFragmentManager.inTransaction { remove(fragment) }
}

