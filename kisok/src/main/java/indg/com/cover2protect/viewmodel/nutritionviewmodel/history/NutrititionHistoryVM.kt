package indg.com.cover2protect.viewmodel.nutritionviewmodel.history

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import indg.com.cover2protect.presenter.ApiService
import indg.com.cover2protect.presenter.DataManager
import indg.com.cover2protect.model.caloriesgraphresponse.getcalories.GetCalories
import indg.com.cover2protect.model.excerciseapi.excercise_get.Data
import indg.com.cover2protect.model.excerciseapi.excercise_get.ExcerciseGetResponse
import indg.com.cover2protect.model.excerciseapi.ExcercisePostResponse
import indg.com.cover2protect.model.nutrition.nutritionhistory.Date
import indg.com.cover2protect.model.nutrition.nutritionhistory.GetNutritionHistory
import indg.com.cover2protect.model.pedometer.PedometerRequest
import indg.com.cover2protect.model.pedometer.PedometerResponse
import indg.com.cover2protect.model.responseformat.ResponseFormat
import indg.com.cover2protect.navigator.NutrionHistoryNavigator
import indg.com.cover2protect.util.HeaderData
import indg.com.cover2protect.viewmodel.BaseViewModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap


class NutrititionHistoryVM :BaseViewModel<NutrionHistoryNavigator>{

    constructor(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData):super(dataManager,apiService,headerData)

    private var caltarget = MutableLiveData<String>()


    fun GetNutritionHistory_Breakfast(date:String,type:String) {
        var list = ArrayList<Date>()
        var header = HashMap<String,String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.GetNutritionHistory(header,date,type)
        call.enqueue(object : Callback<GetNutritionHistory> {
            override fun onResponse(call: Call<GetNutritionHistory>, response: Response<GetNutritionHistory>) {
                if(response.isSuccessful){
                  if(response.body()!!.success){
                      list.addAll(response.body()!!.date)
                      var sum = 0.0
                      if(list.isNotEmpty()){
                          for(i in list.indices){
                              if(!list[i].extra_info.calories.isNullOrEmpty()){
                                  var i = list[i].extra_info.calories.toDouble()
                                  sum += i
                              }
                          }
                      }
                      getNavigator()!!.OnSuccess(list, sum.toString())

                  }else{
                      getNavigator()!!.OnError("NO BREAKFAST DATA FOUND")
                  }
                }
            }

            override fun onFailure(call: Call<GetNutritionHistory>, t: Throwable) {
                getNavigator()!!.OnError("Something Went Wrong")

            }
        })

    }

    fun GetNutritionHistory_Lunch(date:String,type:String) {
        var list = ArrayList<Date>()
        var header = HashMap<String,String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.GetNutritionHistory(header,date,type)
        call.enqueue(object : Callback<GetNutritionHistory> {
            override fun onResponse(call: Call<GetNutritionHistory>, response: Response<GetNutritionHistory>) {
                if(response.isSuccessful){
                    if(response.body()!!.success){
                        list.addAll(response.body()!!.date)
                        var sum = 0.0
                        if(list.isNotEmpty()){
                            for(i in list.indices){
                                if(!list[i].extra_info.calories.isNullOrEmpty()){
                                    var i = list[i].extra_info.calories.toDouble()
                                    sum += i
                                }
                            }
                        }
                        getNavigator()!!.OnLunch(list,sum.toString())

                    }else{
                        getNavigator()!!.OnError("NO LUNCH DATA FOUND")
                    }
                }
            }

            override fun onFailure(call: Call<GetNutritionHistory>, t: Throwable) {
                getNavigator()!!.OnError("Something Went Wrong")

            }
        })

    }


