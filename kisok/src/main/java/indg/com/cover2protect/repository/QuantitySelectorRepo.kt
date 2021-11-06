package indg.com.cover2protect.repository

import androidx.lifecycle.MutableLiveData
import indg.com.cover2protect.model.count.Digits_Model
import java.util.*

class QuantitySelectorRepo {

    private var arrayListMutableLiveData = MutableLiveData<ArrayList<Digits_Model>>()
    private val arrayList = ArrayList<Digits_Model>()
    private var digits_Model: Digits_Model? = null
    private var i: Int? = null

    fun getArticlesList(): MutableLiveData<ArrayList<Digits_Model>> {

        for (i in 0..20) {
            arrayList.add(Digits_Model("" + i))
        }

        arrayListMutableLiveData.value = arrayList

        return arrayListMutableLiveData
    }

}