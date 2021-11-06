package indg.com.cover2protect.model.deviceresponse

data class Data(
        val afib: String,
        val arrhythmia: String,
        val arterial_age: String,
        val bp: Bp,
        val bp_status: String,
        val event_id: String,
        val hr: String,
        val hrv_level: String,
        val pwv: String,
        val record_date: String
)