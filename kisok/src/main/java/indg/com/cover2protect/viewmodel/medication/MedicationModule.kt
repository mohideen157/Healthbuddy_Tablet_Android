package indg.com.cover2protect.viewmodel.medication

import dagger.Module
import dagger.Provides
import indg.com.cover2protect.presenter.ApiService
import indg.com.cover2protect.presenter.DataManager
import indg.com.cover2protect.util.HeaderData

@Module
class MedicationModule {


    @Provides
    fun provideViewModel(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData):MedicationViewModel{

        return MedicationViewModel(dataManager,apiService,headerData)
    }
}