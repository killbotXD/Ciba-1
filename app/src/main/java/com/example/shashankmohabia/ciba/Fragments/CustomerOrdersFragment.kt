package com.example.shashankmohabia.ciba.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shashankmohabia.ciba.Core.dbmerch
import com.example.shashankmohabia.ciba.R
import com.example.shashankmohabia.ciba.Utils.Constants.currUser
import com.example.shashankmohabia.ciba.Utils.Extensions.MerchantOrderAdapter
import com.example.shashankmohabia.ciba.Utils.Extensions.OrderData
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.customer_orders_fragment.*
import kotlinx.android.synthetic.main.toolbaar_menu.*


class CustomerOrdersFragment: Fragment(){
    var adapterMerchantOrder: MerchantOrderAdapter? = null
    var queryMerchantOrders = dbmerch.collection("Orders1").whereEqualTo("placedBy", currUser.name.toString()).orderBy("time")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.customer_orders_fragment,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerView(queryMerchantOrders)
        activity!!.toolbar_menu.title = "Orders"
    }
    private fun setUpRecyclerView(query: Query) {
        val options: FirestoreRecyclerOptions<OrderData> = FirestoreRecyclerOptions.Builder<OrderData>()
                .setQuery(query, OrderData::class.java)
                .build()

        adapterMerchantOrder = MerchantOrderAdapter(options, this.context)
        // maybe a bug like can i use the sme recycler view agian

        recycler_view_customer_orders.layoutManager = LinearLayoutManager(this.context)
        recycler_view_customer_orders.adapter = adapterMerchantOrder
    }

    override fun onStart() {
        super.onStart()
        adapterMerchantOrder!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapterMerchantOrder!!.stopListening()
    }
}