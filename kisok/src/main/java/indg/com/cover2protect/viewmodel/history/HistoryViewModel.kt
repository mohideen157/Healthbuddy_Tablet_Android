package indg.com.cover2protect.viewmodel.history

import com.google.gson.Gson
import indg.com.cover2protect.data.database.model_db.MaisenseDevice
import indg.com.cover2protect.presenter.ApiService
import indg.com.cover2protect.presenter.DataManager
import indg.com.cover2protect.navigator.MainNavigator
import indg.com.cover2protect.model.apiresponse.ApiResponse
import indg.com.cover2protect.util.HeaderData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class HistoryViewModel : indg.com.cover2protect.viewmodel.BaseViewModel<MainNavigator> {

    constructor(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData):super(dataManager,apiService,headerData)

    fun Updatedata(list: MutableList<MaisenseDevice>) {
        val gson = Gson()
        val jsonString = gson.toJson(list)
        var history = HashMap<String,String>()
        history["history"] = jsonString
        var header = HashMap<String,String>()
        header["Accept"] = "application/json"
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.PostDeviceList(header,history)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful()) {
                    if(response.body()!=null){
                        getNavigator()!!.openActivity()
                    }

                } else {
                    when (response.code()) {
                        403 -> getNavigator()!!.handleError("Something went wrong")
                        404 -> getNavigator()!!.handleError("Something went wrong")
                        500 -> getNavigator()!!.handleError("Server Error")
                        else ->getNavigator()!!.handleError("Unknown Error")
                    }

                }
            }
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                getNavigator()!!.handleError("Something went wrong")
            }
        })


    }
}