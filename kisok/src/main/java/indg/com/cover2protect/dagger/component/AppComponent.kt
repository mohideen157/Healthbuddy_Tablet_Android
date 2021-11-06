package indg.com.cover2protect.dagger.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import indg.com.cover2protect.dagger.module.AppModule
import indg.com.cover2protect.dagger.module.NetworkModule
import indg.com.cover2protect.dagger.builder.ActivityBuilder
import indg.com.cover2protect.views.application.Cover2ProtectApp
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class,NetworkModule::class, AppModule::class, ActivityBuilder::class])
interface AppComponent {

    fun inject(app: Cover2ProtectApp)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}