package indg.com.cover2protect.dagger.module

import dagger.Module
import dagger.Provides
import indg.com.cover2protect.util.HeaderData

@Module
class HeaderModule {
    @Provides
    fun provideHeader(): HeaderData {
        return HeaderData()
    }
}