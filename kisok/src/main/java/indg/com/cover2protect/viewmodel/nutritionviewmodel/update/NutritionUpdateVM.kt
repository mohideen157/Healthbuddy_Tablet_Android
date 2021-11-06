package indg.com.cover2protect.viewmodel.nutritionviewmodel.update

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import indg.com.cover2protect.helper.NutritionRetrofit
import indg.com.cover2protect.model.nutrition.nutritiongetresponse.Food
import indg.com.cover2protect.model.nutrition.nutritiongetresponse.NutritionGetResponse
import indg.com.cover2protect.model.nutrition.savenutriondata.NutritionData
import indg.com.cover2protect.model.nutrition.updatenutritionresponse.PutNutritionResponse
import indg.com.cover2protect.navigator.SampleNavigator
import indg.com.cover2protect.util.HeaderData
import indg.com.cover2protect.viewmodel.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NutritionUpdateVM(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData?) :
        BaseViewModel<SampleNavigator>(dataManager, apiService, headerData) {

    private var nutritioninfo = MutableLiveData<ArrayList<Food>>()


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
                    arrayList.addAll(response.body()!!.foods)
                    getNavigator()!!.OnFood(arrayList)


                } else {
                    getNavigator()!!.OnError("Something Went Wrong")
                }
            }

            override fun onFailure(call: Call<NutritionGetResponse>, t: Throwable) {

                getNavigator()!!.OnError("Something Went Wrong")
            }
        })
    }


    fun PostNutritionData(nutritionInfo: NutritionData,id:String) {
        var header = java.util.HashMap<String, String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        val gson = Gson()
        val json = gson.toJson(nutritionInfo)
        val call = getApiService()!!.UpdateNutritionData(header, "nutrition", ""+nutritionInfo.type, json,id)
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