package indg.com.cover2protect.model.excercisemodel

data class Exercise(
        val benefits: String,
        val compendium_code: String,
        val description: String,
        val duration_min: String,
        val met: String,
        val name: String,
        val nf_calories: String,
        val photo: Photo,
        val tag_id: String,
        val user_input: String
)