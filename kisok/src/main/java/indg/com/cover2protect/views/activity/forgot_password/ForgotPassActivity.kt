package indg.com.cover2protect.views.activity.forgot_password

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.rilixtech.CountryCodePicker
import indg.com.cover2protect.BR
import indg.com.cover2protect.data.errordialog.Sneaker
import indg.com.cover2protect.R
import indg.com.cover2protect.base.BaseActivityBinding
import indg.com.cover2protect.databinding.ActivityForgotPassBinding
import indg.com.cover2protect.viewmodel.forgotpasswordvm.ForgotPasswordViewModel
import kotlinx.android.synthetic.main.activity_forgot_pass.*
import javax.inject.Inject


class ForgotPassActivity : BaseActivityBinding<ActivityForgotPassBinding>(), View.OnClickListener, indg.com.cover2protect.presenter.OTPResponse {




    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.activity_forgot_pass

    @Inject
    lateinit var viewmodel: ForgotPasswordViewModel
    private var countryCodePicker: CountryCodePicker? = null
    private var binding: ActivityForgotPassBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeStatusBarColor()
        binding = viewDataBinding
        viewmodel.setNavigator(this)
        countryCodePicker = findViewById(R.id.ccp)
        submit.setOnClickListener(this)
        setSupportActionBar(toolbar)
        setTitle(resources.getString(R.string.forgot_pass))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp)
        toolbar.setNavigationOnClickListener { arrow -> onBackPressed() }


    }

    private fun showToast(it: String) {
        Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setTitle("Error")
                .setMessage(it)
                .sneakError()
    }

    override fun OnOTP(otp: String) {
        hideLoading()
        var intent = Intent(this, ResetPasswordActivity::class.java)
        intent.putExtra("mobile", countryCodePicker!!.fullNumber)
        intent.putExtra("otp", otp)
        startActivity(intent)
    }


    override fun OnSuccess(message: String) {
        hideLoading()
    }

    override fun OnError(msg: String) {
        hideLoading()
        showToast(msg)
    }


    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.submit -> submit_OnClick()
        }
    }

    private fun submit_OnClick() {
        if (!username.text.isNullOrEmpty()) {
            showLoading()
            countryCodePicker!!.enablePhoneAutoFormatter(false)
            countryCodePicker!!.registerPhoneNumberTextView(username)
            viewmodel.PostForgotPass(countryCodePicker!!.fullNumber)


        } else {
            showToast("Please Enter Mobile Number")
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
