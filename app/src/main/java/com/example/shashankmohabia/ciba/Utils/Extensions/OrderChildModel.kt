package com.example.shashankmohabia.ciba.Utils.Extensions

class OrderChildModel {
    constructor(amt: String?, childId: String?, qty: String?) {
        this.amt = amt
        this.childId = childId
        this.qty = qty
    }
    constructor(){
        //needed empty constructor
    }

    var amt: String? = null
    var childId: String? = null
    var qty: String? = null
}