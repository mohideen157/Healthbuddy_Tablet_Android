package indg.com.cover2protect.model.registrationmodel

data class RegistrationResponse(
        val success: Boolean,
        val message: String,
        val otp: String,
        val `data`: Data,
        val validation:List<String>

)