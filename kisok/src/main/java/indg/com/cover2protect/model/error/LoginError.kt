package indg.com.cover2protect.model.error

data class LoginError(
        val error: List<String>,
        val message: String,
        val status: Boolean
)