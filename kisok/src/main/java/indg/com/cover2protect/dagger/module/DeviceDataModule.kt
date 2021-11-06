package indg.com.cover2protect.dagger.module

import dagger.Module
import dagger.Provides
import indg.com.cover2protect.presenter.ApiService
import indg.com.cover2protect.presenter.DataManager
import indg.com.cover2protect.util.HeaderData
import indg.com.cover2protect.viewmodel.devicedataviewmodel.DeviceViewModel

@Module
class DeviceDataModule {

    @Provides
    fun provideViewModel(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData): DeviceViewModel {
        return DeviceViewModel(dataManager,apiService,headerData)
    }
}