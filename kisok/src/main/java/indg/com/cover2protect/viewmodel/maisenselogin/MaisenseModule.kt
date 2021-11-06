package indg.com.cover2protect.viewmodel.maisenselogin

import dagger.Module
import dagger.Provides
import indg.com.cover2protect.presenter.ApiService
import indg.com.cover2protect.presenter.DataManager
import indg.com.cover2protect.util.HeaderData


@Module
class MaisenseModule {

    @Provides
    fun ProvideMaisenseViewModel(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData):MaisenseLoginViewModel{

        return MaisenseLoginViewModel(dataManager,apiService,headerData)
    }
}