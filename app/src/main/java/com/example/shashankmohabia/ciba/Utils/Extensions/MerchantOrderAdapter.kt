package com.example.shashankmohabia.ciba.Utils.Extensions

import android.content.Context
import android.opengl.Visibility
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.example.shashankmohabia.ciba.Core.db
import com.example.shashankmohabia.ciba.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_cart.view.*
import kotlinx.android.synthetic.main.merchant_item.view.*

class MerchantOrderAdapter(options: FirestoreRecyclerOptions<OrderData>, private val mContext: Context?) : FirestoreRecyclerAdapter<OrderData, MerchantOrderAdapter.ItemHolderMerchant>(options) {
var adapterMerchantOrderChild:OrderChildAdapter ? =null


    override fun onBindViewHolder(holder: ItemHolderMerchant, position: Int, model: OrderData) {
        var open:Boolean=false
        val query:Query=db.collection("Orders1/${model.orderId}$/items")
        holder.placedBy.text=model.placedBy
        holder.placedByNumber.text=model.placedByNumber
        holder.placedByTime.text=model.time.toString()
        holder.cardLayout.setOnClickListener {
            if(!open){
                open=!open
            holder.itemView.child_rv_card.visibility=View.VISIBLE
            holder.itemView.child_rv.visibility=View.VISIBLE
                setupChildRV(query,holder)
                adapterMerchantOrderChild!!.startListening()
            holder.itemView.total_qty_text.visibility=View.VISIBLE
            //holder.itemView.Order_qty.visibility=View.VISIBLE
           // holder.itemView.total_amt_text.visibility=View.VISIBLE
            holder.itemView.total_amt.visibility=View.VISIBLE}
            else if(open){
                open=!open
                //adapterMerchantOrderChild!!.stopListening()
                holder.itemView.child_rv_card.visibility=View.GONE
                holder.itemView.child_rv.visibility=View.GONE
                holder.itemView.total_qty_text.visibility=View.GONE
             //   holder.itemView.Order_qty.visibility=View.GONE
                holder.itemView.total_amt_text.visibility=View.GONE
               // holder.itemView.total_amt.visibility=View.GONE

            }
        }

    }

    private fun setupChildRV(query: Query,holder: ItemHolderMerchant) {
        val options: FirestoreRecyclerOptions<OrderChildModel> = FirestoreRecyclerOptions.Builder<OrderChildModel>()
                .setQuery(query, OrderChildModel::class.java)
                .build()
        adapterMerchantOrderChild=OrderChildAdapter(options)
        holder.itemView.child_rv.layoutManager=LinearLayoutManager(this.mContext)
        holder.itemView.child_rv.adapter=adapterMerchantOrderChild



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


