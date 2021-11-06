package indg.com.cover2protect.presenter

interface OTPResponse {

    fun OnSuccess(message:String)

    fun OnError(error:String)


    fun OnOTP(otp:String)
}