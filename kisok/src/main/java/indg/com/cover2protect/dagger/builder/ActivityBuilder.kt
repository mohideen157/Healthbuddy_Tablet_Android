package indg.com.cover2protect.dagger.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import indg.com.cover2protect.viewmodel.Device2.HomeModule
import indg.com.cover2protect.viewmodel.calories.CaloriesModule
import indg.com.cover2protect.viewmodel.forgotpasswordvm.ForgotPassProvider
import indg.com.cover2protect.viewmodel.mainprofileviewmodel.MainProfileModule
import indg.com.cover2protect.viewmodel.medicalreport.MedicalReportModule
import indg.com.cover2protect.viewmodel.pedometer.PedometerModule
import indg.com.cover2protect.viewmodel.registration.RegistrationModule
import indg.com.cover2protect.viewmodel.result.ResultModule

import indg.com.cover2protect.viewmodel.hhi.HHIModule
import indg.com.cover2protect.views.activity.forgot_password.ForgotPassActivity
import indg.com.cover2protect.views.activity.forgot_password.NewPasswordActivity
import indg.com.cover2protect.views.activity.forgot_password.ResetPasswordActivity
import indg.com.cover2protect.views.activity.health_profile.CaloriesTrends
import indg.com.cover2protect.views.activity.health_profile.HHIActivity
import indg.com.cover2protect.views.activity.health_report.HealthReportActivity
import indg.com.cover2protect.views.activity.login.*

import indg.com.cover2protect.views.activity.pedometer.PedometerActivity
import indg.com.cover2protect.views.activity.registration.otp_registration_activity.Registration_OTPActivity
import indg.com.cover2protect.views.activity.registration.RegisterActivity
import indg.com.cover2protect.views.activity.registration.RegisterModule
import indg.com.cover2protect.views.activity.result_view.ResultActivity

import indg.com.cover2protect.views.fragments.profile.ProfileFragmentProvider
import indg.com.cover2protect.views.activity.home.ProfileActivity
import indg.com.cover2protect.views.activity.upload_file.UploadFileActivity

import indg.com.cover2protect.views.activity.device2.HistoryActivity
import indg.com.cover2protect.views.fragments.History.AccountFragment.AccountFragmentProvider
import indg.com.cover2protect.views.fragments.History.DeviceFragment.DeviceFragmentProvider
import indg.com.cover2protect.views.fragments.History.ExcerciseFragment.ExcerciseFragmentProvider
import indg.com.cover2protect.views.fragments.History.HomeFragment.HomeFragmentProvider

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(LoginModule::class)])
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [(RegisterModule::class)])
    abstract fun bindRegisterActivity(): RegisterActivity

    @ContributesAndroidInjector(modules = ([CaloriesModule::class]))
    abstract fun ProvideCal(): CaloriesTrends

    @ContributesAndroidInjector(modules = ([HHIModule::class]))
    abstract fun bindhhi(): HHIActivity

    @ContributesAndroidInjector(modules = ([ProfileFragmentProvider::class, MainProfileModule::class]))
    abstract fun bindNutritionModule(): ProfileActivity

    @ContributesAndroidInjector(modules = ([ResultModule::class]))
    abstract fun bindResultActivity(): ResultActivity

    @ContributesAndroidInjector(modules = ([ForgotPassProvider::class]))
    abstract fun bindForgotPass(): ForgotPassActivity

    @ContributesAndroidInjector(modules = ([ForgotPassProvider::class]))
    abstract fun bindNewPass(): NewPasswordActivity

    @ContributesAndroidInjector(modules = ([ForgotPassProvider::class]))
    abstract fun bindForgotResetPass(): ResetPasswordActivity

    @ContributesAndroidInjector(modules = ([RegistrationModule::class]))
    abstract fun bindRegistration(): Registration_OTPActivity

    @ContributesAndroidInjector(modules = ([PedometerModule::class]))
    abstract fun ProvideViewModel(): PedometerActivity

    @ContributesAndroidInjector(modules = ([HomeModule::class, AccountFragmentProvider::class,
        DeviceFragmentProvider::class, ExcerciseFragmentProvider::class,
        HomeFragmentProvider::class]))
    abstract fun ProvidehomeViewModel(): HistoryActivity

    @ContributesAndroidInjector(modules = ([MedicalReportModule::class]))
    abstract fun ProvideMedicalReport(): UploadFileActivity

    @ContributesAndroidInjector(modules = ([MedicalReportModule::class]))
    abstract fun ProvideMedicalReportResponse(): HealthReportActivity
}