package indg.com.cover2protect.model.count

import androidx.databinding.BaseObservable

class Digits_Model(): BaseObservable() {


    private var name: String? = null

    constructor(numberOfData: String) : this() {
        this.name = numberOfData
    }

    fun getName():String?{
        return this!!.name
    }

    fun setName(name:String)
    {
        this.name = name
    }



}