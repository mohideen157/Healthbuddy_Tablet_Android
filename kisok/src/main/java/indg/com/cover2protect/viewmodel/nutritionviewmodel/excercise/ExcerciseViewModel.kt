package indg.com.cover2protect.viewmodel.nutritionviewmodel.excercise

import androidx.lifecycle.MutableLiveData
import indg.com.cover2protect.helper.NutritionRetrofit
import indg.com.cover2protect.navigator.ExcerciseNavigator
import indg.com.cover2protect.model.caloriesgraphresponse.getcalories.GetCalories
import indg.com.cover2protect.model.excerciseapi.ExcercisePostResponse
import indg.com.cover2protect.model.excercisemodel.ExcerciseModelResponse
import indg.com.cover2protect.model.responseformat.ResponseFormat
import indg.com.cover2protect.util.HeaderData
import indg.com.cover2protect.viewmodel.BaseViewModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.json.JSONObject



class ExcerciseViewModel(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData?) :
        BaseViewModel<ExcerciseNavigator>(dataManager, apiService, headerData) {


    private var caltarget = MutableLiveData<String>()

    fun SaveCalories(cal: String, date: String) {
        val map = java.util.HashMap<String, String>()
        map["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        val data = java.util.HashMap<String, String>()
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
                            if (t.success) {
                                getNavigator()!!.OnSuccess(t)
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

    fun GetCalories(date:String): MutableLiveData<String> {
        val map = java.util.HashMap<String, String>()
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
                            if (t.success) {
                                if(!t.data.isNullOrEmpty()){
                                    getNavigator()!!.OnSuccess(t.data)
                                }else{
                                    getNavigator()!!.OnSuccess("1900")
                                }
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


    fun GetNutritionExcerciseList(name: String) {
        val map = HashMap<String, String>()
        map["x-app-id"] = "2131d777"
        map["x-app-key"] = "93e5443127f263c2dac855ede6d47920"
        val service = NutritionRetrofit.getRetrofitInstance()!!.create(indg.com.cover2protect.presenter.NutrionApi::class.java)
        val call = service!!.GetNutrionExcerciseResponse(map, name)
        call.enqueue(object : Callback<ExcerciseModelResponse> {
            override fun onResponse(call: Call<ExcerciseModelResponse>, response: Response<ExcerciseModelResponse>) {
                if (response.isSuccessful) {
                    if(response.body()!!.exercises.isNotEmpty()){
                        var name = response.body()!!.exercises[0].name+" "+response!!.body()!!.exercises[0].duration_min+" min"
                        getNavigator()!!.OnSuccess(response.body()!!)
                    }else{
                        getNavigator()!!.OnError("No Data Found")
                    }
                }
            }

            override fun onFailure(call: Call<ExcerciseModelResponse>, t: Throwable) {
                getNavigator()!!.OnError("Something Went Wrong")
            }
        })


    }

    fun PostExcercise(date: String, cal: String, excercise: String) {
        var header = java.util.HashMap<String, String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        val jsonParam = JSONObject()
        jsonParam.put("calories", cal)
        jsonParam.put("date", date)
        jsonParam.put("desc", excercise)

        val call = getApiService()!!.PostExcercise(header,"excercise",jsonParam.toString())
        call.enqueue(object : Callback<ExcercisePostResponse> {
            override fun onResponse(call: Call<ExcercisePostResponse>, response: Response<ExcercisePostResponse>) {
                if (response.isSuccessful) {
                   if(response.body()!!.success){
                       getNavigator()!!.OnSuccess(response.body()!!)
                   }
                }
            }

            override fun onFailure(call: Call<ExcercisePostResponse>, t: Throwable) {
                getNavigator()!!.OnError("Something Went Wrong")
            }
        })


    }

}