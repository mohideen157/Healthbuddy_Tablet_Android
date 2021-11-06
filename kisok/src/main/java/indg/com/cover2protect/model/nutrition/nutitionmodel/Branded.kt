package indg.com.cover2protect.model.nutrition.nutitionmodel

data class Branded(
        val brand_name: String,
        val brand_name_item_name: String,
        val brand_type: String,
        val food_name: String,
        val locale: String,
        val nf_calories: String,
        val nix_brand_id: String,
        val nix_item_id: String,
        val photo: PhotoX,
        val region: String,
        val serving_qty: String,
        val serving_unit: String
)