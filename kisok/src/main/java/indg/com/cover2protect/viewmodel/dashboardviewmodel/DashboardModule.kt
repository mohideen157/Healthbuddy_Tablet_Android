package indg.com.cover2protect.viewmodel.dashboardviewmodel

import dagger.Module
import dagger.Provides
import indg.com.cover2protect.presenter.ApiService
import indg.com.cover2protect.presenter.DataManager
import indg.com.cover2protect.util.HeaderData

@Module
class DashboardModule {

    @Provides
    fun provideviewmodel(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData):DashboardViewModel{

        return DashboardViewModel(dataManager,apiService,headerData)
    }
}