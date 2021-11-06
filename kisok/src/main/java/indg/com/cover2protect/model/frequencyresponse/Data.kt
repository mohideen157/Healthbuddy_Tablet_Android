package indg.com.cover2protect.model.frequencyresponse

data class Data(
        val id: Int,
        val patient_id: Int,
        val parent_key: String,
        val child_key: String,
        val value: String,
        val unit: String,
        val extra_info: Any,
        val active: Int,
        val score: Any,
        val created_at: String,
        val updated_at: String
)