package indg.com.cover2protect.viewmodel.dashboardviewmodel



import androidx.lifecycle.MutableLiveData
import indg.com.cover2protect.model.banddatadetail.updatebanddata.UpsertBanddata
import indg.com.cover2protect.util.HeaderData
import indg.com.cover2protect.viewmodel.BaseViewModel
import java.util.HashMap
import com.google.gson.Gson
import indg.com.cover2protect.helper.MaisenseNetwork
import indg.com.cover2protect.model.banddatadetail.updatebanddata.GetBandResponse
import indg.com.cover2protect.model.deviceresponse.DeviceResponse
import indg.com.cover2protect.presenter.ResponseHandle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DashboardViewModel : BaseViewModel<ResponseHandle>{

    constructor(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData):super(dataManager,apiService,headerData)

    private var devicedata = MutableLiveData<DeviceResponse>()



    fun PostBandData(upsertBanddata: UpsertBanddata){
        val header : HashMap<String,String> = HashMap()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val gson = Gson()
        val json = gson.toJson(upsertBanddata)
        val call = getApiService()!!.UpdateDevice2(header,"hra-band-data", "device-2",json)
        call.enqueue(object : Callback<GetBandResponse> {
            override fun onResponse(call: Call<GetBandResponse>, response: Response<GetBandResponse>) {
                if (response.isSuccessful()) {
                    if(response.body()!!.success == true){
                        getNavigator()!!.OnSuccess("Data Saved Successfully")
                    }else{
                        getNavigator()!!.OnError("Data Not Saved")
                    }
                } else {
                    when (response.code()) {
                        403 -> getNavigator()!!.OnError("Something Went Wrong")
                        404 -> getNavigator()!!.OnError("Something Went Wrong")
                        500 -> getNavigator()!!.OnError("Server Error")
                        else -> getNavigator()!!.OnError("Unknown Error")
                    }
                }
            }
            override fun onFailure(call: Call<GetBandResponse>, t: Throwable) {
                getNavigator()!!.OnError("Something Went Wrong")
            }
        })

    }

    fun GetDeviceData(synchedid:String):MutableLiveData<DeviceResponse>{
        val service = MaisenseNetwork.getRetrofitInstance()!!.create(indg.com.cover2protect.presenter.MaisenseApi::class.java)
        val call = service!!.GetDeviceResponse("som@indglobal-consulting.com",synchedid)
        call.enqueue(object : Callback<DeviceResponse> {
            override fun onResponse(call: Call<DeviceResponse>, response: Response<DeviceResponse>) {
                if(response.isSuccessful){
                    if(response.body()!=null){
                        devicedata.value = response.body()
                    }
                }else{
                    getNavigator()!!.OnError("Something Went Wrong")
                }
            }

            override fun onFailure(call: Call<DeviceResponse>, t: Throwable) {
                getNavigator()!!.OnError("Something Went Wrong")
            }
        })

        return  devicedata


    }

}
