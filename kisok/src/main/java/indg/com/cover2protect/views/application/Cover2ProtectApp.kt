package indg.com.cover2protect.views.application

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import indg.com.cover2protect.dagger.component.DaggerAppComponent
import indg.com.cover2protect.helper.DeviceHelper
import javax.inject.Inject
import indg.com.cover2protect.util.crash_handle.CustomActivityOnCrash
import indg.com.cover2protect.util.crash_handle.config.CaocConfig
import indg.com.cover2protect.util.loginutil.LoginButton.TAG
import com.crashlytics.android.Crashlytics
import indg.com.cover2protect.data.database.datasource.DeviceRepository
import indg.com.cover2protect.data.database.local.DeviceDataSource
import indg.com.cover2protect.data.database.local.DeviceDatabase
import indg.com.cover2protect.util.deviceDatabase
import indg.com.cover2protect.util.deviceRepository
import io.fabric.sdk.android.Fabric
import android.net.ConnectivityManager


class Cover2ProtectApp : Application(), HasActivityInjector {

    private val TAG = "Cover2Protect"
    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {

        return activityDispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        try {
            DeviceHelper.init(this)
            Fabric.with(this, Crashlytics())
            CaocConfig.Builder.create().apply()
            DaggerAppComponent.builder()
                    .application(this)
                    .build()
                    .inject(this)
            initdb()


        } catch (ex: Exception) {

        }

    }

    private fun initdb() {
        deviceDatabase = DeviceDatabase.getInstance(this)!!
        deviceRepository = DeviceRepository.getInstance(DeviceDataSource.getInstance(deviceDatabase.cartDAO()))
    }


    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }


    private class CustomEventListener : CustomActivityOnCrash.EventListener {
        override fun onLaunchErrorActivity() {
            Log.i(TAG, "onLaunchErrorActivity()")
        }

        override fun onRestartAppFromErrorActivity() {
            Log.i(TAG, "onRestartAppFromErrorActivity()")
        }

        override fun onCloseAppFromErrorActivity() {
            Log.i(TAG, "onCloseAppFromErrorActivity()")
        }
    }
}