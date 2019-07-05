package com.example.shashankmohabia.ciba.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.shashankmohabia.ciba.R
import com.example.shashankmohabia.ciba.Utils.Constants.currMerchant
import com.example.shashankmohabia.ciba.Utils.Constants.currUser
import kotlinx.android.synthetic.main.customer_profile_fragment.*

class CustomerProfileFragment:Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.customer_profile_fragment,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        customer_profile_name.text= currUser.name
        customer_profile_email.text= currUser.email
        customer_profile_number.text= currUser.number
        customer_profile_roll_number.text= currUser.roll
        customer_profile_address.text= currUser.add
        Glide.with(this).load(currUser.profileUrl).apply(RequestOptions.circleCropTransform()).into(customer_profile_img)
        cur_merchant.text= currMerchant.name
        btn_change_merchant.setOnClickListener {
            Toast.makeText(this.context,"change merchant",Toast.LENGTH_SHORT).show()
        }
        customer_edit_profile_button.setOnClickListener {
            Toast.makeText(this.context,"Edit button clicked",Toast.LENGTH_SHORT).show()

        }
    }
}