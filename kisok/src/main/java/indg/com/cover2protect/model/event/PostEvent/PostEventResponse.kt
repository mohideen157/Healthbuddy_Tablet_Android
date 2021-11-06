package indg.com.cover2protect.model.event.PostEvent

data class PostEventResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
)