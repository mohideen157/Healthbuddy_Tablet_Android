package indg.com.cover2protect.model.hhi

data class hhiresponse(
        val message: String,
        val score: Int,
        val success: Boolean,
        val total: Int
)