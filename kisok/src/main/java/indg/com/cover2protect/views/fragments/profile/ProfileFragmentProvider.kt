package indg.com.cover2protect.views.fragments.profile

import dagger.Module
import dagger.android.ContributesAndroidInjector
import indg.com.cover2protect.viewmodel.mainprofileviewmodel.MainProfileModule

@Module
abstract class ProfileFragmentProvider {


    @ContributesAndroidInjector(modules = [(MainProfileModule::class)])
    abstract fun provideProfileFragment(): ProfileFragment
}