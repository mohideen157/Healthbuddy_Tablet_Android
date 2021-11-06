package indg.com.cover2protect.views.fragments.History.AccountFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector
import indg.com.cover2protect.viewmodel.Device2.HomeModule


@Module
abstract class AccountFragmentProvider {

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun ProvideFragment():AccountFragment
}