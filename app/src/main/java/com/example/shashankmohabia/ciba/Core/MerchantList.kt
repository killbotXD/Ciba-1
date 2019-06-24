package com.example.shashankmohabia.ciba.Core

import android.os.Bundle

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.example.shashankmohabia.ciba.R
import com.example.shashankmohabia.ciba.Utils.Constants.MerchantData
import com.example.shashankmohabia.ciba.Utils.Extensions.MerchantListAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query

var adapterMerchList:MerchantListAdapter?= null
val queryMerchList=db.collection("MerchantList").orderBy("name",Query.Direction.ASCENDING)

class MerchantList:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.merchant_list)

        setUpRecyclerView(queryMerchList)
    }
    private fun setUpRecyclerView(query: Query) {


        val options: FirestoreRecyclerOptions<MerchantData> = FirestoreRecyclerOptions.Builder<MerchantData>()
                .setQuery(query, MerchantData::class.java)
                .build()

        adapterMerchList = MerchantListAdapter(options, this)

        val recyclerView = findViewById<RecyclerView>(R.id.merchant_list_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapterMerchList

    }

    override fun onStart() {
        super.onStart()
        adapterMerchList!!.startListening()
    }
}