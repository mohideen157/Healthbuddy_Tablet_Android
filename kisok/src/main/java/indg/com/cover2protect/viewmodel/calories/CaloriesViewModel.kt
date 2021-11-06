package indg.com.cover2protect.viewmodel.calories

import androidx.lifecycle.MutableLiveData
import indg.com.cover2protect.presenter.ApiService
import indg.com.cover2protect.presenter.DataManager
import indg.com.cover2protect.model.caloriesgraphresponse.Caloriesgraphresponse
import indg.com.cover2protect.model.caloriesgraphresponse.Data
import indg.com.cover2protect.navigator.TrendsNavigator
import indg.com.cover2protect.util.HeaderData
import indg.com.cover2protect.viewmodel.BaseViewModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

class CaloriesViewModel(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData?) :
        BaseViewModel<TrendsNavigator>(dataManager, apiService, headerData) {

    private var caltrends = MutableLiveData<ArrayList<Data>>()

    fun GetCalTrends(): MutableLiveData<ArrayList<Data>> {
        var bplist = ArrayList<Data>()
        var header = java.util.HashMap<String, String>()
        header["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        getApiService()!!.GetCaloriesGraph(header).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Caloriesgraphresponse> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: Caloriesgraphresponse) {
                        if (t != null) {
                            if (t.success) {
                                if (t.data.isNotEmpty()) {
                                    var startdate: String = ""
                                    var enddate: String = ""
                                    if (!t.data[0].date.isNullOrEmpty()) {
                                        startdate = t.data[0].date
                                    }
                                    if (!t.data[t.data.size - 1].date.isNullOrEmpty()) {
                                        enddate = t.data[t.data.size - 1].date
                                    }
                                    getNavigator()!!.OnSuccess(startdate, enddate)
                                    bplist.addAll(t.data)
                                    caltrends.value = bplist
                                } else {
                                    getNavigator()!!.OnError("No Data")
                                }
                            } else {
                                getNavigator()!!.OnError("Something Went Wrong")
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        getNavigator()!!.OnError("Something Went Wrong")

                    }

                })



        return caltrends
    }

}