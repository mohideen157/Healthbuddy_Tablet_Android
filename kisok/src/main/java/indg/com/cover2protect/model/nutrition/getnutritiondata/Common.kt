package indg.com.cover2protect.model.nutrition.getnutritiondata

data class Common(
        val common_type: Any,
        val food_name: String,
        val locale: String,
        val photo: Photo,
        val serving_qty: Any,
        val serving_unit: String,
        val tag_id: String,
        val tag_name: String
)