package indg.com.cover2protect.viewmodel.hhi

import indg.com.cover2protect.model.hhi.hhigraph.Data
import indg.com.cover2protect.model.hhi.hhigraph.hhigraphresponse
import indg.com.cover2protect.navigator.hhinavigator
import indg.com.cover2protect.util.HeaderData
import indg.com.cover2protect.viewmodel.BaseViewModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

class HHIViewmodel(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData) :
        BaseViewModel<hhinavigator>(dataManager,apiService,headerData) {


    fun GetHHI() {
        var hrlist = ArrayList<Data>()
        var header = java.util.HashMap<String, String>()
        header["Authorization"] = "Bearer " + getDataManager()!!.accessToken
        getApiService()!!.GetHHIGraph(header).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<hhigraphresponse> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: hhigraphresponse) {
                        if (t != null) {
                            if (t.status) {
                                if (!t.data.isNullOrEmpty()) {
                                    hrlist.addAll(t.data)
                                    getNavigator()!!.onSuccess(hrlist)
                                } else {
                                    getNavigator()!!.onError("No Data")

                                }
                            } else {
                                getNavigator()!!.onError("No Data")
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        getNavigator()!!.onError("No Data")

                    }

                })
    }



}