package indg.com.cover2protect.model.excerciseapi.excercise_get

data class ExcerciseGetResponse(
        val `data`: List<Data>,
        val message: String,
        val success: Boolean
)