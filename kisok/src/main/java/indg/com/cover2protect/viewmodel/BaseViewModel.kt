package indg.com.cover2protect.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableBoolean
import indg.com.cover2protect.presenter.ApiService
import indg.com.cover2protect.presenter.DataManager
import indg.com.cover2protect.util.HeaderData
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

abstract class BaseViewModel<N>: ViewModel {

    private var mDataManager: indg.com.cover2protect.presenter.DataManager?=null
    private var mApiService: indg.com.cover2protect.presenter.ApiService?=null
    private var mcontext:Application?=null
    private val mIsLoading = ObservableBoolean(false)
    private val mCompositeDisposable: CompositeDisposable?=null
    private var header : HeaderData?=null

    private var mNavigator: WeakReference<N>? = null
    constructor(dataManager: indg.com.cover2protect.presenter.DataManager){
        mDataManager = dataManager

    }

    constructor(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData?){
        mDataManager = dataManager
        mApiService = apiService
        header = headerData
    }



    override fun onCleared() {
        mCompositeDisposable!!.dispose()
        super.onCleared()
    }

    fun getHeader():HeaderData?
    {
        return header
    }

    fun getCompositeDisposable(): CompositeDisposable? {
        return mCompositeDisposable
    }


    fun getDataManager(): indg.com.cover2protect.presenter.DataManager? {
        return mDataManager
    }

    fun getApiService(): indg.com.cover2protect.presenter.ApiService?{
        return  mApiService
    }


    fun getIsLoading(): ObservableBoolean {
        return mIsLoading
    }

    fun setIsLoading(isLoading: Boolean) {
        mIsLoading.set(isLoading)
    }

    fun getNavigator(): N? {
        return mNavigator!!.get()
    }

    fun setNavigator(navigator: N) {
        this.mNavigator = WeakReference(navigator)
    }





}