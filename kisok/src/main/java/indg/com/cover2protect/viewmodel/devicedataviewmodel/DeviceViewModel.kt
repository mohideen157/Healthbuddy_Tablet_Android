package indg.com.cover2protect.viewmodel.devicedataviewmodel

import androidx.lifecycle.MutableLiveData
import indg.com.cover2protect.helper.MaisenseNetwork
import indg.com.cover2protect.model.banddatadetail.DetailModel
import indg.com.cover2protect.model.deviceresponse.DeviceResponse
import indg.com.cover2protect.model.event.GetEvent.GetRecordResponse
import indg.com.cover2protect.model.event.PostEvent.PostEventResponse
import indg.com.cover2protect.model.profile.Profile
import indg.com.cover2protect.navigator.devicedata_navigator
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

class DeviceViewModel :BaseViewModel<devicedata_navigator>{

    constructor(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData) : super( dataManager, apiService,headerData)

    private var resultdata = MutableLiveData<DetailModel>()
    private var devicedata = MutableLiveData<DeviceResponse>()
    private var profiledata = MutableLiveData<Profile>()


    fun GetDeviceData(synchedid:String){
        val service = MaisenseNetwork.getRetrofitInstance()!!.create(indg.com.cover2protect.presenter.MaisenseApi::class.java)
        val call = service!!.GetDeviceResponse("som@indglobal-consulting.com",synchedid)
        call.enqueue(object : Callback<DeviceResponse> {
            override fun onResponse(call: Call<DeviceResponse>, response: Response<DeviceResponse>) {
                if(response.isSuccessful){
                   if(response.body()!=null){
                       getNavigator()!!.OnSuccess(response.body()!!)
                   }
                }
            }

            override fun onFailure(call: Call<DeviceResponse>, t: Throwable) {
               getNavigator()!!.OnError("Something Went Wrong")
            }
        })
    }


    fun GetEvent(synchedid:String){
        var header = HashMap<String,String>()
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.GetEvent(header,synchedid)
        call.enqueue(object : Callback<GetRecordResponse> {
            override fun onResponse(call: Call<GetRecordResponse>, response: Response<GetRecordResponse>) {
                if(response.isSuccessful){
                    if(response.body()!=null){
                        if(response.body()!!.success){
                            getNavigator()!!.OnSuccess(response.body()!!)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<GetRecordResponse>, t: Throwable) {
                getNavigator()!!.OnError("Something Went Wrong")
            }
        })



    }


    fun UpdateEvent(synchedid:String,event:String){
        var map = HashMap<String,String>()
        map["synched"] = synchedid
        map["event"] = event
        var header = HashMap<String,String>()
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.UpdateEvent(header,map)
        call.enqueue(object : Callback<PostEventResponse> {
            override fun onResponse(call: Call<PostEventResponse>, response: Response<PostEventResponse>) {
                if(response.isSuccessful){
                    if(response.body()!=null){
                        if(response.body()!!.success){
                            getNavigator()!!.OnSuccess(response.body()!!)
                        }else{
                            getNavigator()!!.OnError("Problem Occured While Updating Event")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<PostEventResponse>, t: Throwable) {
                getNavigator()!!.OnError("Something Went Wrong")
            }
        })



    }

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
                            getNavigator()!!.OnError(t.message.toString())
                        }
                    }
                    override fun onError(e: Throwable) {
                        try {
                            getNavigator()!!.OnError("Something went wrong")
                        } catch (ex: Exception) {
                        }
                    }

                })
        return profiledata

    }


    fun getData():MutableLiveData<DetailModel>{

        return resultdata
    }



}