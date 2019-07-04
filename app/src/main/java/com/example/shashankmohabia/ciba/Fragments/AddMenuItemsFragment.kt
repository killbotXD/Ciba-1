package com.example.shashankmohabia.ciba.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.shashankmohabia.ciba.Core.db
import com.example.shashankmohabia.ciba.R
import com.example.shashankmohabia.ciba.Utils.Constants.currMerchant
import com.example.shashankmohabia.ciba.Utils.Extensions.ItemData
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.add_menu_item_merchant.*
import kotlinx.android.synthetic.main.toolbar_merchant.*

class AddMenuItemsFragment():Fragment(){
    var vegOrNot=true
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_menu_item_merchant,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity!!.toolbar_menu_merchant.title = "ADD ITEM"
        radio_grp.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.veg2->vegOrNot=true
                R.id.non_veg2->vegOrNot=false
            }
        }
        add_btn2.setOnClickListener {
            when {
                item_name2.text.isNullOrEmpty() -> item_name2.error = "This Field Cannot Be Empty"
                item_preptime2.text.isNullOrEmpty() -> item_preptime2.error = "This Field Cannot Be Empty"
                item_price2.text.isNullOrEmpty() -> item_price2.error = "This Field Cannot Be Empty"
                else -> addItem()
            }

        }
    }

    private fun addItem() {
        val Item=HashMap<String,Any>()
        Item["name"]=item_name2.text.toString()
        Item["price"]=item_price2.text.toString().toInt()
        Item["preptime"]=item_preptime2.text.toString()
        Item["vegOrNot"]=vegOrNot
        Item["availability"]="available"

        db.collection("MerchantList").document(currMerchant.id.toString()).collection("Menu")
                .add(Item).addOnSuccessListener {
                    Toasty.success(this.context!!,"SUCCESS", Toast.LENGTH_SHORT).show()
                }.addOnCompleteListener {

                    activity!!.supportFragmentManager.beginTransaction().replace(R.id.merchant_fragment,MerchantMenuFragment()).commit()
                }



    }

}