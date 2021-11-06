package indg.com.cover2protect.viewmodel.medicalreport

import dagger.Module
import dagger.Provides
import indg.com.cover2protect.presenter.ApiService
import indg.com.cover2protect.presenter.DataManager
import indg.com.cover2protect.util.HeaderData


@Module
class MedicalReportModule {

    @Provides
    fun ProvideViewModel(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData):MedicalReportViewModel{

        return MedicalReportViewModel(dataManager,apiService,headerData)

    }
}