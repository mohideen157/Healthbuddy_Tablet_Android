package indg.com.cover2protect.model

data class MedicalReportResponse(
    val BP: List<String>,
    val GLUCOSE_F: List<String>,
    val GLUCOSE_PP: List<String>,
    val HDL_CHOLESTEROL: List<String>,
    val Heart_rate: List<String>,
    val LDL_CHOLESTEROL: List<String>,
    val TRIGLYCERIDES: List<String>
)