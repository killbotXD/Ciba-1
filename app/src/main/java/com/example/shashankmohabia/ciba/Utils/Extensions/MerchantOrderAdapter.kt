package com.example.shashankmohabia.ciba.Utils.Extensions

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.example.shashankmohabia.ciba.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class MerchantOrderAdapter(options: FirestoreRecyclerOptions<OrderData>, private val mContext: Context?) : FirestoreRecyclerAdapter<OrderData, MerchantOrderAdapter.ItemHolderMerchant>(options) {


    override fun onBindViewHolder(holder: ItemHolderMerchant, position: Int, model: OrderData) {

        holder.placedBy.text=model.placedBy
        holder.placedByNumber.text=model.placedByNumber
        holder.placedByTime.text=model.time.toString()


        //add onclick listener?
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolderMerchant {
        val v : View = LayoutInflater.from(parent.context).inflate(R.layout.merchant_item,parent,false)

        return ItemHolderMerchant(v)
    }


    class ItemHolderMerchant(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val placedBy=itemView.findViewById<TextView>(R.id.placed_by) as TextView
        val placedByNumber = itemView.findViewById<TextView>(R.id.placed_by_number)
        val placedByTime = itemView.findViewById<TextView>(R.id.placed_on)
        val cardLayout=itemView.findViewById<RelativeLayout>(R.id.relative_layout_item_merchant)



    }






}


