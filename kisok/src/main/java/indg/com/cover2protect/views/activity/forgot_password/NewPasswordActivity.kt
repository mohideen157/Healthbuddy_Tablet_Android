package indg.com.cover2protect.views.activity.forgot_password

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import indg.com.cover2protect.BR
import indg.com.cover2protect.data.errordialog.Sneaker
import indg.com.cover2protect.R
import indg.com.cover2protect.base.BaseActivityBinding
import indg.com.cover2protect.databinding.ActivityNewPasswordBinding
import indg.com.cover2protect.viewmodel.forgotpasswordvm.ForgotPasswordViewModel
import indg.com.cover2protect.views.activity.login.LoginActivity
import kotlinx.android.synthetic.main.activity_new_password.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject

class NewPasswordActivity : BaseActivityBinding<ActivityNewPasswordBinding>(), View.OnClickListener, indg.com.cover2protect.presenter.OTPResponse {



    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.activity_new_password


    @Inject
    lateinit var viewmodel: ForgotPasswordViewModel
    private var mobile: String = ""
    private var binding:ActivityNewPasswordBinding?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        viewmodel.setNavigator(this)
        setSupportActionBar(toolbar)
        title = resources.getString(R.string.newpass)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp)
        toolbar.setNavigationOnClickListener { arrow -> onBackPressed() }
        submit.setOnClickListener(this)
        if (!intent.getStringExtra("mobile").isNullOrEmpty()) {
            mobile = intent.getStringExtra("mobile")
        }

    }

    fun isValidPassword(password: String): Boolean {
        val pattern: Pattern
        val matcher: Matcher

        val PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\\\d)(?=.*[$@$!%*#?&])[A-Za-z\\\\d$@$!%*#?&]{8,}$"

        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)

        // return matcher.matches()
        return true

    }

    private fun showToast(it: String) {
        Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setTitle("Error")
                .setMessage(it)
                .sneakError()
    }
    override fun OnSuccess(message: String) {
        hideLoading()
        val intent = Intent(applicationContext, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }


    override fun OnError(error: String) {
        hideLoading()
    }

    override fun OnOTP(otp: String) {
        hideLoading()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.submit -> Validation()
        }
    }

    private fun Validation() {
        if (password.text.toString().equals(passwordnew.text.toString())) {
            if (isValidPassword(password.text.toString())) {
                showLoading()
                viewmodel.ResetPassword(mobile, password.text.toString())
            }
        } else {
            Toast.makeText(applicationContext, "Password does not match!",
                    Toast.LENGTH_LONG).show();
        }
    }

}
