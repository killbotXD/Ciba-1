package com.example.shashankmohabia.ciba.Fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.shashankmohabia.ciba.Core.db
import com.example.shashankmohabia.ciba.R
import com.example.shashankmohabia.ciba.Utils.Constants.currMerchant
import com.example.shashankmohabia.ciba.Utils.Extensions.Communicator
import com.google.firebase.firestore.Query
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.edit_menu_item_merchant.*

class EditMenuItemFragment: Fragment(){
    var vegOrNot:Boolean=true
    var query:Query?=null
    var id:String?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.edit_menu_item_merchant,container,false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val model= ViewModelProviders.of(activity!!).get(Communicator::class.java)
        radio_grp2.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.veg3->vegOrNot=true
                R.id.non_veg3->vegOrNot=false
            }
        }
        model.message.observe(this, object : Observer<Any> {
            override fun onChanged(o: Any?) {
                query= db.collection("MerchantList").document(currMerchant.id.toString()).collection("Menu")
                        .whereEqualTo("name",o!!.toString())
                query!!.get().addOnSuccessListener {
                    for(docs in it){
                        item_name3.setText(docs.data["name"].toString())
                        item_price3.setText(docs.data["price"].toString())
                        item_preptime3.setText(docs.data["preptime"].toString())
                        if (docs.data["vegOrNot"]!!.equals(true)){
                            radio_grp2.check(R.id.veg3)
                        }else{radio_grp2.check(R.id.non_veg3)}
                        id=docs.id
                    }
                }
            }
        })



        add_btn3.setOnClickListener {
            when {
                item_name3.text.isNullOrEmpty() -> item_name3.error = "This Field Cannot Be Empty"
                item_preptime3.text.isNullOrEmpty() -> item_preptime3.error = "This Field Cannot Be Empty"
                item_price3.text.isNullOrEmpty() -> item_price3.error = "This Field Cannot Be Empty"
                else -> makeChanges()
            }
        }
    }

    private fun makeChanges() {
        db.collection("MerchantList").document(currMerchant.id.toString()).collection("Menu").document(id!!).update("name",item_name3.text.toString())
        db.collection("MerchantList").document(currMerchant.id.toString()).collection("Menu").document(id!!).update("price",item_price3.text.toString().toInt())
        db.collection("MerchantList").document(currMerchant.id.toString()).collection("Menu").document(id!!).update("preptime",item_preptime3.text.toString())
        db.collection("MerchantList").document(currMerchant.id.toString()).collection("Menu").document(id!!).update("vegOrNot",vegOrNot).addOnSuccessListener {
            Toasty.success(this.view!!.context,"Success",Toast.LENGTH_SHORT).show()
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.merchant_fragment,MerchantMenuFragment()).commit()}
    }


}
