package indg.com.cover2protect.viewmodel.nutritionviewmodel

import androidx.lifecycle.MutableLiveData
import indg.com.cover2protect.helper.NutritionRetrofit
import indg.com.cover2protect.model.nutrition.Hit
import indg.com.cover2protect.model.nutrition.savenutriondata.NutritionData
import indg.com.cover2protect.model.nutrition.updatenutritionresponse.PutNutritionResponse
import indg.com.cover2protect.util.HeaderData
import indg.com.cover2protect.viewmodel.BaseViewModel

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList
import com.google.gson.Gson
import indg.com.cover2protect.model.caloriesgraphresponse.getcalories.GetCalories
import indg.com.cover2protect.model.nutrition.getnutritiondata.Common
import indg.com.cover2protect.model.nutrition.nutitionmodel.NutritionModelResponse
import indg.com.cover2protect.model.nutrition.nutritiongetresponse.Food
import indg.com.cover2protect.model.nutrition.nutritiongetresponse.NutritionGetResponse
import indg.com.cover2protect.model.responseformat.ResponseFormat
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap


class NutritionViewModel : BaseViewModel<indg.com.cover2protect.presenter.NutritionModelNavigator> {

    constructor(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData) : super(dataManager, apiService, headerData)

    private var resultdata = MutableLiveData<String>()
    private var caltarget = MutableLiveData<String>()
    private val datalist = ArrayList<Common>()
    private var nutritioninfo = MutableLiveData<ArrayList<Food>>()
    private var nutritionList = MutableLiveData<NutritionModelResponse>()
    private var hit: Hit? = null


    fun GetNutritionList(q: String) {
        val map = HashMap<String, String>()
        map["x-app-id"] = "2131d777"
        map["x-app-key"] = "93e5443127f263c2dac855ede6d47920"
        val service = NutritionRetrofit.getRetrofitInstance()!!.create(indg.com.cover2protect.presenter.NutrionApi::class.java)
        val call = service!!.GetNutrionInstant(map, q)
        call.subscribeOn(Schedulers.io())
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<NutritionModelResponse> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(search: NutritionModelResponse) {
                        if (!search.common.isNullOrEmpty()) {
                            getNavigator()!!.onData(search)

                        }
                    }

                    override fun onError(e: Throwable) {
                        getNavigator()!!.OnError("Something went wrong")
                    }

                    override fun onComplete() {

                    }
                })
    }


    fun GetNutritionInfo(id: String) {
        val arrayList = ArrayList<Food>()
        val map = HashMap<String, String>()
        map["x-app-id"] = "2131d777"
        map["x-app-key"] = "93e5443127f263c2dac855ede6d47920"
        val service = NutritionRetrofit.getRetrofitInstance()!!.create(indg.com.cover2protect.presenter.NutrionApi::class.java)
        val call = service!!.GetNutrionResponse(map, id)
        call.enqueue(object : Callback<NutritionGetResponse> {
            override fun onResponse(call: Call<NutritionGetResponse>, response: Response<NutritionGetResponse>) {
                if (response.isSuccessful) {
                    if (!response.body()!!.foods.isNullOrEmpty()) {
                        arrayList.addAll(response.body()!!.foods)
                        getNavigator()!!.OnFood(arrayList)
                    } else {
                        getNavigator()!!.OnError("Not Found")

                    }
                } else {
                    getNavigator()!!.OnError("Not Found")
                }
            }

            override fun onFailure(call: Call<NutritionGetResponse>, t: Throwable) {

                getNavigator()!!.OnError("Something Went Wrong")
            }
        })


    }

    fun PostNutritionData(nutritionInfo: NutritionData) {
        var header = java.util.HashMap<String, String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        val gson = Gson()
        val json = gson.toJson(nutritionInfo)
        val call = getApiService()!!.UpdateNutrition(header, "nutrition", "" + nutritionInfo.type, json)
        call.enqueue(object : Callback<PutNutritionResponse> {
            override fun onResponse(call: Call<PutNutritionResponse>, response: Response<PutNutritionResponse>) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response!!.body()!!.success == true) {
                            getNavigator()!!.OnSuccess("Data Saved Successfully")
                        } else {
                            getNavigator()!!.OnError("Something Went Wrong")
                        }
                    }
                } else {
                    when (response.code()) {
                        403 -> getNavigator()!!.OnError("Something Went Wrong")
                        404 -> getNavigator()!!.OnError("Something Went Wrong")
                        500 -> getNavigator()!!.OnError("Server Broken")
                        else -> getNavigator()!!.OnError("UnKnown Error")
                    }
                }
            }

            override fun onFailure(call: Call<PutNutritionResponse>, t: Throwable) {

                getNavigator()!!.OnError("Something Went Wrong")
            }
        })

    }

    fun SaveCalories(date: String, cal: String) {
        val map = HashMap<String, String>()
        map["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        val data = HashMap<String, String>()
        data["parent_key"] = "calories"
        data["child_key"] = "target"
        data["date"] = date
        data["target"] = cal
        getApiService()!!.SetCaloriesTarget(map, data).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseFormat> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: ResponseFormat) {
                        if (t != null) {
                            if (t.success == true) {
                                getNavigator()!!.OnSuccess(t.message)
                            } else {
                                getNavigator()!!.OnError("No Data")
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        getNavigator()!!.OnError("Something Went Wrong")

                    }

                })


    }

    fun GetCalories(date: String): MutableLiveData<String> {
        val map = HashMap<String, String>()
        map["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        getApiService()!!.GetCaloriesTarget(map, date).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GetCalories> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: GetCalories) {
                        if (t != null) {
                            if (t.success == true) {
                                caltarget.value = t.data
                            } else {
                                getNavigator()!!.OnError("No Data")
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        getNavigator()!!.OnError("Something Went Wrong")

                    }

                })

        return caltarget


    }


    fun GetNutritionType(id: String) {
        val arrayList = ArrayList<Food>()
        val map = HashMap<String, String>()
        map["x-app-id"] = "2131d777"
        map["x-app-key"] = "93e5443127f263c2dac855ede6d47920"
        val service = NutritionRetrofit.getRetrofitInstance()!!.create(indg.com.cover2protect.presenter.NutrionApi::class.java)
        val call = service!!.GetNutrionResponse(map, id)
        call.enqueue(object : Callback<NutritionGetResponse> {
            override fun onResponse(call: Call<NutritionGetResponse>, response: Response<NutritionGetResponse>) {
                if (response.isSuccessful) {
                    arrayList.addAll(response.body()!!.foods)
                    getNavigator()!!.OnType(arrayList)
                } else {
                    getNavigator()!!.OnError("Not Found")
                }
            }

            override fun onFailure(call: Call<NutritionGetResponse>, t: Throwable) {

                getNavigator()!!.OnError("Something Went Wrong")
            }
        })


    }

}