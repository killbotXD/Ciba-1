package com.example.shashankmohabia.ciba.Utils.Constants

class MerchantData{
    var email : String? =  null
    var name :String? =null
    var paytmNumber : String? =  null
    var profileUrl : String ? = null
    var id :String? = null

    constructor(){

        //empty construction needed
    }

    constructor(email: String?, name: String?, paytmNumber: String?, profileUrl: String?, id: String?) {
        this.email = email
        this.name = name
        this.paytmNumber = paytmNumber
        this.profileUrl = profileUrl
        this.id = id
    }


}

var currMerchant=MerchantData()