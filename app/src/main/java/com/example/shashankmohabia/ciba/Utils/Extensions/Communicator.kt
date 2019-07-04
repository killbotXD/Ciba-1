package com.example.shashankmohabia.ciba.Utils.Extensions

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class Communicator:ViewModel(){
    val message=MutableLiveData<Any>()
            fun setMsgCommunicator(msg:String){
                message.value=msg
            }
}