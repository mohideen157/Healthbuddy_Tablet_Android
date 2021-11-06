package indg.com.cover2protect.model.articles

import java.io.Serializable

class Data : Serializable {
    var content: String? = null
    var image: String? = null
    var title: String? = null
    var user: User? = null
}