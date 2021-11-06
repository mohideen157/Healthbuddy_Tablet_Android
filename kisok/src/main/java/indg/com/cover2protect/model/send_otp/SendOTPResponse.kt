package indg.com.cover2protect.model.send_otp

data class SendOTPResponse(
        val message: String,
        val otp: Int,
        val success: Boolean
)