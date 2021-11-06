package indg.com.cover2protect.model.nutrition.nutritionhistory

data class GetNutritionHistory(
        val date: List<Date>,
        val message: String,
        val success: Boolean
)