package indg.com.cover2protect.model.articles

data class ArticleResponse(
        val `data`: List<Data>,
        val links: Links,
        val meta: Meta,
        val success: Boolean
)