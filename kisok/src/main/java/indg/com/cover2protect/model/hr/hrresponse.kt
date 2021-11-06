package indg.com.cover2protect.model.hr

data class hrresponse(
        val `data`: List<Data>,
        val message: String,
        val success: Boolean
)