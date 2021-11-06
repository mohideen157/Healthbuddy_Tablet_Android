package indg.com.cover2protect.viewmodel.medication

import androidx.lifecycle.MutableLiveData
import indg.com.cover2protect.model.diseases.DiseaseData
import indg.com.cover2protect.model.diseases.GetDiseases
import indg.com.cover2protect.model.medicationmodel.deleteresponse.DeleteResponse
import indg.com.cover2protect.model.medicationmodel.medicationdetail.PostMedicationDetail
import indg.com.cover2protect.model.medicationmodel.medicationinfo.Data
import indg.com.cover2protect.model.medicationmodel.medicationinfo.MedicationInfo
import indg.com.cover2protect.model.medicationmodel.medicationnameresponse.PostMedicationResponse
import indg.com.cover2protect.model.medicinedetail.MedicineDetailResponse
import indg.com.cover2protect.model.profilecompletion.ProfileCompletionResponse
import indg.com.cover2protect.navigator.allergyNavigator
import indg.com.cover2protect.util.HeaderData
import indg.com.cover2protect.viewmodel.BaseViewModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList
import java.util.HashMap

class MedicationDetailViewModel : BaseViewModel<allergyNavigator>{

    private var arrayListMutableLiveData = MutableLiveData<ArrayList<DiseaseData>>()
    private var resultdata = MutableLiveData<String>()
    private var responsedata = MutableLiveData<PostMedicationResponse>()
    private var Medicalresultdata = MutableLiveData<String>()
    private val arrayList = ArrayList<DiseaseData>()
    private var medlist = MutableLiveData<ArrayList<Data>>()
    private val medarrayList = ArrayList<Data>()

    constructor(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData):super(dataManager,apiService,headerData)

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


    fun GetMedicineDetail(){
        var header = java.util.HashMap<String, String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        getApiService()!!.GetmedicineDetail(header).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<MedicineDetailResponse> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: MedicineDetailResponse) {
                        if(t.success){
                            if(!t.data.isNullOrEmpty()){
                                getNavigator()!!.onResult(t)
                            }else{
                                getNavigator()!!.OnError("NoDataAvailable")
                            }
                        }else{
                            getNavigator()!!.OnError("NoDataAvailable")
                        }
                    }

                    override fun onError(e: Throwable) {
                        getNavigator()!!.OnError("Something Went Wrong")
                    }

                })

    }


    fun GetMedicalList(): MutableLiveData<ArrayList<Data>> {
        var header = java.util.HashMap<String, String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.GETMedicationList(header)
        call.enqueue(object : Callback<MedicationInfo> {
            override fun onResponse(call: Call<MedicationInfo>, response: Response<MedicationInfo>) {
                if(response.isSuccessful){
                 if(response.body()!=null){
                     medarrayList.clear()
                     if(response.body()!!.success == true){
                         medarrayList.addAll(response.body()!!.data)
                         medlist.value = medarrayList
                     }else{

                         medlist.value = null
                         Medicalresultdata.value = response!!.body()!!.message
                     }
                 }

                }
            }
            override fun onFailure(call: Call<MedicationInfo>, t: Throwable) {

                Medicalresultdata.value = "Something went wrong.."
            }
        })


        return medlist
    }


    fun Diseases(){
        var header = java.util.HashMap<String, String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        getApiService()!!.getMedication(header).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<GetDiseases> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: GetDiseases) {
                        if(!t.data.isNullOrEmpty()){
                            getNavigator()!!.onResult(t)
                        }
                    }

                    override fun onError(e: Throwable) {
                        getNavigator()!!.OnError("Something Went Wrong")
                    }

                })

    }

    fun PostMedication(name:String):MutableLiveData<PostMedicationResponse>{
        var header = java.util.HashMap<String, String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.UpdateMedicationDetail(header,"medication", "medication-details",name)
        call.enqueue(object : Callback<PostMedicationResponse> {
            override fun onResponse(call: Call<PostMedicationResponse>, response: Response<PostMedicationResponse>) {
                if (response.isSuccessful()) {
                   if(response.body()!=null){
                       if(response!!.body()!!.success == true){
                           resultdata.value = "Data Updated Successfully"
                           responsedata.value = response!!.body()
                       }else{
                           responsedata.value = response!!.body()
                           resultdata.value = response.body()!!.message
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
            override fun onFailure(call: Call<PostMedicationResponse>, t: Throwable) {

                resultdata.value = "Something went wrong.."
            }
        })



        return  responsedata
    }

    fun DeleteMedication(name:String):MutableLiveData<String>{
        var header = java.util.HashMap<String, String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.DeleteMedication(header,name)
        call.enqueue(object : Callback<DeleteResponse> {
            override fun onResponse(call: Call<DeleteResponse>, response: Response<DeleteResponse>) {
                if (response.isSuccessful()) {
                    if(response.body()!=null){
                        if(response!!.body()!!.success == true){
                            resultdata.value = response.body()!!.message

                        }else{
                            resultdata.value = response.body()!!.message
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
            override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {

                resultdata.value = "Something went wrong.."
            }
        })



        return  resultdata
    }

    fun DeleteMedicationList(name:String):MutableLiveData<String>{
        var header = java.util.HashMap<String, String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.DeleteMedicationList(header,name)
        call.enqueue(object : Callback<DeleteResponse> {
            override fun onResponse(call: Call<DeleteResponse>, response: Response<DeleteResponse>) {
                if (response.isSuccessful()) {
                   if(response.body()!=null){
                       if(response!!.body()!!.success == true){
                           resultdata.value = response.body()!!.message

                       }else{
                           resultdata.value = response.body()!!.message
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
            override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {

                resultdata.value = "Something went wrong.."
            }
        })



        return  resultdata
    }


    fun UpdateMedicationInfo(type:String,count:String,dosage:String,id:String):MutableLiveData<String>{
        var header = HashMap<String,String>()
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer "+getDataManager()!!.accessToken
        val call = getApiService()!!.UpdateMedDetail(id,header,dosage, type,count)
        call.enqueue(object : Callback<PostMedicationDetail> {
            override fun onResponse(call: Call<PostMedicationDetail>, response: Response<PostMedicationDetail>) {
                if (response.isSuccessful()) {
                  if(response.body()!=null){
                      if(response!!.body()!!.success == true){
                          resultdata.value = "Data Updated Successfully"
                      }else{
                          resultdata.value = response.body()!!.message
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
            override fun onFailure(call: Call<PostMedicationDetail>, t: Throwable) {

                resultdata.value = "Something went wrong.."
            }
        })



        return  resultdata
    }




}