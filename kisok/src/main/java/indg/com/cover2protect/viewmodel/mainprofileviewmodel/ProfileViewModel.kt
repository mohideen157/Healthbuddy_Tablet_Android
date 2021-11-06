package indg.com.cover2protect.viewmodel.mainprofileviewmodel

import androidx.lifecycle.MutableLiveData
import indg.com.cover2protect.presenter.ApiService
import indg.com.cover2protect.presenter.DataManager
import indg.com.cover2protect.model.profile.Profile
import indg.com.cover2protect.util.HeaderData
import indg.com.cover2protect.viewmodel.BaseViewModel
import java.util.HashMap
import io.reactivex.Observer
import indg.com.cover2protect.presenter.Profile_Data_Response
import indg.com.cover2protect.model.articles.ArticleResponse
import indg.com.cover2protect.model.articles.Data
import indg.com.cover2protect.model.hhi.hhiresponse
import indg.com.cover2protect.model.imageresponse.ImageUrlResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileViewModel : BaseViewModel<Profile_Data_Response> {

    constructor(dataManager: DataManager, apiService: ApiService, headerData: HeaderData) : super(dataManager, apiService, headerData)

    private var profiledata = MutableLiveData<Profile>()
    private var hhidata = MutableLiveData<hhiresponse>()
    private var profileImage = MutableLiveData<String>()
    private var articledata = MutableLiveData<ArrayList<Data>>()


    fun GetProfileData(): MutableLiveData<Profile> {
        var header = HashMap<String, String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        getApiService()!!.getProfile(header).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Profile> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: Profile) {
                        if (t.success) {
                            profiledata.value = t
                        } else {
                            getNavigator()!!.onError(t.message.toString())
                        }
                    }
                    override fun onError(e: Throwable) {
                        try {
                            getNavigator()!!.onError("Something went wrong")
                        } catch (ex: Exception) {
                        }
                    }

                })
        return profiledata


    }


    fun GetProfileImage(): MutableLiveData<String> {
        var header = HashMap<String, String>()
        header["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        val call = getApiService()!!.GETImage(header)
        call.enqueue(object : Callback<ImageUrlResponse> {
            override fun onResponse(call: Call<ImageUrlResponse>, response: Response<ImageUrlResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.success == true) {
                        profileImage.value = response.body()!!.image_url
                    } else {
                        getNavigator()!!.onError("No Image Found")
                    }
                } else {
                    getNavigator()!!.onError("Something Went Wrong")
                }
            }

            override fun onFailure(call: Call<ImageUrlResponse>, t: Throwable) {
                getNavigator()!!.onError("Something Went Wrong")
            }
        })

        return profileImage

    }


    fun GetArticles(): MutableLiveData<ArrayList<Data>> {
        var list_article = ArrayList<Data>()
        var header = HashMap<String, String>()
        header["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        val call = getApiService()!!.GetArticleResponse(header)
        call.enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(call: Call<ArticleResponse>, response: Response<ArticleResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        if (response!!.body()!!.data.isNotEmpty()) {
                            if (response!!.body()!!.data.size <= 5) {
                                list_article.addAll(response!!.body()!!.data)
                                articledata.value = list_article
                            } else {
                                for (i in response!!.body()!!.data.indices) {
                                    if (i <= 5) {
                                        list_article.add(response!!.body()!!.data[i])
                                    }
                                }
                                articledata.value = list_article

                            }

                        } else {
                            getNavigator()!!.onError("No Articles Found")
                        }
                    } else {
                        getNavigator()!!.onError("Something Went Wrong")
                    }

                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                getNavigator()!!.onError("Something Went Wrong")
            }
        })

        return articledata
    }


    fun GetHHI(): MutableLiveData<hhiresponse> {
        var list_article = ArrayList<Data>()
        var header = HashMap<String, String>()
        header["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        val call = getApiService()!!.GetHHI(header)
        call.enqueue(object : Callback<hhiresponse> {
            override fun onResponse(call: Call<hhiresponse>, response: Response<hhiresponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        hhidata.value = response.body()
                    } else {
                        getNavigator()!!.onError("Something Went Wrong")
                    }
                }
            }

            override fun onFailure(call: Call<hhiresponse>, t: Throwable) {
                getNavigator()!!.onError("Something Went Wrong")
            }
        })

        return hhidata
    }


}