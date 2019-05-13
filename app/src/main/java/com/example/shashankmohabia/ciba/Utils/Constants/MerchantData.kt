package com.example.shashankmohabia.ciba.Utils.Constants

class MerchantData{

    var name :String? =null
    var paytmNumber : String? =  null
    var profileUrl : String ? = null
    var email : String? =  null
    //var id :String? = null

    constructor(){

        //empty construction needed
    }

    constructor(name: String?, paytmNumber: String?, profileUrl: String?, email: String?) {
        this.name = name
        this.paytmNumber = paytmNumber
        this.profileUrl = profileUrl
        this.email = email
        //this.id = id
    }


}

var currMerchant=MerchantData()