package com.example.shashankmohabia.ciba.Utils.Extensions

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.shashankmohabia.ciba.Core.db
import com.example.shashankmohabia.ciba.Core.replaceFragment
import com.example.shashankmohabia.ciba.Fragments.EditMenuItemFragment
import com.example.shashankmohabia.ciba.R
import com.example.shashankmohabia.ciba.Utils.Constants.currMerchant
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.merchant_menu_item.view.*




class MerchantMenuAdapter(options:FirestoreRecyclerOptions<ItemData>, private val mContext: Context):FirestoreRecyclerAdapter<ItemData,MerchantMenuAdapter.ItemHolder>(options){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MerchantMenuAdapter.ItemHolder {
        val v : View = LayoutInflater.from(p0.context).inflate(R.layout.merchant_menu_item,p0,false)

        return ItemHolder(v)
    }

    override fun onBindViewHolder(p0: MerchantMenuAdapter.ItemHolder, p1: Int, p2: ItemData) {
     p0.name.text=p2.name.toString()
        p0.price.text=p2.price.toString().trim()
        p0.preptime.text=p2.preptime.toString()
        p0.itemView.menu_item_edit_button.setOnClickListener {
            (mContext as AppCompatActivity).replaceFragment(EditMenuItemFragment(),R.id.merchant_fragment)
            val model= ViewModelProviders.of(mContext as AppCompatActivity).get(Communicator::class.java)
            model.setMsgCommunicator(p2.name.toString())
                        }
        p0.itemView.menu_item_remove_button.setOnClickListener {
            db.collection("MerchantList").document(currMerchant.id.toString()).collection("Menu").document(p2.id.toString()).delete().addOnSuccessListener {
              
                Toast.makeText(mContext,"DELETE SUCCECFUL",Toast.LENGTH_SHORT).show()
            }
        }

    }
    class ItemHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        val name =itemView.findViewById<TextView>(R.id.text_view_item_name2)
        val price =itemView.findViewById<TextView>(R.id.text_view_item_price2)
        val preptime=itemView.findViewById<TextView>(R.id.text_view_item_preptime)
    }

}