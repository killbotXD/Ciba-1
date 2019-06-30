package com.example.shashankmohabia.ciba.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shashankmohabia.ciba.Core.db
import com.example.shashankmohabia.ciba.R
import com.example.shashankmohabia.ciba.Utils.Constants.currMerchant
import com.example.shashankmohabia.ciba.Utils.Extensions.ItemData
import com.example.shashankmohabia.ciba.Utils.Extensions.MerchantMenuAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.merchant_menu_fragment.*
import kotlinx.android.synthetic.main.toolbar_merchant.*

class MerchantMenuFragment:Fragment(){
    var adapterMerchantMenu:MerchantMenuAdapter?=null
    val queryMerchantMenu= db.collection("MerchantList").document(currMerchant.id.toString()).collection("Menu").orderBy("name")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.merchant_menu_fragment,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerView(queryMerchantMenu)
        activity!!.toolbar_menu_merchant.title = "Menu"
    }
    private fun setUpRecyclerView(query: Query) {
        val options: FirestoreRecyclerOptions<ItemData> = FirestoreRecyclerOptions.Builder<ItemData>()
                .setQuery(query, ItemData::class.java)
                .build()

        adapterMerchantMenu = MerchantMenuAdapter(options, this.context)
        // maybe a bug like can i use the sme recycler view agian
        merchant_menu_recyclerview.layoutManager = LinearLayoutManager(this.context)
        merchant_menu_recyclerview.adapter = adapterMerchantMenu
    }

    override fun onStart() {
        super.onStart()
        adapterMerchantMenu!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapterMerchantMenu!!.stopListening()
    }
}