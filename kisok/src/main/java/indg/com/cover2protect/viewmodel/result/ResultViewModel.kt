package indg.com.cover2protect.viewmodel.result

import androidx.lifecycle.MutableLiveData
import indg.com.cover2protect.presenter.ApiService
import indg.com.cover2protect.presenter.DataManager
import indg.com.cover2protect.navigator.MainNavigator
import indg.com.cover2protect.model.hramodel.hradata
import indg.com.cover2protect.util.HeaderData
import indg.com.cover2protect.viewmodel.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class ResultViewModel: BaseViewModel<MainNavigator> {

    constructor(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData):super(dataManager,apiService,headerData)

    private var resultdata = MutableLiveData<String>()
    private var hra = MutableLiveData<String>()

    fun GetHra() :MutableLiveData<String>{
        var header = HashMap<String,String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.GetHRA(header)
        call.enqueue(object : Callback<hradata> {
            override fun onResponse(call: Call<hradata>, response: Response<hradata>) {
                if (response.isSuccessful()) {
                   if(response.body()!=null){
                       resultdata.value = "Success"
                       if(response.body()!!.data!!.hra!=null && response.body()!!.data!!.hra!=""){
                           var score = response!!.body()!!.data!!.hra
                           score = score.replace("%", "");
                           val data = score.toInt()
                           hra.value = data.toString()
                       }
                   }
                } else {
                    when (response.code()) {
                        403 -> resultdata.value = "Something went wrong"
                        404 -> resultdata.value = "Not Found"

                        500 -> resultdata.value = "Server Broken"
                        else -> resultdata.value = "Unknown Error"
                    }

                }
            }
            override fun onFailure(call: Call<hradata>, t: Throwable) {

                resultdata.value = "Something went wrong.."
            }
        })

        return hra

    }



}