package indg.com.cover2protect.viewmodel.pedometer

import androidx.lifecycle.MutableLiveData
import indg.com.cover2protect.model.pedometer.PedometerResponse
import indg.com.cover2protect.util.HeaderData
import indg.com.cover2protect.viewmodel.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap
import com.google.gson.Gson
import indg.com.cover2protect.model.arrhythmia.arrythmialist.arrythmialist_response
import indg.com.cover2protect.model.pedometer.PedometerRequest
import indg.com.cover2protect.model.pedometer.pedometerresponse.PedometerGetResponse
import indg.com.cover2protect.model.pedometer.pedometerresponse.PedometerResponsedata
import indg.com.cover2protect.navigator.response_navigator
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class PedometerViewModel : BaseViewModel<response_navigator> {

    constructor(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData) : super(dataManager, apiService, headerData)

    private var arrhythmia = MutableLiveData<String>()


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
                            getNavigator()!!.onSuccess(response!!.body()!!)
                        }else{
                            getNavigator()!!.onError("Unable To Upload Data")
                        }
                    }
                } else {
                    when (response.code()) {
                        403 -> getNavigator()!!.onError("Something Went Wrong")
                        404 -> getNavigator()!!.onError("Something Went Wrong")
                        500 -> getNavigator()!!.onError("Server Not Responding")
                        else -> getNavigator()!!.onError("Something Went Wrong")
                    }
                }
            }

            override fun onFailure(call: Call<PedometerResponse>, t: Throwable) {
                getNavigator()!!.onError("Something Went Wrong")
            }
        })
    }


    fun UpsertPedometer(date:String,steps:String) {
        var header = HashMap<String, String>()
        header["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        var map = HashMap<String,String>()
        map["steps"] = steps
        map["date"] =  date
        val call = getApiService()!!.UploadPedometerStep(header, map)
        call.enqueue(object : Callback<PedometerResponsedata> {
            override fun onResponse(call: Call<PedometerResponsedata>, response: Response<PedometerResponsedata>) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if(response.body()!!.success){
                            getNavigator()!!.onSuccess(response!!.body()!!)
                        }else{
                            getNavigator()!!.onError("Unable To Upload Data")
                        }
                    }
                } else {
                    when (response.code()) {
                        403 -> getNavigator()!!.onError("Something Went Wrong")
                        404 -> getNavigator()!!.onError("Something Went Wrong")
                        500 -> getNavigator()!!.onError("Server Not Responding")
                        else -> getNavigator()!!.onError("Something Went Wrong")
                    }
                }
            }

            override fun onFailure(call: Call<PedometerResponsedata>, t: Throwable) {
                getNavigator()!!.onError("Something Went Wrong")
            }
        })
    }


    fun GetPedometer(date:String) {
        var header = HashMap<String, String>()
        header["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        val call = getApiService()!!.GetPedometerData(header, date)
        call.enqueue(object : Callback<PedometerGetResponse> {
            override fun onResponse(call: Call<PedometerGetResponse>, response: Response<PedometerGetResponse>) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if(response!!.body()!!.success){
                            if(!response.body()!!.data.isNullOrEmpty()){
                                getNavigator()!!.onSuccess(response!!.body()!!.data!![0].steps)
                            }else{
                                getNavigator()!!.onSuccess("8000")

                            }
                        }else{
                            getNavigator()!!.onSuccess("8000")
                        }
                    }
                } else {
                    when (response.code()) {
                        403 -> getNavigator()!!.onError("Something Went Wrong")
                        404 -> getNavigator()!!.onError("Something Went Wrong")
                        500 -> getNavigator()!!.onError("Server Not Responding")
                        else -> getNavigator()!!.onError("Something Went Wrong")
                    }
                }
            }

            override fun onFailure(call: Call<PedometerGetResponse>, t: Throwable) {
                getNavigator()!!.onError("Something Went Wrong")
            }
        })
    }


    fun GetArrhythmia(){
        var header = java.util.HashMap<String, String>()
        header["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        getApiService()!!.GetArrhythmia(header).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<arrythmialist_response> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: arrythmialist_response) {
                        if (t != null) {

                            if (t.success) {
                                if (!t.data.isNullOrEmpty()) {
                                    getNavigator()!!.onSuccess(t)
                                } else {
                                    getNavigator()!!.onError("No Data Found")

                                }

                            } else {
                                getNavigator()!!.onError(t.message)
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        getNavigator()!!.onError("Something Went Wrong")

                    }

                })

    }

}