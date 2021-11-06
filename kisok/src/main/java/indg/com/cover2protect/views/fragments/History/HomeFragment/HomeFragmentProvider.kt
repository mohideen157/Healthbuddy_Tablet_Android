package indg.com.cover2protect.views.fragments.History.HomeFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector
import indg.com.cover2protect.viewmodel.Device2.HomeModule


@Module
abstract class HomeFragmentProvider {

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun ProvideFragment():HomeFragment
}