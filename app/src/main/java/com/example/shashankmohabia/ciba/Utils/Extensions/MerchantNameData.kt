package com.example.shashankmohabia.ciba.Utils.Extensions

class MerchantNameData{
    var merchantName : String? = null

    constructor(){

        // secondary const needed for firebase

    }

    constructor(merchantName: String?) {
        this.merchantName = merchantName
    }
}

object merchantNames{
    val list= mutableListOf<MerchantNameData>()

}