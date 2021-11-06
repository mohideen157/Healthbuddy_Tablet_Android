package indg.com.cover2protect.viewmodel.history

import dagger.Module
import dagger.Provides
import indg.com.cover2protect.presenter.ApiService
import indg.com.cover2protect.presenter.DataManager
import indg.com.cover2protect.util.HeaderData

@Module
class HistoryModule {


        @Provides
        fun ProvideViewModel(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData):HistoryViewModel{
            return HistoryViewModel(dataManager,apiService,headerData)
        }

}