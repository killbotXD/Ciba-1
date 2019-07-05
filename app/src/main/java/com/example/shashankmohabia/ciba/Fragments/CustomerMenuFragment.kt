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
import com.example.shashankmohabia.ciba.Utils.Extensions.MenuAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.customer_menu_fragment.*
import kotlinx.android.synthetic.main.toolbaar_menu.*

class CustomerMenuFragment: Fragment(){
    var adapter: MenuAdapter? = null
    val query= db.collection("MerchantList").document(currMerchant.id.toString()).collection("Menu").orderBy("name")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.customer_menu_fragment,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
            setUpRecyclerView(query)
        activity!!.toolbar_menu.title = "Menu"
    }
    private fun setUpRecyclerView(query: Query) {


        val options: FirestoreRecyclerOptions<ItemData> = FirestoreRecyclerOptions.Builder<ItemData>()
                .setQuery(query, ItemData::class.java)
                .build()

        adapter = MenuAdapter(options, this.context!!)


        recycler_view.layoutManager = LinearLayoutManager(this.context)
        recycler_view.adapter = adapter

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