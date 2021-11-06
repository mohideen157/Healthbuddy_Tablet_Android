package indg.com.cover2protect.viewmodel.Device2

import dagger.Module
import dagger.Provides
import indg.com.cover2protect.presenter.ApiService
import indg.com.cover2protect.presenter.DataManager
import indg.com.cover2protect.util.HeaderData

@Module
class HomeModule  {

    @Provides
    fun ProvideViewModel(dataManager: DataManager,apiService: ApiService,headerData: HeaderData):HomeViewModel{

        return HomeViewModel(dataManager,apiService,headerData)
    }
}