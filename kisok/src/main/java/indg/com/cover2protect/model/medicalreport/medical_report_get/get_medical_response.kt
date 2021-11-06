package indg.com.cover2protect.model.medicalreport.medical_report_get

data class get_medical_response(
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
)