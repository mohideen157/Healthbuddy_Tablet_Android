package indg.com.cover2protect.navigator

import indg.com.cover2protect.model.hhi.hhigraph.Data

interface hhinavigator {

    fun onSuccess(data:List<Data>)
    fun onError(msg:String)
}