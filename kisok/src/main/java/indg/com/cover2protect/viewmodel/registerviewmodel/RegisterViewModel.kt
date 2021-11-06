package indg.com.cover2protect.viewmodel.registerviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.databinding.ObservableField
import indg.com.cover2protect.viewmodel.BaseViewModel
import indg.com.cover2protect.presenter.ApiService
import indg.com.cover2protect.presenter.DataManager


import indg.com.cover2protect.model.organization.organisationmodel
import indg.com.cover2protect.model.registrationmodel.RegistrationResponse
import indg.com.cover2protect.model.tenant.Data
import indg.com.cover2protect.model.tenant.Tenentdata
import indg.com.cover2protect.navigator.RegisterNavigator
import indg.com.cover2protect.util.HeaderData
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterViewModel : BaseViewModel<RegisterNavigator> {

    constructor(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData) : super(dataManager, apiService, headerData)

    var username = ObservableField("")
    var password = ObservableField("")
    var mobile = ObservableField("")
    var email = ObservableField("")
    var orgid = ObservableField("")

    var tenant = MutableLiveData<ArrayList<Data>>()
    var organisation = MutableLiveData<ArrayList<indg.com.cover2protect.model.organization.Data>>()
    var organisationresponse = MutableLiveData<String>()


    var resultdata = MutableLiveData<String>()

    fun RegisterApi(user: String, pass: String, mob: String, emails: String, countrycode: String, orgid: String, tenantname: String) {
        username.set(user)
        password.set(pass)
        mobile.set(mob)
        email.set(emails)
        val map = HashMap<String, String>()
        map.put("country_code", countrycode)
        map.put("email", emails)
        map.put("mobile_no", mob)
        map.put("password", pass)
        map.put("name", user)
        map.put("organisation_id", orgid)
        val call = getApiService()!!.RegisterAuth(map)
        call.enqueue(object : Callback<RegistrationResponse> {
            override fun onResponse(call: Call<RegistrationResponse>, response: Response<RegistrationResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.success){
                        getNavigator()!!.OnOtpResponse(user,pass,emails,response.body()!!.otp,response.body()!!.data.id,response.body()!!.data.mobile_no)
                    }else{
                       if(!response.body()!!.validation.isNullOrEmpty()){
                           getNavigator()!!.OnError(response.body()!!.validation[0])
                       }else{
                           getNavigator()!!.OnError(response.body()!!.message)
                       }
                    }
                }
            }
            override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                getNavigator()!!.OnError(t.message.toString())
            }
        })

    }

    fun isNullOrEmpty(str: String?): Boolean {
        if (str != null && !str.isEmpty())
            return false
        return true
    }

    fun GetTenantdata(): MutableLiveData<ArrayList<Data>> {
        val arrayList = ArrayList<Data>()
        var header: java.util.HashMap<String, String> = getHeader()!!.getHeaderData()
        val call = getApiService()!!.GetTenant()
        call.enqueue(object : Callback<Tenentdata> {
            override fun onResponse(call: Call<Tenentdata>, response: Response<Tenentdata>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        if (response.body()!!.status.equals("Success")) {
                            arrayList.addAll(response.body()!!.data)
                            tenant.value = arrayList
                        } else {
                            resultdata.value = response.body()!!.message
                        }
                    }
                } else {
                    resultdata.value = "Something went wrong"
                }
            }

            override fun onFailure(call: Call<Tenentdata>, t: Throwable) {
                resultdata.value = "Something went wrong.."
            }
        })

        return tenant

    }


    fun GetOrganisationList(value: String): MutableLiveData<ArrayList<indg.com.cover2protect.model.organization.Data>> {
        val orgarrayList = ArrayList<indg.com.cover2protect.model.organization.Data>()
        getApiService()!!.GEtOrganisationlist(value).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<organisationmodel> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: organisationmodel) {
                        if (t != null) {
                            if (t.status == "Success") {
                                orgarrayList.addAll(t.data)
                                organisation.value = orgarrayList
                            } else {
                                organisation.value = null
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        organisation.value = null

                    }

                })



        return organisation
    }


    fun getRegisterResult(): MutableLiveData<String> {
        return resultdata
    }


}

