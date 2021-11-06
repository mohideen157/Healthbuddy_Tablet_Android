package indg.com.cover2protect.viewmodel.multipleques

import dagger.Module
import dagger.Provides
import indg.com.cover2protect.presenter.ApiService
import indg.com.cover2protect.presenter.DataManager
import indg.com.cover2protect.util.HeaderData

@Module
class MultipleQuesModule {

    @Provides
    fun ProvideMultipleQuesVM(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData):MultipleQuesVM{
        return MultipleQuesVM(dataManager,apiService,headerData)
    }
}