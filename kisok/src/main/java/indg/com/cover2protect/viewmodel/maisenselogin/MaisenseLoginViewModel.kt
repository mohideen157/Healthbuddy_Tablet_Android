package indg.com.cover2protect.viewmodel.maisenselogin

import indg.com.cover2protect.model.login.LoginResponse
import indg.com.cover2protect.navigator.MaisenseNavigator
import indg.com.cover2protect.util.HeaderData
import indg.com.cover2protect.viewmodel.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class MaisenseLoginViewModel(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData?) : BaseViewModel<MaisenseNavigator>(dataManager, apiService, headerData) {


    fun LoginApi(user: String, pass: String) {
        var result: String = ""
        val map = HashMap<String, String>()
        map["username"] = user
        map["password"] = pass
        val call = getApiService()!!.authuser(map)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.getStatus() == true) {
                        if (!response.body()!!.getData()!!.getToken().isNullOrEmpty()) {
                           getNavigator()!!.OnSuccess(response.body()!!.getData()!!.getUser()!!.getEmail()!!.toString())
                        } else {
                           getNavigator()!!.OnError("Token Not Found")
                        }
                    } else {
                       getNavigator()!!.OnError(response!!.body()!!.getMessage().toString())
                    }
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                getNavigator()!!.OnError("Something Went Wrong")
            }
        })


    }



}