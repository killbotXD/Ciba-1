package com.example.shashankmohabia.ciba.Utils.Extensions

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.example.shashankmohabia.ciba.Core.MenuExpanded
import com.example.shashankmohabia.ciba.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions


class MenuAdapter(options: FirestoreRecyclerOptions<ItemData>, private val mContext: Context) : FirestoreRecyclerAdapter<ItemData, MenuAdapter.ItemHolder>(options) {

    override fun onBindViewHolder(holder: ItemHolder, position: Int, model: ItemData) {
        Log.d("on_bind_view_holder","working")
        holder.textViewName.text = model.name
        holder.textViewPrice.text = model.price.toString()
        if(model.name.isNullOrEmpty()){
            Log.d("ADAPTERNULL","true")
        }else{
            Log.d("ADAPTERNULL","False")
        }
        holder.layout.setOnClickListener {
            val intent=Intent(mContext, MenuExpanded::class.java)
            intent.putExtra("availableOrNot",model.availableOrNot)
            intent.putExtra("vegOrNot",model.vegOrNot)
            intent.putExtra("itemName",model.name)
            intent.putExtra("itemPrice",model.price)
            intent.putExtra("prepTime",model.preptime)
            intent.putExtra("id",model.id)
            mContext.startActivity(intent)



        }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
       val v : View = LayoutInflater.from(parent.context).inflate(R.layout.menu_item,parent,false)
        Log.d("on_create_view_holder","working")
        return ItemHolder(v)
    }


    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val textViewName =itemView.findViewById<TextView>(R.id.text_view_item_name)
        val textViewPrice=itemView.findViewById<TextView>(R.id.text_view_item_price)
        val layout=itemView.findViewById<RelativeLayout>(R.id.relative_layout_item)
            init {
                itemView.setOnClickListener {


                    Toast.makeText(itemView.context,itemView.id.toString(), Toast.LENGTH_LONG).show()
                }
            }


    }






}


