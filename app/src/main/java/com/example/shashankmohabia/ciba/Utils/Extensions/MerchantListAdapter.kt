package com.example.shashankmohabia.ciba.Utils.Extensions

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.shashankmohabia.ciba.R
import com.example.shashankmohabia.ciba.Utils.Constants.MerchantData
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class MerchantListAdapter(options: FirestoreRecyclerOptions<MerchantData>, private val mContext: Context) : FirestoreRecyclerAdapter<MerchantData, MerchantListAdapter.ItemHolderList>(options) {
    override fun onBindViewHolder(p0:ItemHolderList, p1: Int, p2: MerchantData) {
        p0.merchantName.text = p2.name

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemHolderList {
        val v: View = LayoutInflater.from(p0.context).inflate(R.layout.merchant_list__item, p0, false)
        return ItemHolderList(v)
    }

    class ItemHolderList(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val merchantName = itemView.findViewById<TextView>(R.id.text_view_merchant_name_list)

    }

}



