package indg.com.cover2protect.navigator

interface RegisterNavigator {

    fun OnError(message:String)
    fun OnSuccess(message:String)
    fun OnOtpResponse(user:String,pass:String,email:String,otp:String,id:String,mobile:String)
}