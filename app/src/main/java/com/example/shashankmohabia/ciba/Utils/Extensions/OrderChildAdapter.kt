package com.example.shashankmohabia.ciba.Utils.Extensions

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shashankmohabia.ciba.Core.db
import com.example.shashankmohabia.ciba.R
import com.example.shashankmohabia.ciba.Utils.Constants.currMerchant
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.order_child_recycler_view.view.*


class OrderChildAdapter(options: FirestoreRecyclerOptions<OrderChildModel>):FirestoreRecyclerAdapter<OrderChildModel,OrderChildAdapter.holder>(options){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): OrderChildAdapter.holder {
      val v:View=LayoutInflater.from(p0.context).inflate(R.layout.order_child_recycler_view,p0,false)
        return holder(v)
    }

    override fun onBindViewHolder(holder: OrderChildAdapter.holder, position: Int, model: OrderChildModel) {
        holder.itemView.text_view_order_item_name.text=model.childId
        holder.itemView.text_view_order_item_amt.text=model.amt.toString()
        holder.itemView.text_view_order_item_qty.text=model.qty.toString()
        holder.itemView.text_view_order_item_rate.text=model.qty.toString()
        //val queryItem = db.collection("MerchantList/${currMerchant.id.toString()}$/Menu").whereEqualTo("id",model.childId)
        //        queryItem.get().addOnSuccessListener {
        //            for(doc in it){
        //                holder.itemView.text_view_order_item_name.text=doc["name"].toString()
        //                holder.itemView.text_view_order_item_amt.text=model.amt.toString()
        //                holder.itemView.text_view_order_item_qty.text=model.qty.toString()
        //                holder.itemView.text_view_order_item_rate.text=doc["price"].toString()
        //            }
        //        }//

    }

    class holder(itemView:View):RecyclerView.ViewHolder(itemView){

    }
}