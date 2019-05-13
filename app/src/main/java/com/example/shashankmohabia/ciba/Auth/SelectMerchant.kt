package com.example.shashankmohabia.ciba.Auth

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.shashankmohabia.ciba.R
import com.example.shashankmohabia.ciba.Utils.Constants.MerchantData
import com.example.shashankmohabia.ciba.Utils.Extensions.SelectMerchantAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.merchant_options_layout.*

class SelectMerchant:AppCompatActivity(){
    val refrence= FirebaseFirestore.getInstance()
    val query=refrence.collection("MerchantList").orderBy("name")
    var adapter : SelectMerchantAdapter ? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.merchant_options_layout)
        setSupportActionBar(toolbar_select_merchant)
        setupRecyclerview(query)


    }
    private fun setupRecyclerview(query:Query) {
        val options: FirestoreRecyclerOptions<MerchantData> = FirestoreRecyclerOptions.Builder<MerchantData>()
                .setQuery(query, MerchantData::class.java)
                .build()

        adapter = SelectMerchantAdapter(options, this)

        val recyclerView = findViewById<RecyclerView>(R.id.select_merchant_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }


}