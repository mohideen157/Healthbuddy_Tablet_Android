package indg.com.cover2protect.base

import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableBoolean


import indg.com.cover2protect.presenter.DataManager

import java.lang.ref.WeakReference

import io.reactivex.disposables.CompositeDisposable


abstract class BaseViewModel<N>(val dataManager: indg.com.cover2protect.presenter.DataManager) : ViewModel() {

    val isLoading = ObservableBoolean(false)

    val compositeDisposable: CompositeDisposable

    private var mNavigator: WeakReference<N>? = null

    var navigator: N?
        get() = mNavigator!!.get()
        set(navigator) {
            this.mNavigator = WeakReference<N>(navigator)
        }

    init {
        this.compositeDisposable = CompositeDisposable()
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun setIsLoading(isLoading: Boolean) {
        this.isLoading.set(isLoading)
    }
}
