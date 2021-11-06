package indg.com.cover2protect.viewmodel.loginviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.databinding.ObservableField
import indg.com.cover2protect.helper.DeviceHelper
import indg.com.cover2protect.navigator.MainNavigator
import indg.com.cover2protect.model.login.LoginResponse
import indg.com.cover2protect.util.HeaderData
import indg.com.cover2protect.viewmodel.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class LoginViewModel : BaseViewModel<MainNavigator> {


    constructor(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData) : super(dataManager, apiService, headerData)

    var username = ObservableField("")
    var password = ObservableField("")

    var resultdata = MutableLiveData<String>()
    var getshowprofile = MutableLiveData<String>()
    var email = MutableLiveData<String>()


    fun LoginApi(user: String, pass: String) {
        var result: String = ""
        username.set(user)
        password.set(pass)
        val map = HashMap<String, String>()
        map["username"] = user
        map["password"] = pass
        val call = getApiService()!!.authuser(map)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.getStatus() == true) {
                        if (response.body()!!.getData()!!.getToken() != null) {
                            DeviceHelper.token = response.body()!!.getData()!!.getToken()
                            getDataManager()!!.updateUserInfo(
                                    accessToken = response.body()!!.getData()!!.getToken()!!,
                                    userName = response.body()!!.getData()!!.getUser()!!.getName()!!,
                                    email = response.body()!!.getData()!!.getUser()!!.getEmail()!!,
                                    profilePicPath = ""
                            )
                            getHeader()!!.setHeader(response.body()!!.getData()!!.getToken()!!)
                            result = "success"
                            getshowprofile.value = response.body()!!.getData()!!.getShowProfile().toString()
                            resultdata.value = result
                            email.value = response.body()!!.getData()!!.getUser()!!.getEmail()!!
                        } else {
                            result = "Token Not Found"
                            resultdata.value = result
                        }
                    } else {
                        result = response.body()!!.getMessage().toString()
                        resultdata.value = result
                    }
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                resultdata.value = "Something went wrong.."
            }
        })


    }


    fun getEmailid(): MutableLiveData<String> {
        return email
    }

    fun ShowProfile(): MutableLiveData<String> {
        return getshowprofile
    }

    fun getLoginResult(): MutableLiveData<String> {
        return resultdata
    }


}