package indg.com.cover2protect.views.activity.registration

import dagger.Module
import dagger.Provides
import indg.com.cover2protect.presenter.ApiService
import indg.com.cover2protect.presenter.DataManager
import indg.com.cover2protect.util.HeaderData
import indg.com.cover2protect.viewmodel.registerviewmodel.RegisterViewModel

@Module
class  RegisterModule{

    @Provides
    fun provideRegisterViewModel(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData): RegisterViewModel {
        return RegisterViewModel(dataManager,apiService,headerData)
    }
}