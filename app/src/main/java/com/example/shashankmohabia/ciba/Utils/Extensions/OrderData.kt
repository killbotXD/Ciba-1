package com.example.shashankmohabia.ciba.Utils.Extensions

import java.sql.Timestamp
import java.util.*

class OrderData{
    var isAprooved:Boolean?=null
    var isCancled:Boolean?=null
    var isDelivered:Boolean?=null
    var isPaymentRecieved:Boolean?=null
    var orderId :String? = null
    var placedBy :String? = null
    var placedTo : String? = null
    var placedByNumber : String ? =null
    var time : Date? = null

    constructor(){

        //empty constructor needed
    }

    constructor(isAprooved: Boolean?, isCancled: Boolean?, isDelivered: Boolean?, isPaymentRecieved: Boolean?, orderId: String?, placedBy: String?, placedTo: String?, placedByNumber: String?, time: Date?) {
        this.isAprooved = isAprooved
        this.isCancled = isCancled
        this.isDelivered = isDelivered
        this.isPaymentRecieved = isPaymentRecieved
        this.orderId = orderId
        this.placedBy = placedBy
        this.placedTo = placedTo
        this.placedByNumber = placedByNumber
        this.time = time
    }


}