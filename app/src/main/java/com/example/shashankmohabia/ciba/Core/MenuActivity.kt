package com.example.shashankmohabia.ciba.Core

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.example.shashankmohabia.ciba.R
import com.example.shashankmohabia.ciba.UserType.UserTypeSelectionActivity
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import android.support.v4.view.MenuItemCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.SearchView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.shashankmohabia.ciba.Auth.LoginActivity
import com.example.shashankmohabia.ciba.Fragments.CustomerMenuFragment
import com.example.shashankmohabia.ciba.Fragments.CustomerOrdersFragment
import com.example.shashankmohabia.ciba.Fragments.CustomerProfileFragment
import com.example.shashankmohabia.ciba.Utils.Constants.currMerchant
import com.example.shashankmohabia.ciba.Utils.Constants.currUser
import com.example.shashankmohabia.ciba.Utils.Extensions.*
import com.squareup.picasso.Picasso
import es.dmoral.toasty.Toasty

import kotlinx.android.synthetic.main.menu_activity.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.nav_header.view.*
import org.w3c.dom.Text


lateinit var mGoogleSignInClient: GoogleSignInClient
lateinit var gso: GoogleSignInOptions
val db = FirebaseFirestore.getInstance()
val menuref = db.collection("MerchantList/${currMerchant.id.toString()}$/Menu")
var searchAdapter: SearchAdapter? = null

