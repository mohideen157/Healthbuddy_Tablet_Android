package indg.com.cover2protect.navigator

import indg.com.cover2protect.model.heriditary.Data

interface HeriditaryNavigator {

    fun OnCompletion(message:String)
    fun onSuccess(data:List<Data>)
    fun onError(msg:String)
}