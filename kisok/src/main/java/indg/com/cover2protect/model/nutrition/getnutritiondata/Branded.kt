package indg.com.cover2protect.model.nutrition.getnutritiondata

data class Branded(
        val brand_name: String,
        val brand_name_item_name: String,
        val brand_type: Any,
        val food_name: String,
        val locale: String,
        val nf_calories: Any,
        val nix_brand_id: String,
        val nix_item_id: String,
        val photo: Photo,
        val region: Any,
        val serving_qty: Any,
        val serving_unit: String
)