//I think i should rename this to customer activity
class MenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        var fragment:Fragment=CustomerMenuFragment()
        when(p0.itemId){
            R.id.profile->{
                removeFragment(fragment)
                fragment=CustomerProfileFragment()
                replaceFragment(fragment,R.id.customer_fragment)

            }
            R.id.menu ->{
                removeFragment(fragment)
                fragment=CustomerMenuFragment()
                replaceFragment(fragment,R.id.customer_fragment)

            }
            R.id.orders->{
                removeFragment(fragment)
                fragment= CustomerOrdersFragment()
                replaceFragment(fragment,R.id.customer_fragment)
            }
            R.id.logout->{               logout()
            }
            R.id.contactUs->{                Toast.makeText(this,"CONTACTus",Toast.LENGTH_SHORT).show()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_activity)
        val toolbar_menu: Toolbar = findViewById<Toolbar>(R.id.toolbar_menu)
        setSupportActionBar(toolbar_menu)

        val toggle:ActionBarDrawerToggle =object : ActionBarDrawerToggle(this, drawer_layout, toolbar_menu,
                R.string.Navigation_drawer_open, R.string.Navigation_drawer_close){
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                setupFragment()
                invalidateOptionsMenu()
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)


                setupFragment()
                invalidateOptionsMenu()
            }
        }

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()


        nav_view.setNavigationItemSelectedListener(this)

        //GSO
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        //function to add the user dp and name with email
        //setupFragment()




        //loging out here from toolbar
        /*  val Add = findViewById<Button>(R.id.db_add) as Button
              val roll = findViewById<TextView>(R.id.roll) as TextView
              val addr = findViewById<TextView>(R.id.addr)as TextView
              val email = findViewById<TextView>(R.id.email)as TextView
              val name = findViewById<TextView>(R.id.name)as TextView
              val ord = findViewById<TextView>(R.id.order)as TextView
              val prof = findViewById<TextView>(R.id.prof)as TextView
              val show = findViewById<Button>(R.id.db_show) as Button

              Add.setOnClickListener {
                  Toast.makeText(this,"button working",Toast.LENGTH_SHORT).show()
                  val user = HashMap<String, Any>()
                  user["Roll number"] = "B18CSE053"
                  user["address"] = "263 G6"
                  user["email_id"] = "sonawane.1@iitj.ac.in"
                  user["name"] = "Soham"
                  user["orders"] = "NULL"
                  user["prof_pic"] = "NULL"

      // Add a new document with a generated ID
                  db.collection("User")
                          .add(user)
                          .addOnSuccessListener { documentReference ->
                              Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.id)

                          }
                          .addOnFailureListener { e ->
                              Log.w(TAG, "Error adding document", e)
                          }
              }*/

        /*show.setOnClickListener {
          var query = db.collection("User").whereEqualTo("Roll number","B18CSE053")
                  query.get()
                    .addOnSuccessListener { result ->
                        for (document in result ) {

                                Log.d(TAG, document.id + " => " + document.data)

                                roll.text = document.data["Roll number"].toString()
                                addr.text = document.data["address"]!!.toString()
                                email.text = document.data["email_id"]!!.toString()
                                name.text = document.data["name"]!!.toString()
                                ord.text = document.data["orders"]!!.toString()
                                prof.text = document.data["prof_pic"]!!.toString()


                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.w(TAG, "Error getting documents.", exception)
                    }

        }*/

    }

    private fun setupFragment() {

        val navigationViewHeader=nav_view.getHeaderView(0)
        val userdp=navigationViewHeader.findViewById<ImageView>(R.id.userDP)
        val username = navigationViewHeader.findViewById<TextView>(R.id.UserName)
        val useremail = navigationViewHeader.findViewById<TextView>(R.id.UserEmail)
        Glide.with(this).load(currUser.profileUrl).override(330,330).apply(RequestOptions.circleCropTransform()).into(userdp)
        username.text= currUser.name
        username.isAllCaps=true
        useremail.text= currUser.email
        //CHecking if the text view is null

        username.text= currUser.name


    }


    override fun onBackPressed() {
        if(drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START)
        }else{
        super.onBackPressed()}
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_menu, menu)
        val searchView = MenuItemCompat.getActionView(menu!!.findItem(R.id.search_menu)) as SearchView
        val searchManager: SearchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String): Boolean {
                if (!text.trim { it <= ' ' }.isEmpty()) {
                    search(text)
                    filteredData.filterData.clear()
                    setupSearchRecyclerView()
                } else {
                }
                setupSearchRecyclerView()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filteredData.filterData.clear()

                if (newText.trim { it <= ' ' }.isEmpty()) {



                    filteredData.filterData.clear()

                } else {

                    search(newText)
                    setupSearchRecyclerView()


                }

                return false
            }
        })
        searchView.setOnSearchClickListener {


        }
        searchManager.setOnCancelListener {


        }
        searchView.setOnCloseListener {

            return@setOnCloseListener false
        }


        return true
    }

    private fun search(s: String) {
        var name: String? = null
        menuref.get().addOnSuccessListener {
            for (collection in it) {
                var singleDataItem = ItemData()

                name = collection.data["name"].toString()
                s.toString().toLowerCase()
                name!!.toLowerCase()
                //lol I made a function to compare them :P
                if (checkIfEqual(name!!.toLowerCase(), s)) {
                    singleDataItem.preptime = collection.data["preptime"].toString()
                    singleDataItem.name = name
                    singleDataItem.price = collection.getDouble("price")!!.toInt()
                    singleDataItem.vegOrNot = collection.getBoolean("vegOrNot")
                    singleDataItem.availableOrNot = collection.data["availability"].toString()
                    singleDataItem.id = collection.id
                    filteredData.filterData.add(singleDataItem)
                    name = null
                    setupSearchRecyclerView()
                }
            }
            Toast.makeText(this, "Showing the most relevant", Toast.LENGTH_LONG).show()
        }


    }

    private fun checkIfEqual(name: String, s: String): Boolean {

        for (i in s.indices) {
            if (!name[i].equals(s[i])) {
                return false
            }
        }
        return true
    }


    private fun setupSearchRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.customer_recycler_view_menu)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager


        searchAdapter = SearchAdapter(this, filteredData.filterData)
        recyclerView.adapter = searchAdapter

    }

    //menu
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {
            R.id.sign_out -> logout()
            R.id.notification -> GotoNotification()
            R.id.current_orders -> GotoCurrOrders()
            R.id.menu_cart -> updateUI()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateUI() {
        val intent = Intent(this, Cart::class.java)
        startActivity(intent)
    }

     fun logout() {
        val intent = Intent(this, UserTypeSelectionActivity::class.java)
        mGoogleSignInClient.signOut().addOnCompleteListener {
            startActivity(intent)
            Toast.makeText(this, "YOU just logged out", Toast.LENGTH_SHORT).show()
            finish()
        }

    }

    private fun GotoNotification() {
        Toast.makeText(this, "Need to create the activity for notification", Toast.LENGTH_SHORT).show()
    }

    private fun GotoCurrOrders() {
        Toast.makeText(this, "Need to create the activity for notification", Toast.LENGTH_SHORT).show()

    }



    override fun onStart() {
        super.onStart()
        setupFragment()
        nav_view.menu.getItem(1).setChecked(true)
    }

}