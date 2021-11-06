package indg.com.cover2protect.viewmodel.registration

import indg.com.cover2protect.model.registrationmodel.otp.RegisterOtpVerification
import indg.com.cover2protect.model.send_otp.SendOTPResponse
import indg.com.cover2protect.navigator.response_navigator
import indg.com.cover2protect.util.HeaderData
import indg.com.cover2protect.viewmodel.BaseViewModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationViewModel : BaseViewModel<response_navigator> {


    constructor(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData) : super(dataManager, apiService, headerData)


    fun OTPVerified(mobile: String, id: String) {
        val map = HashMap<String, String>()
        map.put("id", id)
        map.put("mobile_no", mobile)
        getApiService()!!.RegisterOTPuser(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<RegisterOtpVerification> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: RegisterOtpVerification) {
                        try {
                            if (t != null) {
                               if(t.success){
                                   getNavigator()!!.onSuccess(t)
                               }else{
                                   getNavigator()!!.onError(t.message)
                               }
                            }
                        } catch (ex: Exception) {
                        }
                    }

                    override fun onError(e: Throwable) {
                        try {
                            getNavigator()!!.onError("Something went wrong")
                        } catch (ex: Exception) {
                        }
                    }

                })


    }

    fun PostForgotPass(Mobile:String){
        val call = getApiService()!!.UpdateForgotPass(Mobile)
        call.enqueue(object : Callback<SendOTPResponse> {
            override fun onResponse(call: Call<SendOTPResponse>, response: Response<SendOTPResponse>) {
                if (response.isSuccessful) {
                    if(response.body()!=null){
                        if(response.body()!!.success){
                            getNavigator()!!.onSuccess(response.body()!!)

                        }else{
                            getNavigator()!!.onError(response.body()!!.message)
                        }
                    }

                } else {
                    when (response.code()) {
                        403 -> getNavigator()!!.onError("Something Went Wrong")
                        404 -> getNavigator()!!.onError("Something Went Wrong")
                        500 -> getNavigator()!!.onError("Server Not Responding")
                        else ->getNavigator()!!.onError("Unknown Error")
                    }

                }
            }
            override fun onFailure(call: Call<SendOTPResponse>, t: Throwable) {

                getNavigator()!!.onError("Something Went Wrong")
            }
        })



    }

}