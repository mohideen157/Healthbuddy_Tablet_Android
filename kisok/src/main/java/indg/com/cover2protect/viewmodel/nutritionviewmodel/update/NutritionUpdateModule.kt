package indg.com.cover2protect.viewmodel.nutritionviewmodel.update

import dagger.Module
import dagger.Provides
import indg.com.cover2protect.presenter.ApiService
import indg.com.cover2protect.presenter.DataManager
import indg.com.cover2protect.util.HeaderData


@Module
class NutritionUpdateModule {

    @Provides
    fun ProvideViewModel(dataManager: indg.com.cover2protect.presenter.DataManager, apiService: indg.com.cover2protect.presenter.ApiService, headerData: HeaderData):NutritionUpdateVM{

        return NutritionUpdateVM(dataManager,apiService,headerData)
    }
}