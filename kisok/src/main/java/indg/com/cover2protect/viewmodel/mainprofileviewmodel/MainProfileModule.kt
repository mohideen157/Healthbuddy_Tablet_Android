package indg.com.cover2protect.viewmodel.mainprofileviewmodel

import dagger.Module
import dagger.Provides
import indg.com.cover2protect.presenter.ApiService
import indg.com.cover2protect.presenter.DataManager
import indg.com.cover2protect.util.HeaderData


@Module
class MainProfileModule {

    @Provides
    fun provideViewModel(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData):ProfileViewModel{

        return ProfileViewModel(dataManager,apiService,headerData)
    }
}