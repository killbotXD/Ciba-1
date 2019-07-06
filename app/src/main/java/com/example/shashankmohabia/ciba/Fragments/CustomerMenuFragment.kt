package com.example.shashankmohabia.ciba.Fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.shashankmohabia.ciba.Core.db
import com.example.shashankmohabia.ciba.Core.menuref
import com.example.shashankmohabia.ciba.R
import com.example.shashankmohabia.ciba.Utils.Constants.currMerchant
import com.example.shashankmohabia.ciba.Utils.Extensions.ItemData
import com.example.shashankmohabia.ciba.Utils.Extensions.MenuAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.customer_menu_fragment.*
import kotlinx.android.synthetic.main.toolbaar_menu.*
//FirebaseFirestore.getInstance().collection("MerchantList").document(currMerchant.id.toString()).collection("Menu").orderBy("name")
class CustomerMenuFragment: Fragment(){
    var adapterCustomerMenu: MenuAdapter? = null
    val queryCustomerMenu= db.collection("MerchantList").document(currMerchant.id.toString()).collection("Menu").orderBy("name")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.customer_menu_fragment,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
            setUpRecyclerView(queryCustomerMenu)

        activity!!.toolbar_menu.title = "Menu"
    }
    private fun setUpRecyclerView(query: Query) {


        val options: FirestoreRecyclerOptions<ItemData> = FirestoreRecyclerOptions.Builder<ItemData>()
                .setQuery(query, ItemData::class.java)
                .build()
        val recyclerView=view!!.findViewById<RecyclerView>(R.id.customer_recycler_view_menu)
        adapterCustomerMenu = MenuAdapter(options, this.context!!)
        recyclerView!!.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter=adapterCustomerMenu

    }


    override fun onStart() {
        super.onStart()
        adapterCustomerMenu!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapterCustomerMenu!!.stopListening()
    }
}