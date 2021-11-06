package indg.com.cover2protect.viewmodel.medication

import androidx.lifecycle.MutableLiveData
import indg.com.cover2protect.model.MedicationHealthProfile.medicationhealthProfileResponse
import indg.com.cover2protect.model.profilecompletion.ProfileCompletionResponse
import indg.com.cover2protect.model.ResponseModel.Responsedata
import indg.com.cover2protect.navigator.response_navigator
import indg.com.cover2protect.util.HeaderData
import indg.com.cover2protect.viewmodel.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class MedicationViewModel : BaseViewModel<response_navigator>{

    constructor(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData):super(dataManager,apiService,headerData)

    private var resultdata = MutableLiveData<String>()
    private var scoredata = MutableLiveData<Int>()

    fun GetHra() {
        var header = java.util.HashMap<String, String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.GetProfileCompletion(header)
        call.enqueue(object : Callback<ProfileCompletionResponse> {
            override fun onResponse(call: Call<ProfileCompletionResponse>, response: Response<ProfileCompletionResponse>) {
                if (response.isSuccessful()) {
                    if(response.body()!=null){
                        if(response.body()!!.success == true){
                            getNavigator()!!.onSuccess(response.body()!!)
                        }else{
                            getNavigator()!!.onError("hra exception")
                        }
                    }
                } else {
                    when (response.code()) {
                        403 -> getNavigator()!!.onError("hra exception")
                        404 -> getNavigator()!!.onError("hra exception")
                        500 ->  getNavigator()!!.onError("hra exception")
                        else ->  getNavigator()!!.onError("hra exception")
                    }

                }
            }
            override fun onFailure(call: Call<ProfileCompletionResponse>, t: Throwable) {

                getNavigator()!!.onError("hra exception")
            }
        })



    }


    fun UpsertData(answer : String){
        var header = HashMap<String,String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.UpsertMedication(header,"medication", answer)
        call.enqueue(object : Callback<Responsedata> {
            override fun onResponse(call: Call<Responsedata>, response: Response<Responsedata>) {
                if (response.isSuccessful) {
                   if(response.body()!!.success){
                       getNavigator()!!.onSuccess(response.body()!!)
                       if(response.body()!!.data!!.totalScore!=null && response.body()!!.data!!.totalScore!=""){
                           var score = response!!.body()!!.data!!.totalScore
                           score = score.replace("%", "");
                           val data = score.toInt()
                           scoredata.value = data
                       }
                   }else{
                       getNavigator()!!.onError("Unable to Upload Data")
                   }
                } else {
                    when (response.code()) {
                        403 -> getNavigator()!!.onError("Something Went Wrong")
                        404 -> getNavigator()!!.onError("Data Not Found")
                        500 -> getNavigator()!!.onError("Server Not Responding")
                        else ->getNavigator()!!.onError("Unknown Error")
                    }
                }
            }
            override fun onFailure(call: Call<Responsedata>, t: Throwable) {
                getNavigator()!!.onError("Something Went Wrong")

            }
        })



    }

    fun GETMedicationData(){
        var header = HashMap<String,String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.GETMedicationdata(header)
        call.enqueue(object : Callback<medicationhealthProfileResponse> {
            override fun onResponse(call: Call<medicationhealthProfileResponse>, response: Response<medicationhealthProfileResponse>) {
              if(response.isSuccessful){
                  if(response.body()!!.success){
                      getNavigator()!!.onSuccess(response.body()!!)
                  }else{
                      getNavigator()!!.onError("No Data Found")
                  }
              }
            }

            override fun onFailure(call: Call<medicationhealthProfileResponse>, t: Throwable) {
                getNavigator()!!.onError("Something Went Wrong")
            }
        })


    }




}