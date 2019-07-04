package com.example.shashankmohabia.ciba.Fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shashankmohabia.ciba.Core.menuRef
import com.example.shashankmohabia.ciba.R
import com.example.shashankmohabia.ciba.Utils.Extensions.Communicator
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.add_menu_item_merchant.*

class EditMenuItemFragment: Fragment(){
    var query:Query?=null
    var id:String?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_menu_item_merchant,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val model= ViewModelProviders.of(activity!!).get(Communicator::class.java)

        model.message.observe(this, object : Observer<Any> {
            override fun onChanged(o: Any?) {
                query= menuRef.whereEqualTo("name",o.toString())

            }
        })
        query!!.get().addOnSuccessListener {
            for (doc in it){
                item_name2.setText(doc["name"].toString())
                item_preptime2.setText(doc["preptime"].toString())
                item_price2.setText(doc["price"].toString())
                id=doc.id
            } }

        add_btn2.setOnClickListener {
            menuRef.document(id!!).update("name",item_name2.text.toString())
            menuRef.document(id!!).update("price",item_price2.text.toString().toInt())
            menuRef.document(id!!).update("preptime",item_preptime2.text.toString())
        }
    }
}