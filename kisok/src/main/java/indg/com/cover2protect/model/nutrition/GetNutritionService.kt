package indg.com.cover2protect.model.nutrition

data class GetNutritionService(
        val hits: List<Hit>,
        val max_score: String,
        val total_hits: Int
)