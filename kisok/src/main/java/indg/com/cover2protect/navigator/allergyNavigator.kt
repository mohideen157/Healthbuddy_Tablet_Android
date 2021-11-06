package indg.com.cover2protect.navigator

interface allergyNavigator {

    fun OnError(message:String)
    fun OnSuccess(message:String)
    fun OnData(data:String)
    fun onResult(`data`:Any)
}