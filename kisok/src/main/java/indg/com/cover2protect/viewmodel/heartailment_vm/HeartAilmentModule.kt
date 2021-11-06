package indg.com.cover2protect.viewmodel.heartailment_vm

import dagger.Module
import dagger.Provides
import indg.com.cover2protect.presenter.ApiService
import indg.com.cover2protect.presenter.DataManager
import indg.com.cover2protect.util.HeaderData

@Module
class HeartAilmentModule {



    @Provides
    fun provideHeartAilmentVM(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData):HeartAilmentViewModel{
        return  HeartAilmentViewModel(dataManager,apiService,headerData)
    }
}