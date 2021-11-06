package indg.com.cover2protect.model.event.GetEvent

data class GetRecordResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
)