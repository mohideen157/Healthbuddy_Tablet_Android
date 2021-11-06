package indg.com.cover2protect.presenter

interface ResponseHandle {

    fun OnSuccess(message:String)

    fun OnError(msg:String)
}