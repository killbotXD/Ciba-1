package com.example.shashankmohabia.ciba.Utils.Extensions

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.example.shashankmohabia.ciba.Core.MenuExpanded
import com.example.shashankmohabia.ciba.R
import com.example.shashankmohabia.ciba.Utils.Constants.MerchantData
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions


class SelectMerchantAdapter(options: FirestoreRecyclerOptions<MerchantData>, private val mContext: Context) : FirestoreRecyclerAdapter<MerchantData, SelectMerchantAdapter.ItemHolder>(options) {
    override fun onBindViewHolder(holder: ItemHolder, position: Int, model: MerchantData) {
        holder.textviewname.text=model.name.toString()

        holder.layout.setOnClickListener {


        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val v : View = LayoutInflater.from(parent.context).inflate(R.layout.merchant_item_option,parent,false)

        return ItemHolder(v)
    }


    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textviewname=itemView.findViewById<TextView>(R.id.text_view_merchant_name)
                val layout=itemView.findViewById<RelativeLayout>(R.id.relative_layout_item2)


    }






}

