package indg.com.cover2protect.views.fragments.History.ExcerciseFragment

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import indg.com.cover2protect.viewmodel.Device2.HomeModule


@Module
abstract class ExcerciseFragmentProvider {

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun provideFragment():ExcerciseFragment
}