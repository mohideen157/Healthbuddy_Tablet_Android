package indg.com.cover2protect.viewmodel.forgotpasswordvm

import dagger.Module
import dagger.Provides
import indg.com.cover2protect.presenter.ApiService
import indg.com.cover2protect.presenter.DataManager
import indg.com.cover2protect.util.HeaderData

@Module
class ForgotPassProvider {

    @Provides
    fun provideViewModel(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData):ForgotPasswordViewModel{
        return ForgotPasswordViewModel(dataManager,apiService,headerData)
    }
}