package indg.com.cover2protect.viewmodel.forgotpasswordvm

import androidx.lifecycle.MutableLiveData
import indg.com.cover2protect.presenter.ApiService
import indg.com.cover2protect.presenter.DataManager
import indg.com.cover2protect.presenter.OTPResponse
import indg.com.cover2protect.model.resetpassword.ResetPassword
import indg.com.cover2protect.model.send_otp.SendOTPResponse
import indg.com.cover2protect.util.HeaderData
import indg.com.cover2protect.viewmodel.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordViewModel : BaseViewModel<indg.com.cover2protect.presenter.OTPResponse>{
    private var resultdata = MutableLiveData<String>()

    constructor(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData):super(dataManager,apiService,headerData)

    fun PostForgotPass(Mobile:String){
        val call = getApiService()!!.UpdateForgotPass(Mobile)
        call.enqueue(object : Callback<SendOTPResponse> {
            override fun onResponse(call: Call<SendOTPResponse>, response: Response<SendOTPResponse>) {
                if (response.isSuccessful) {
                  if(response.body()!=null){
                      if(response.body()!!.success){
                          getNavigator()!!.OnOTP(response.body()!!.otp.toString())

                      }else{
                          getNavigator()!!.OnError(response.body()!!.message)
                      }
                  }

                } else {
                    when (response.code()) {
                        403 -> getNavigator()!!.OnError("Something Went Wrong")
                        404 -> getNavigator()!!.OnError("Something Went Wrong")
                        500 -> getNavigator()!!.OnError("Server Not Responding")
                        else ->getNavigator()!!.OnError("Unknown Error")
                    }

                }
            }
            override fun onFailure(call: Call<SendOTPResponse>, t: Throwable) {

                getNavigator()!!.OnError("Something Went Wrong")
            }
        })



    }


    fun ResetPassword(Mobile:String,password:String) {
        val call = getApiService()!!.ResetPassword(Mobile,password)
        call.enqueue(object : Callback<ResetPassword> {
            override fun onResponse(call: Call<ResetPassword>, response: Response<ResetPassword>) {
                if (response.isSuccessful) {
                  if(response.body()!=null){
                      if(response.body()!!.success){
                          getNavigator()!!.OnSuccess(response.body()!!.email)

                      }else{
                          getNavigator()!!.OnError(response.body()!!.message)
                      }
                  }

                } else {
                    when (response.code()) {
                        403 -> getNavigator()!!.OnError("Something Went Wrong")
                        404 -> getNavigator()!!.OnError("Something Went Wrong")
                        500 -> getNavigator()!!.OnError("Server Not Responding")
                        else ->getNavigator()!!.OnError("Unknown Error")
                    }

                }
            }
            override fun onFailure(call: Call<ResetPassword>, t: Throwable) {

                getNavigator()!!.OnError("Something Went Wrong")
            }
        })


    }


}