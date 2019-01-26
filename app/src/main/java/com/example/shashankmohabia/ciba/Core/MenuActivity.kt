package com.example.shashankmohabia.ciba.Core

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
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
import android.support.v7.widget.SearchView
import com.example.shashankmohabia.ciba.Utils.Extensions.*


lateinit var mGoogleSignInClient: GoogleSignInClient
lateinit var gso:GoogleSignInOptions
val db = FirebaseFirestore.getInstance()
val menuref =db.collection("Users")
var user2 = HashMap<String, Any>()
var adapter : MenuAdapter? = null
var searchAdapter : SearchAdapter? = null
val query = menuref.orderBy("name",Query.Direction.ASCENDING)

class MenuActivity: AppCompatActivity(){
    val TAG = MenuActivity::class.java.simpleName



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_activity)
        val  toolbar_menu : Toolbar = findViewById(R.id.toolbar_menu)
        setSupportActionBar(toolbar_menu)

        setUpRecyclerView(query)

       //GSO
        gso= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)




        //logging out here from toolbar
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_menu,menu)
        val searchView = MenuItemCompat.getActionView(menu!!.findItem(R.id.search_menu)) as SearchView
        val searchManager : SearchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String): Boolean {
                if (!text.trim { it <= ' ' }.isEmpty()) {
                search(text)
                    filteredData.filterData.clear()
                    setupSearchRecyclerView()
                }else{
                }
                       setupSearchRecyclerView()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {  filteredData.filterData.clear()

                if (newText.trim { it <= ' ' }.isEmpty()) {

                    setUpRecyclerView(query)
                    adapter!!.startListening()
                    filteredData.filterData.clear()

                }else{

                    search(newText)
                    setupSearchRecyclerView()


                }

                return false
            }
        })
        searchView.setOnSearchClickListener {
            Toast.makeText(this@MenuActivity,"FUCK_ME2",Toast.LENGTH_SHORT).show()

        }
        searchManager.setOnCancelListener {
            Toast.makeText(this@MenuActivity,"FUCK_ME_3",Toast.LENGTH_SHORT).show()

        }
        searchView.setOnCloseListener {
            Toast.makeText(this@MenuActivity,"FUCK",Toast.LENGTH_SHORT).show()
            return@setOnCloseListener false
        }


        return true
    }

    private fun search(s: String) {
        var name : String?=null
    menuref.get().addOnSuccessListener {
        for(collection in it){
            var singleDataItem=ItemData()

            name=collection.data["name"].toString()
            s.toString().toLowerCase()
            name!!.toLowerCase()

            if(checkIfEqual(name!!.toLowerCase(),s)){
                singleDataItem.preptime=collection.data["preptime"].toString()
                singleDataItem.name=name
                singleDataItem.price=collection.getDouble("price")!!.toInt()
                singleDataItem.vegOrNot=collection.getBoolean("vegOrNot")
                singleDataItem.availableOrNot=collection.data["availability"].toString()
                singleDataItem.id=collection.id
                filteredData.filterData.add(singleDataItem)
                name=null
                setupSearchRecyclerView()
            }
        }
        Toast.makeText(this,"Showing the most relevant",Toast.LENGTH_LONG).show()
    }


    }

    private fun checkIfEqual(name: String, s: String):Boolean {

        for (i in s.indices){
            if(!name[i].equals(s[i])){return false}
        }
        return true
    }


    private fun setupSearchRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val layoutManager=LinearLayoutManager(this)
        layoutManager.orientation=LinearLayoutManager.VERTICAL
        recyclerView.layoutManager=layoutManager


        searchAdapter = SearchAdapter(this, filteredData.filterData)
        recyclerView.adapter= searchAdapter

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId){
            R.id.sign_out ->logout()
            R.id.notification -> GotoNotification()
            R.id.current_orders -> GotoCurrOrders()
            R.id.menu_cart->updateUI()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateUI() {
        val intent = Intent(this,Cart::class.java)
        startActivity(intent)
    }

    private fun logout(){
        val intent = Intent(this,UserTypeSelectionActivity::class.java)
        mGoogleSignInClient.signOut().addOnCompleteListener {
            startActivity(intent)
            Toast.makeText(this,"YOU just logged out",Toast.LENGTH_SHORT).show()
            finish()
        }

    }

    private fun GotoNotification(){
     Toast.makeText(this,"Need to create the activity for notification",Toast.LENGTH_SHORT).show()
    }

    private fun GotoCurrOrders(){
        Toast.makeText(this,"Need to create the activity for notification",Toast.LENGTH_SHORT).show()

    }

    private fun setUpRecyclerView(query: Query){


        val options :  FirestoreRecyclerOptions<ItemData> = FirestoreRecyclerOptions.Builder<ItemData>()
                .setQuery(query,ItemData::class.java)
                .build()

        adapter = MenuAdapter(options, this)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.adapter= adapter

    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()


    }

    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }

}