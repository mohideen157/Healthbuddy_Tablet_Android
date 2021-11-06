package indg.com.cover2protect.model.medicationmodel.medicationinfo

data class Data(
        val active: Int,
        val child_key: String,
        val created_at: String,
        val dosage: String,
        val extra_info: String,
        val id: Int,
        val parent_key: String,
        val patient_id: Int,
        val per_day: String,
        val score: String,
        val type: String,
        val unit: String,
        val updated_at: String,
        val value: String
)