    fun PostPedometerData(pedometerRequest: PedometerRequest) {
        var header = HashMap<String, String>()
        header["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        var pedometer = HashMap<String, String>()
        val gson = Gson()
        val json = gson.toJson(pedometerRequest)
        pedometer["parent_key"] = "pado-meter"
        pedometer["extra_info"] = json
        val call = getApiService()!!.PostPedometer(header, pedometer)
        call.enqueue(object : Callback<PedometerResponse> {
            override fun onResponse(call: Call<PedometerResponse>, response: Response<PedometerResponse>) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if(response.body()!!.success){
                            getNavigator()!!.OnPedometerSuccess()
                        }else{
                            getNavigator()!!.OnError("Unable To Upload Data")
                        }
                    }
                } else {
                    when (response.code()) {
                        403 -> getNavigator()!!.OnError("Something Went Wrong")
                        404 -> getNavigator()!!.OnError("Something Went Wrong")
                        500 -> getNavigator()!!.OnError("Server Not Responding")
                        else -> getNavigator()!!.OnError("Something Went Wrong")
                    }
                }
            }

            override fun onFailure(call: Call<PedometerResponse>, t: Throwable) {
                getNavigator()!!.OnError("Something Went Wrong")
            }
        })
    }



    fun GetNutritionHistory_Dinner(date:String,type:String) {
        var list = ArrayList<Date>()
        var header = HashMap<String,String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.GetNutritionHistory(header,date,type)
        call.enqueue(object : Callback<GetNutritionHistory> {
            override fun onResponse(call: Call<GetNutritionHistory>, response: Response<GetNutritionHistory>) {
                if(response.isSuccessful){
                    if(response.body()!!.success){
                        list.addAll(response.body()!!.date)
                        var sum = 0.0
                        if(list.isNotEmpty()){
                            for(i in list.indices){
                                if(!list[i].extra_info.calories.isNullOrEmpty()){
                                    var i = list[i].extra_info.calories.toDouble()
                                    sum += i
                                }
                            }
                        }
                        getNavigator()!!.OnDinner(list,sum.toString())
                    }else{
                        getNavigator()!!.OnError("NO DINNER DATA FOUND")
                    }
                }
            }

            override fun onFailure(call: Call<GetNutritionHistory>, t: Throwable) {
                getNavigator()!!.OnError("Something Went Wrong")

            }
        })

    }


    fun GetNutritionHistory_Snacks(date:String,type:String) {
        var list = ArrayList<Date>()
        var header = HashMap<String,String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.GetNutritionHistory(header,date,type)
        call.enqueue(object : Callback<GetNutritionHistory> {
            override fun onResponse(call: Call<GetNutritionHistory>, response: Response<GetNutritionHistory>) {
                if(response.isSuccessful){
                    if(response.body()!!.success){
                        list.addAll(response.body()!!.date)
                        var sum = 0.0
                        if(list.isNotEmpty()){
                            for(i in list.indices){
                                if(!list[i].extra_info.calories.isNullOrEmpty()){
                                    var i = list[i].extra_info.calories.toDouble()
                                    sum += i
                                }
                            }
                        }
                        getNavigator()!!.OnSnacks(list,sum.toString())
                    }else{
                        getNavigator()!!.OnError("NO SNACK DATA FOUND")
                    }
                }
            }

            override fun onFailure(call: Call<GetNutritionHistory>, t: Throwable) {
                getNavigator()!!.OnError("Something Went Wrong")

            }
        })

    }


    fun GETExcercise(date:String) {
        var list = ArrayList<Data>()
        var header = HashMap<String,String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.GetExcercise(header,date)
        call.enqueue(object : Callback<ExcerciseGetResponse> {
            override fun onResponse(call: Call<ExcerciseGetResponse>, response: Response<ExcerciseGetResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.success){
                        list.addAll(response.body()!!.data)
                        var sum = 0.0
                        if(list.isNotEmpty()){
                            for(i in list.indices){
                                if(!list[i].extra_info.calories.isNullOrEmpty()){
                                    var i = list[i].extra_info.calories.toDouble()
                                    sum += i
                                }
                            }
                        }
                        getNavigator()!!.OnExcercise(list,sum.toString())
                    }else{
                        getNavigator()!!.OnError("NO SNACK DATA FOUND")
                    }
                }
            }

            override fun onFailure(call: Call<ExcerciseGetResponse>, t: Throwable) {
                getNavigator()!!.OnError("Something Went Wrong")

            }
        })

    }

    fun SaveCalories(cal: String) {
        val map = HashMap<String, String>()
        map["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        val data = HashMap<String, String>()
        data["parent_key"] = "calories"
        data["child_key"] = "target"
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
                               getNavigator()!!.OnCalTarget(t.message)
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


    fun DeleteNutritionItembyId(id:String) {
        var header = HashMap<String,String>()
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.DeleteNutrition(id,header)
        call.enqueue(object : Callback<ResponseFormat> {
            override fun onResponse(call: Call<ResponseFormat>, response: Response<ResponseFormat>) {
                if(response.isSuccessful){
                  if(response.body()!!.success){

                      getNavigator()!!.OnSuccess(emptyList(),"Deleted")
                  }else{
                      getNavigator()!!.OnError(response.body()!!.message)
                  }
                }
            }

            override fun onFailure(call: Call<ResponseFormat>, t: Throwable) {
                getNavigator()!!.OnError("Something Went Wrong")

            }
        })

    }


    fun DeleteExcerciseItembyId(id:String) {
        var header = HashMap<String,String>()
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.DeleteExcercise(header,id)
        call.enqueue(object : Callback<ExcercisePostResponse> {
            override fun onResponse(call: Call<ExcercisePostResponse>, response: Response<ExcercisePostResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.success){
                        getNavigator()!!.OnSuccess(emptyList(),"Deleted")
                    }else{
                        getNavigator()!!.OnError(response.body()!!.message)
                    }
                }
            }

            override fun onFailure(call: Call<ExcercisePostResponse>, t: Throwable) {
                getNavigator()!!.OnError("Something Went Wrong")

            }
        })

    }


    fun GetCalories(date:String):MutableLiveData<String> {
        val map = HashMap<String, String>()
        map["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        getApiService()!!.GetCaloriesTarget(map,date).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GetCalories> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: GetCalories) {
                        if (t != null) {
                            if (t.success == true) {
                                if(!t.data.isNullOrEmpty()){
                                    caltarget.value = t.data
                                }else{
                                    caltarget.value = "1900"
                                }
                            } else {
                                getNavigator()!!.OnError("No Cal Data")
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        getNavigator()!!.OnError("Something Went Wrong")

                    }

                })

        return caltarget


    }

}