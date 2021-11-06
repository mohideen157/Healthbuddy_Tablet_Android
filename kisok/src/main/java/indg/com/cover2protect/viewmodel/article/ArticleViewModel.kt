package indg.com.cover2protect.viewmodel.article

import indg.com.cover2protect.model.articles.ArticleResponse
import indg.com.cover2protect.navigator.ArticleNavigator
import indg.com.cover2protect.util.HeaderData
import indg.com.cover2protect.viewmodel.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class ArticleViewModel(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData?) :
        BaseViewModel<ArticleNavigator>(dataManager, apiService, headerData) {


    fun GetArticles(pageid:String) {
        var header = HashMap<String, String>()
        header["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        val call = getApiService()!!.GetArticlePaginationResponse(header,pageid)
        call.enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(call: Call<ArticleResponse>, response: Response<ArticleResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        if (response!!.body()!!.data.isNotEmpty()) {
                             getNavigator()!!.OnSuccess(response.body()!!)
                        } else {
                            getNavigator()!!.OnError("No Articles Found")
                        }
                    } else {
                        getNavigator()!!.OnError("Something Went Wrong")
                    }

                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                getNavigator()!!.OnError("Something Went Wrong")
            }
        })

    }


}