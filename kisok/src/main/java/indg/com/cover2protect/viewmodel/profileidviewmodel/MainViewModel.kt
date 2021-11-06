package indg.com.cover2protect.viewmodel.profileidviewmodel

import indg.com.cover2protect.model.profile_api.bloodgroupresponse.BloodgroupResponse
import indg.com.cover2protect.model.profile_api.heightresponse.HeightResponse
import indg.com.cover2protect.model.profile_api.profileresponse.ProfileResponse
import indg.com.cover2protect.model.profile_api.weightresponse.WeightResponse
import indg.com.cover2protect.model.profile_api.dobprofile.dobprofileResponse
import indg.com.cover2protect.model.profilecompletion.ProfileCompletionResponse
import indg.com.cover2protect.model.uploadimage.ImageResponse
import indg.com.cover2protect.navigator.response_navigator
import indg.com.cover2protect.util.HeaderData
import indg.com.cover2protect.viewmodel.BaseViewModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.HashMap

class MainViewModel : BaseViewModel<response_navigator> {


    constructor(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData) : super(dataManager, apiService, headerData)


    fun GetHra() {
        var header = HashMap<String, String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        val call = getApiService()!!.GetProfileCompletion(header)
        call.enqueue(object : Callback<ProfileCompletionResponse> {
            override fun onResponse(call: Call<ProfileCompletionResponse>, response: Response<ProfileCompletionResponse>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        if (response.body()!!.success) {
                            getNavigator()!!.onSuccess(response.body()!!)
                        } else {
                            getNavigator()!!.onError("hra exception")
                        }
                    }
                } else {
                    when (response.code()) {
                        403 -> getNavigator()!!.onError("hra exception")
                        404 -> getNavigator()!!.onError("hra exception")
                        500 -> getNavigator()!!.onError("hra exception")
                        else -> getNavigator()!!.onError("hra exception")
                    }

                }
            }

            override fun onFailure(call: Call<ProfileCompletionResponse>, t: Throwable) {

                getNavigator()!!.onError("hra exception")
            }
        })


    }

    fun GetProfiledob() {
        var header = HashMap<String, String>()
        header["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        getApiService()!!.GetProfileAPIDob(header).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<dobprofileResponse> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: dobprofileResponse) {
                        if (t.success) {
                            if (t.data != null) {
                                getNavigator()!!.onSuccess(t)
                            }
                        } else {
                            getNavigator()!!.onError("No Data Found")
                        }
                    }

                    override fun onError(e: Throwable) {
                    }

                })

    }

    fun GetProfileHeight() {
        var header = HashMap<String, String>()
        header["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        getApiService()!!.GetProfileAPIHeight(header, "height").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HeightResponse> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: HeightResponse) {
                        if (t.success) {
                            if (t.data != null) {
                                getNavigator()!!.onSuccess(t)
                            }
                        } else {
                            getNavigator()!!.onError("Something Went Wrong")
                        }
                    }

                    override fun onError(e: Throwable) {
                        getNavigator()!!.onError("Something Went Wrong")
                    }

                })

    }

    fun GetProfileWeight() {
        var header = HashMap<String, String>()
        header["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        getApiService()!!.GetProfileAPIWeight(header, "weight").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<WeightResponse> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: WeightResponse) {
                        if (t.success) {
                            if (t.data != null) {
                                getNavigator()!!.onSuccess(t)
                            }
                        } else {
                            getNavigator()!!.onError("Something Went Wrong")
                        }
                    }

                    override fun onError(e: Throwable) {
                        getNavigator()!!.onError("Something Went Wrong")

                    }

                })

    }

    fun GetProfileBlood() {
        var header = HashMap<String, String>()
        header["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        getApiService()!!.GetProfileAPIBlood(header, "blood-group").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<BloodgroupResponse> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: BloodgroupResponse) {
                        if (t.success) {
                            if (t.data != null) {
                                getNavigator()!!.onSuccess(t)
                            }
                        } else {
                            getNavigator()!!.onError("No Data Found")
                        }
                    }

                    override fun onError(e: Throwable) {
                        getNavigator()!!.onError("Something Went Wrong")

                    }

                })
    }


    fun PostProfileDob(dob: String, nationalid: String, gender: String) {
        var header = HashMap<String, String>()
        header["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        var map = HashMap<String, String>()
        map["national_id"] = nationalid
        map["dob"] = dob
        map["gender"] = gender
        getApiService()!!.PostProfileAPI(header, map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ProfileResponse> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: ProfileResponse) {
                        if (t.success) {
                            getNavigator()!!.onSuccess("Success")
                        } else {
                            getNavigator()!!.onError("Something Went Wrong")
                        }
                    }

                    override fun onError(e: Throwable) {
                        getNavigator()!!.onError("Something Went Wrong")

                    }

                })
    }

    fun PostProfileHeight(heightcm: String, heightfeet: String) {
        var header = HashMap<String, String>()
        header["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        var map = HashMap<String, String>()
        map["height_feet"] = heightfeet
        map["height_cm"] = heightcm
        getApiService()!!.PostProfileAPI(header, map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ProfileResponse> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: ProfileResponse) {
                        if (t.success) {
                            getNavigator()!!.onSuccess("Success")
                        } else {
                            getNavigator()!!.onError("Something Went Wrong")
                        }
                    }

                    override fun onError(e: Throwable) {
                        getNavigator()!!.onError("Something Went Wrong")

                    }

                })
    }

    fun PostProfileWeight(weight_kg: String, weight_pounds: String, bmi: String) {
        var header = HashMap<String, String>()
        header["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        var map = HashMap<String, String>()
        map["weight_kg"] = weight_kg
        map["weight_pounds"] = weight_pounds
        map["bmi"] = bmi
        getApiService()!!.PostProfileAPI(header, map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ProfileResponse> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: ProfileResponse) {
                        if (t.success) {
                            getNavigator()!!.onSuccess("Success")
                        } else {
                            getNavigator()!!.onError("Something Went Wrong")
                        }
                    }

                    override fun onError(e: Throwable) {
                        getNavigator()!!.onError("Something Went Wrong")

                    }

                })
    }


    fun PostProfileBloodGroup(blood_group: String) {
        var header = HashMap<String, String>()
        header["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        var map = HashMap<String, String>()
        map["blood_group"] = blood_group
        getApiService()!!.PostProfileAPI(header, map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ProfileResponse> {
                    override fun onComplete() {

                    }
                    override fun onSubscribe(d: Disposable) {
                    }
                    override fun onNext(t: ProfileResponse) {
                        if (t.success) {
                            getNavigator()!!.onSuccess("Success")
                        } else {
                            getNavigator()!!.onError("Unable to Upload Data")
                        }
                    }
                    override fun onError(e: Throwable) {
                        getNavigator()!!.onError("Something Went Wrong")

                    }

                })
    }


    fun UploadImage(path: String) {
        var header = HashMap<String, String>()
        header["Accept"] = "application/json"
        header["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        val file = File(path)
        val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        val body = MultipartBody.Part.createFormData("app_image", file.getName(), requestFile)
        val call = getApiService()!!.uploadImage(header, body)
        call.enqueue(object : Callback<ImageResponse> {
            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body()!!.success) {
                            getNavigator()!!.onSuccess("Image Saved Successfully")
                        } else {
                            getNavigator()!!.onError(response.body()!!.message)
                        }
                    }
                } else {
                    when (response.code()) {
                        403 -> getNavigator()!!.onError("Something went wrong")
                        404 -> getNavigator()!!.onError("Not Found")
                        500 -> getNavigator()!!.onError("Server Broken")
                        else -> getNavigator()!!.onError("Unknown Error")
                    }

                }
            }

            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                getNavigator()!!.onError("Something Went Wrong")
            }
        })


    }


}