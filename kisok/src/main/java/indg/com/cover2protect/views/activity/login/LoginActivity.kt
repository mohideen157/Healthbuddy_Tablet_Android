package indg.com.cover2protect.views.activity.login

import android.Manifest
import android.app.Activity
import androidx.lifecycle.Observer
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.core.app.ActivityCompat
import android.view.View
import android.view.WindowManager
import com.rilixtech.CountryCodePicker
import indg.com.cover2protect.BR
import indg.com.cover2protect.data.errordialog.Sneaker
import indg.com.cover2protect.helper.SaveSharedPreference
import indg.com.cover2protect.presenter.RegisterPresenter
import indg.com.cover2protect.R
import indg.com.cover2protect.base.BaseActivityBinding
import indg.com.cover2protect.util.HeaderData
import indg.com.cover2protect.viewmodel.loginviewmodel.LoginViewModel
import indg.com.cover2protect.views.activity.forgot_password.ForgotPassActivity
import indg.com.cover2protect.views.activity.registration.RegisterActivity
import indg.com.cover2protect.databinding.ActivityLoginBinding
import indg.com.cover2protect.views.activity.deviceConnection.kisok.kioskDash
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivityBinding<ActivityLoginBinding>(), View.OnClickListener {


    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.activity_login

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.forgot_password -> forgot_password_Click()
        }

    }

    private fun forgot_password_Click() {
        startActivity(Intent(this, ForgotPassActivity::class.java))
    }

    private var binding: ActivityLoginBinding? = null
    private val REQUEST_EXTERNAL_STORAGE = 1
    private  var PERMISSIONS = arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    fun verifyPermissions(activity: Activity?, vararg permissions: String): Boolean {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity != null && permissions != null) {

            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
    }
    @Inject
    lateinit var viewmodel: LoginViewModel
    @Inject
    lateinit var headerData: HeaderData
    private var showprofile: Boolean = false

    private var countryCodePicker: CountryCodePicker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeStatusBarColor()
        binding = viewDataBinding
        binding!!.viewModel = viewmodel
        countryCodePicker = findViewById(R.id.ccp)
        forgot_password!!.setOnClickListener(this)
        if (!SaveSharedPreference.getMobile(applicationContext).isNullOrEmpty()) {
            username.setText(SaveSharedPreference.getMobile(applicationContext))
        }
        if (!verifyPermissions(this, *PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_EXTERNAL_STORAGE)
        }
        GetDataShowProfile()
        viewmodel!!.getLoginResult().observe(this, Observer {
            if (it!! == "success") {
                hideLoading()
                SaveSharedPreference.setLoggedIn(applicationContext, true)
                val intent = Intent(this@LoginActivity, kioskDash::class.java)
                startActivity(intent)
            } else {
                hideLoading()
                showtoast(it)
            }
        })
        binding!!.lifecycleOwner = this
        binding!!.presenter = object : indg.com.cover2protect.presenter.Presenter {
            override fun logIn() {
                var user: String = binding!!.username.text.toString()
                var pass: String = binding!!.password.text.toString()
                countryCodePicker!!.enablePhoneAutoFormatter(false)
                countryCodePicker!!.registerPhoneNumberTextView(username)
                if (user.isNotEmpty() && pass.isNotEmpty()) {
                    if (countryCodePicker!!.isValid) {
                        if (isNetworkConnected) {
                            showLoading()
                            viewmodel!!.LoginApi(countryCodePicker!!.getFullNumber(), pass)
                        } else {
                            showtoast("" + resources.getString(R.string.NetworkError))
                        }
                    } else {
                        showtoast("" + resources.getString(R.string.invalidMobile))
                    }

                } else {
                    showtoast(getString(R.string.fieldvalidation))
                }

            }

        }
        binding!!.registerpresenter = object : RegisterPresenter {
            override fun Register() {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun GetDataShowProfile() {
        viewmodel.ShowProfile().observe(this, Observer {
            if (it != null) {
                showprofile = it == "true"
            }
        })
    }


    private fun showtoast(it: String?) {
        if (it != null) {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                    .setMessage(it)
                    .setHeight(140)
                    .setDuration(3000)
                    .sneakError()
        }

    }

    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.darkblue)
        }
    }


}
