package com.example.shashankmohabia.ciba.Utils.Extensions

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.shashankmohabia.ciba.R

class CartAdapter(val context: Context, val item : List<CartData>): RecyclerView.Adapter<CartAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
val view : View = LayoutInflater.from(context).inflate(R.layout.cart_item,p0,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
return item.size   }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        val cartData=item[p1]
        p0.setData(cartData,p1)
        p0.layout.setOnClickListener {
            Toast.makeText(context,item[p1].id,Toast.LENGTH_LONG).show()
        }

    }

    class MyViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView){
        val itemName = itemView.findViewById<TextView>(R.id.text_view_cart_item_name)
        val itemRate = itemView.findViewById<TextView>(R.id.text_view_cart_item_rate)
        val qty = itemView.findViewById<TextView>(R.id.text_view_order_item_qty)
        val amt = itemView.findViewById<TextView>(R.id.text_view_cart_item_amt)
        val layout = itemView.findViewById<LinearLayout>(R.id.relative_layout_cart_item)
        fun setData(cartData: CartData,pos:Int){
            itemName.text=cartData.name
            itemRate.text=cartData.rate.toString()
            qty.text=cartData.qty.toString()
            amt.text=cartData.amt.toString()
        }
    }
    fun deleteItem(position:Int){
        data.items.removeAt(position)
    }
}