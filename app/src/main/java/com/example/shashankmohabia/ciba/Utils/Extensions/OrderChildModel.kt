package com.example.shashankmohabia.ciba.Utils.Extensions

class OrderChildModel {

    constructor(){
        //needed empty constructor
    }

    constructor(amt: Int?, childId: String?, qty: Int?) {
        this.amt = amt
        this.childId = childId
        this.qty = qty
    }

    var amt: Int? = null
    var childId: String? = null
    var qty: Int? = null
}