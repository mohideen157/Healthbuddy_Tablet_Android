package indg.com.cover2protect.viewmodel.multipleques

import androidx.lifecycle.MutableLiveData
import indg.com.cover2protect.model.multiplequesmodel.MultipleQuesResponse
import indg.com.cover2protect.model.profilecompletion.ProfileCompletionResponse
import indg.com.cover2protect.model.ResponseModel.Responsedata
import indg.com.cover2protect.navigator.Main_Navigation
import indg.com.cover2protect.util.HeaderData
import indg.com.cover2protect.viewmodel.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class MultipleQuesVM : BaseViewModel<Main_Navigation> {

    constructor(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData):super(dataManager,apiService,headerData)


    private var resultdata = MutableLiveData<String>()
    private var getmultipleres = MutableLiveData<MultipleQuesResponse>()
    private var scoredata = MutableLiveData<Int>()


    fun UpsertCardiovascularCoronary(answer : String){
        var header = java.util.HashMap<String, String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.UpsertCardiovascularCoronary(header,"cardiovascular-or-stroke","coronary-heart-ischemic-heart", answer)
        call.enqueue(object : Callback<Responsedata> {
            override fun onResponse(call: Call<Responsedata>, response: Response<Responsedata>) {
                if (response.isSuccessful()) {
                    if(response.body()!=null){
                        resultdata.value = "Success"
                        if(response.body()!!.data!!.totalScore!=null && response.body()!!.data!!.totalScore!=""){
                            var score = response!!.body()!!.data!!.totalScore
                            score = score.replace("%", "");
                            val data = score.toInt()
                            scoredata.value = data
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
            override fun onFailure(call: Call<Responsedata>, t: Throwable) {

                resultdata.value = "Something went wrong.."
            }
        })

    }

    fun UpsertCardiovascularAnginaPain(answer : String){
        var header = java.util.HashMap<String, String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.UpsertCardiovascularAnginaPain(header,"cardiovascular-or-stroke","angina-pain", answer)
        call.enqueue(object : Callback<Responsedata> {
            override fun onResponse(call: Call<Responsedata>, response: Response<Responsedata>) {
                if (response.isSuccessful()) {
                  if(response.body()!=null){
                      resultdata.value = "Success"
                      if(response.body()!!.data!!.totalScore!=null && response.body()!!.data!!.totalScore!=""){
                          var score = response!!.body()!!.data!!.totalScore
                          score = score.replace("%", "");
                          val data = score.toInt()
                          scoredata.value = data
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
            override fun onFailure(call: Call<Responsedata>, t: Throwable) {

                resultdata.value = "Something went wrong.."
            }
        })

    }

    fun UpsertCardiovascularRegularMedication(answer : String){
        var header = java.util.HashMap<String, String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.UpsertCardiovascularAnginaPain(header,"cardiovascular-or-stroke","regular-medication", answer)
        call.enqueue(object : Callback<Responsedata> {
            override fun onResponse(call: Call<Responsedata>, response: Response<Responsedata>) {
                if (response.isSuccessful()) {
                  if(response.body()!=null){
                      resultdata.value = "Success"
                      if(response.body()!!.data!!.totalScore!=null && response.body()!!.data!!.totalScore!=""){
                          var score = response!!.body()!!.data!!.totalScore
                          score = score.replace("%", "");
                          val data = score.toInt()
                          scoredata.value = data
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
            override fun onFailure(call: Call<Responsedata>, t: Throwable) {

                resultdata.value = "Something went wrong.."
            }
        })

    }

    fun UpsertCardivascHeartAttack(answer : String){
        var header = java.util.HashMap<String, String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.UpsertCardivascHeartAttack(header,"cardiovascular-or-stroke","heart-attack", answer)
        call.enqueue(object : Callback<Responsedata> {
            override fun onResponse(call: Call<Responsedata>, response: Response<Responsedata>) {
                if (response.isSuccessful()) {
                  if(response.body()!=null){
                        resultdata.value = "Success"
                        if(response.body()!!.data!!.totalScore!=null && response.body()!!.data!!.totalScore!=""){
                            var score = response!!.body()!!.data!!.totalScore
                            score = score.replace("%", "");
                            val data = score.toInt()
                            scoredata.value = data
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
            override fun onFailure(call: Call<Responsedata>, t: Throwable) {

                resultdata.value = "Something went wrong.."
            }
        })

    }

    fun UpsertCardiovascEcg(answer : String){
        var header = java.util.HashMap<String, String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.UpsertCardiovascEcg(header,"cardiovascular-or-stroke","ecg", answer)
        call.enqueue(object : Callback<Responsedata> {
            override fun onResponse(call: Call<Responsedata>, response: Response<Responsedata>) {
                if (response.isSuccessful()) {
                   if(response.body()!=null){
                       resultdata.value = "Success"
                       if(response.body()!!.data!!.totalScore!=null && response.body()!!.data!!.totalScore!=""){
                           var score = response!!.body()!!.data!!.totalScore
                           score = score.replace("%", "");
                           val data = score.toInt()
                           scoredata.value = data
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
            override fun onFailure(call: Call<Responsedata>, t: Throwable) {

                resultdata.value = "Something went wrong.."
            }
        })

    }

    fun UpsertCadiovascCoronaryAngiography(answer : String){
        var header = java.util.HashMap<String, String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.UpsertCadiovascCoronaryAngiography(header,"cardiovascular-or-stroke","coronary-angiography", answer)
        call.enqueue(object : Callback<Responsedata> {
            override fun onResponse(call: Call<Responsedata>, response: Response<Responsedata>) {
                if (response.isSuccessful()) {
                 if(response.body()!=null){
                     resultdata.value = "Success"
                     if(response.body()!!.data!!.totalScore!=null && response.body()!!.data!!.totalScore!=""){
                         var score = response!!.body()!!.data!!.totalScore
                         score = score.replace("%", "");
                         val data = score.toInt()
                         scoredata.value = data
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
            override fun onFailure(call: Call<Responsedata>, t: Throwable) {

                resultdata.value = "Something went wrong.."
            }
        })

    }


    fun UpsertCardiovascBypassSurgery(answer : String){
        var header = java.util.HashMap<String, String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.UpsertCardiovascBypassSurgery(header,"cardiovascular-or-stroke","bypass-surgery", answer)
        call.enqueue(object : Callback<Responsedata> {
            override fun onResponse(call: Call<Responsedata>, response: Response<Responsedata>) {
                if (response.isSuccessful()) {
                   if(response.body()!=null){
                       resultdata.value = "Success"
                       if(response.body()!!.data!!.totalScore!=null && response.body()!!.data!!.totalScore!=""){
                           var score = response!!.body()!!.data!!.totalScore
                           score = score.replace("%", "");
                           val data = score.toInt()
                           scoredata.value = data
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
            override fun onFailure(call: Call<Responsedata>, t: Throwable) {

                resultdata.value = "Something went wrong.."
            }
        })

    }

    fun UpsertCardiovascStentPlacement(answer : String){
        var header = java.util.HashMap<String, String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.UpsertCardiovascStentPlacement(header,"cardiovascular-or-stroke","stent-placement", answer)
        call.enqueue(object : Callback<Responsedata> {
            override fun onResponse(call: Call<Responsedata>, response: Response<Responsedata>) {
                if (response.isSuccessful()) {
                  if(response.body()!=null){
                      resultdata.value = "Success"
                      if(response.body()!!.data!!.totalScore!=null && response.body()!!.data!!.totalScore!=""){
                          var score = response!!.body()!!.data!!.totalScore
                          score = score.replace("%", "");
                          val data = score.toInt()
                          scoredata.value = data
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
            override fun onFailure(call: Call<Responsedata>, t: Throwable) {

                resultdata.value = "Something went wrong.."
            }
        })

    }

    fun UpsertCardiovascValveSurgery(answer : String){
        var header = java.util.HashMap<String, String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.UpsertCardiovascValveSurgery(header,"cardiovascular-or-stroke","valve-surgery", answer)
        call.enqueue(object : Callback<Responsedata> {
            override fun onResponse(call: Call<Responsedata>, response: Response<Responsedata>) {
                if (response.isSuccessful()) {
                  if(response.body()!=null){
                      resultdata.value = "Success"
                      if(response.body()!!.data!!.totalScore!=null && response.body()!!.data!!.totalScore!=""){
                          var score = response!!.body()!!.data!!.totalScore
                          score = score.replace("%", "");
                          val data = score.toInt()
                          scoredata.value = data
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
            override fun onFailure(call: Call<Responsedata>, t: Throwable) {

                resultdata.value = "Something went wrong.."
            }
        })

    }

    //GEt APi
    fun GETMultipleQuestionResponse():MutableLiveData<MultipleQuesResponse>{
        var header = HashMap<String,String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.GETMultipleQuesResponse(header)
        call.enqueue(object : Callback<MultipleQuesResponse> {
            override fun onResponse(call: Call<MultipleQuesResponse>, response: Response<MultipleQuesResponse>) {
               if(response.isSuccessful){
                   if(response.body()!=null){
                       if(response!!.body()!!.success == true){
                           getmultipleres.value = response.body()

                       }else{
                           resultdata.value = response!!.body()!!.message
                       }
                   }
               }
            }

            override fun onFailure(call: Call<MultipleQuesResponse>, t: Throwable) {

                resultdata.value = "Something went wrong.."
            }
        })

        return getmultipleres

    }

    fun GetHra() {
        var header = HashMap<String,String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.GetProfileCompletion(header)
        call.enqueue(object : Callback<ProfileCompletionResponse> {
            override fun onResponse(call: Call<ProfileCompletionResponse>, response: Response<ProfileCompletionResponse>) {
                if (response.isSuccessful()) {
                    if(response.body()!=null){
                        if(response.body()!!.success == true){
                            getNavigator()!!.OnData(response.body()!!.data.toString())
                        }else{
                            getNavigator()!!.OnError("hra exception")
                        }
                    }
                } else {
                    when (response.code()) {
                        403 -> getNavigator()!!.OnError("hra exception")
                        404 -> getNavigator()!!.OnError("hra exception")
                        500 ->  getNavigator()!!.OnError("hra exception")
                        else ->  getNavigator()!!.OnError("hra exception")
                    }

                }
            }
            override fun onFailure(call: Call<ProfileCompletionResponse>, t: Throwable) {

                getNavigator()!!.OnError("hra exception")
            }
        })



    }

    fun getData():MutableLiveData<String>{
        return  resultdata
    }
    fun GetScore(): MutableLiveData<Int> {
        return scoredata
    }



}