package indg.com.cover2protect.dagger.module

import dagger.Module
import dagger.Provides
import dagger.Reusable
import indg.com.cover2protect.presenter.ApiService
import indg.com.cover2protect.util.BASE_URL
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


@Module
object NetworkModule {

    @Reusable
    @Provides
    @JvmStatic
    internal fun providePostApi(retrofit: Retrofit): indg.com.cover2protect.presenter.ApiService
    {
        return retrofit.create(indg.com.cover2protect.presenter.ApiService::class.java)
    }


    @Provides
    @JvmStatic
    @Reusable
    internal fun provideRetrofitInterface(): Retrofit
    {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()

    }


}