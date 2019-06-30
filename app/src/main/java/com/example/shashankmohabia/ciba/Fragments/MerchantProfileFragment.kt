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
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.merchant_profile_fragment.*

class MerchantProfileFragment:Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.merchant_profile_fragment,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Glide.with(this).load(currMerchant.profileUrl).dontAnimate().apply(RequestOptions.circleCropTransform()).into(merchant_profile_img)
        merchant_profile_name.text= currMerchant.name.toString()
        merchant_profile_number.text= currMerchant.paytmNumber.toString()
    }
}