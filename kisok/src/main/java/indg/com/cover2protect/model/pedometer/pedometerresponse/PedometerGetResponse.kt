package indg.com.cover2protect.model.pedometer.pedometerresponse

data class PedometerGetResponse(
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
)