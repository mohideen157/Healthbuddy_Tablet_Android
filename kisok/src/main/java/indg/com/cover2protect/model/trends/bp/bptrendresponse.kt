package indg.com.cover2protect.model.trends.bp

data class bptrendresponse(
        val `data`: List<Data>,
        val end_date: String,
        val message: String,
        val start_date: String,
        val success: Boolean
)