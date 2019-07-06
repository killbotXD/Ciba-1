package com.example.shashankmohabia.ciba.Utils.Extensions

class ItemData{
    var availableOrNot : String?=null
    var id :String? = null
    var name : String? =null
    var preptime : String? =null
    var price :Int = 0
    var vegOrNot : Boolean? = null
    constructor(){

        //empty constructor needed
    }

    constructor(availableOrNot: String?, vegOrNot: Boolean?, name: String?, preptime: String?, price: Int,id:String?) {
        this.availableOrNot = availableOrNot
        this.vegOrNot = vegOrNot
        this.name = name
        this.preptime = preptime
        this.price = price
        this.id = id
    }


}

/*object searchData{
    val searchItems  = mutableListOf<ItemData>(
            ItemData("NA",false,"NA","NA",0,"NA")
    )
}*/

object filteredData{
    val filterData= mutableListOf<ItemData>(
            ItemData("NA",false,"na","na",0,"Na")
    )
}