package indg.com.cover2protect.viewmodel.Device2

import indg.com.cover2protect.navigator.response_navigator
import indg.com.cover2protect.presenter.ApiService
import indg.com.cover2protect.presenter.DataManager
import indg.com.cover2protect.util.HeaderData
import indg.com.cover2protect.viewmodel.BaseViewModel

class HomeViewModel(dataManager: DataManager, apiService: ApiService, headerData: HeaderData?) :
        BaseViewModel<response_navigator>(dataManager, apiService, headerData) {


}