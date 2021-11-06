package indg.com.cover2protect.viewmodel.medicalreport

import indg.com.cover2protect.helper.MedicalRecordRest
import indg.com.cover2protect.model.Awsresponse.aws_response
import indg.com.cover2protect.model.MedicalReportResponse
import indg.com.cover2protect.model.medicalreport.medical_report_get.get_medical_response
import indg.com.cover2protect.model.medicalreport.medicalreportresponse
import indg.com.cover2protect.navigator.MedicalReportNavigator
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

class MedicalReportViewModel(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData?) :
        BaseViewModel<MedicalReportNavigator>(dataManager, apiService, headerData) {

    fun GetMedicalReport(name: String) {
        val service = MedicalRecordRest.getRetrofitInstance()!!.create(indg.com.cover2protect.presenter.NutrionApi::class.java)
        var body = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                name
        )
        val call = service!!.MedicalReport(body)
        call.enqueue(object : Callback<MedicalReportResponse> {
            override fun onResponse(call: Call<MedicalReportResponse>, response: Response<MedicalReportResponse>) {
                if (response.isSuccessful) {
                    getNavigator()!!.OnSuccess(response.body()!!)
                }
            }

            override fun onFailure(call: Call<MedicalReportResponse>, t: Throwable) {
                getNavigator()!!.OnError("Something went wrong")
            }
        })


    }



    fun GETAWSDetails() {
        val call = getApiService()!!.GetAWS()
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<aws_response> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(search: aws_response) {
                      if(search.data.success){
                          getNavigator()!!.OnUpdate(search)
                      }
                    }

                    override fun onError(e: Throwable) {
                        getNavigator()!!.OnError("Aws Credential Error")
                    }

                    override fun onComplete() {

                    }
                })
    }




    fun GetMedicalReportResponse() {
        var header = HashMap<String, String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        val call = getApiService()!!.GetReport(header)
        call.enqueue(object : Callback<get_medical_response> {
            override fun onResponse(call: Call<get_medical_response>, response: Response<get_medical_response>) {
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        if (!response.body()!!.data.isNullOrEmpty()) {
                            getNavigator()!!.OnUpdate(response.body()!!)
                        } else {
                            getNavigator()!!.OnError("No Data")
                        }
                    } else {
                        getNavigator()!!.OnError("No Data")

                    }
                }
            }

            override fun onFailure(call: Call<get_medical_response>, t: Throwable) {
                getNavigator()!!.OnError("Something went wrong")
            }
        })


    }

    fun PostMedicalReport(file_path: String, bp: String, hr: String, sf: String, snf: String, tri: String, hdl: String, ldl: String) {
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        builder.addFormDataPart("heart_rate", hr)
        builder.addFormDataPart("bp", bp)
        builder.addFormDataPart("sugar_fasting", sf)
        builder.addFormDataPart("sugar_non_fasting", snf)
        builder.addFormDataPart("triglycerides", tri)
        builder.addFormDataPart("hdl_cholesterol", hdl)
        builder.addFormDataPart("ldl_cholesterol", ldl)
        val file = File(file_path)
        builder.addFormDataPart("report_file", file.name, RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file))
        val requestBody = builder.build()
        var header = HashMap<String, String>()
        header["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        val call = getApiService()!!.UpdateHealthReport(header, requestBody)
        call.enqueue(object : Callback<medicalreportresponse> {
            override fun onResponse(call: Call<medicalreportresponse>, response: Response<medicalreportresponse>) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        getNavigator()!!.OnUpdate(response!!.body()!!.message)

                    }

                } else {
                    when (response.code()) {
                        403 -> getNavigator()!!.OnError("Something Went Wrong")
                        404 -> getNavigator()!!.OnError("Server Not Responding")
                        500 -> getNavigator()!!.OnError("Server Not Responding")
                        else -> getNavigator()!!.OnError("Unknown Error")
                    }

                }
            }

            override fun onFailure(call: Call<medicalreportresponse>, t: Throwable) {

                getNavigator()!!.OnError("Something Went Wrong")
            }
        })
    }


}