package indg.com.cover2protect.model.resetpassword

data class ResetPassword(
        val email: String,
        val message: String,
        val success: Boolean
)