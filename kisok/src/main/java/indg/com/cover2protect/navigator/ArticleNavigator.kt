package indg.com.cover2protect.navigator

import indg.com.cover2protect.model.articles.ArticleResponse

interface ArticleNavigator {

    fun OnSuccess(data : ArticleResponse)

    fun OnError(error:String)


}