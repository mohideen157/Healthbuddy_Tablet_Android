package indg.com.cover2protect.dagger.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import indg.com.cover2protect.dagger.PreferenceInfo
import indg.com.cover2protect.helper.AppDataManager
import indg.com.cover2protect.helper.AppPreferenceHelper
import indg.com.cover2protect.util.HeaderData
import indg.com.cover2protect.util.PREF_NAME
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun provideDataManager(appDataManager: AppDataManager): indg.com.cover2protect.presenter.DataManager {
        return appDataManager
    }


    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    @Provides
    @PreferenceInfo
    internal fun providePreferenceName(): String {
        return PREF_NAME
    }

    @Provides
    @Singleton
    internal fun providePreferencesHelper(appPreferencesHelper: AppPreferenceHelper): indg.com.cover2protect.presenter.PreferencesHelper {
        return appPreferencesHelper
    }

    @Provides
    @Singleton
    internal fun provideHeader(): HeaderData{
        return HeaderData()
    }



}