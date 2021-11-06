package indg.com.cover2protect.model.pedometer.pedometerresponse

data class Data(
    val created_at: String,
    val date: String,
    val id: Int,
    val patient_id: Int,
    val steps: String,
    val updated_at: String
)