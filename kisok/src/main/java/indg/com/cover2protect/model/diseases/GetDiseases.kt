package indg.com.cover2protect.model.diseases

data class GetDiseases(
        val `data`: List<DiseaseData>,
        val success: Boolean,
        val message:String
)