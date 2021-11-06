package indg.com.cover2protect.views.activity.registration

import androidx.lifecycle.Observer
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.rilixtech.CountryCodePicker
import indg.com.cover2protect.BR
import indg.com.cover2protect.data.errordialog.Sneaker
import indg.com.cover2protect.navigator.RegisterNavigator
import indg.com.cover2protect.presenter.RegisterPresenter
import indg.com.cover2protect.R
import indg.com.cover2protect.base.BaseActivityBinding
import indg.com.cover2protect.viewmodel.registerviewmodel.RegisterViewModel
import indg.com.cover2protect.views.activity.login.LoginActivity
import indg.com.cover2protect.views.activity.registration.otp_registration_activity.Registration_OTPActivity
import indg.com.cover2protect.databinding.ActivityRegisterBinding
import kotlinx.android.synthetic.main.activity_register.*
import javax.inject.Inject
import java.util.regex.Matcher
import java.util.regex.Pattern


class RegisterActivity : BaseActivityBinding<ActivityRegisterBinding>(), RegisterNavigator {


    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.activity_register

    private var binding: ActivityRegisterBinding? = null
    private var countryCodePicker: CountryCodePicker? = null
    private var passwordregex: String = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\\\S+\$).{4,}\$"
    @Inject
    lateinit var viewModel: RegisterViewModel
    private val data = ArrayList<String>()
    private val id = ArrayList<String>()
    private val orgdata = ArrayList<String>()
    private var tenanatname: String = ""
    private var organizationname: String = ""
    private val orgid = java.util.ArrayList<String>()
    val myStrings = arrayOf("No Data Found")
    private var deviceStatus: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeStatusBarColor()
        binding = viewDataBinding
        binding!!.viewModel = viewModel
        viewModel!!.setNavigator(this)
        getSpinnerdata()
        countryCodePicker = findViewById(R.id.ccp)
        binding!!.lifecycleOwner = this
        binding!!.loginpresenter = object : indg.com.cover2protect.presenter.Presenter {
            override fun logIn() {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
            }

        }
        binding!!.presenter = object : RegisterPresenter {
            override fun Register() {
                if (isNetworkConnected) {
                    countryCodePicker!!.enablePhoneAutoFormatter(false)
                    var username: String = et_username.text.toString()
                    var password: String = et_password.text.toString()
                    var mobile: String = et_mobile.text.toString()
                    var email: String = et_email.text.toString()
                    var orgid: String = organizationname
                    var tenantname: String = tenanatname
                    countryCodePicker!!.registerPhoneNumberTextView(et_mobile)
                    if (username.isNotEmpty() && password.isNotEmpty() && mobile.isNotEmpty() && email.isNotEmpty() && orgid.isNotEmpty() && tenantname.isNotEmpty()) {
                        if (isValidPassword(password)) {
                            if (countryCodePicker!!.isValid) {
                                if (isNetworkConnected) {
                                    showLoading()
                                    viewModel!!.RegisterApi(username, password, mobile, email, countryCodePicker!!.selectedCountryCode, orgid, tenantname)

                                } else {
                                    showtoast(resources.getString(R.string.interneterror))
                                }
                            } else {
                                showtoast(resources.getString(R.string.ValidMobile))
                            }
                        } else {
                            showtoast(resources.getString(R.string.PasswordValidation))
                        }
                    } else {
                        showtoast(resources.getString(R.string.Fieldempty))
                    }

                } else {
                    showtoast(resources.getString(R.string.interneterror))
                }

            }

        }

        spinnerselector()

    }


    private fun spinnerselector() {

        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (!data.isNullOrEmpty()) {
                    tenanatname = data[p2]
                    try {
                        viewModel!!.GetOrganisationList(id[p2]).observe(this@RegisterActivity, Observer {
                            if (it != null) {
                                orgdata.clear()
                                for (i in it.indices) {
                                    orgdata.add(it!![i].name)
                                    orgid.add(it!![i].id.toString())
                                }
                                spinner2.adapter = ArrayAdapter(this@RegisterActivity, android.R.layout.simple_spinner_dropdown_item, orgdata.distinct())

                            } else {
                                spinner2.adapter = ArrayAdapter(this@RegisterActivity, android.R.layout.simple_spinner_dropdown_item, myStrings)

                            }

                        })
                    } catch (ex: Exception) {
                    }
                }
            }

        }

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (!orgid.isNullOrEmpty()) {
                    organizationname = orgid[p2]
                }
            }

        }

    }

    private fun getSpinnerdata() {

        try {
            viewModel.GetTenantdata().observe(this, Observer {
                if (it != null) {
                    for (i in it.indices) {
                        data.add(it[i].name)
                        id.add(it[i].id.toString())
                    }
                    spinner1.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, data)

                }

            })
        } catch (ex: Exception) {
        }

    }

    private fun showtoast(it: String?) {

        if (it != null) {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                    .setTitle("Error")
                    .setMessage(it)
                    .sneakError()
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

    override fun OnOtpResponse(user: String, pass: String, email: String, otp: String, id: String, mobile: String) {
        SwitchACtivity(id, otp, mobile)
    }

    fun SwitchACtivity(id: String, otp: String, mobile: String) {
        hideLoading()
        val intent = Intent(this, Registration_OTPActivity::class.java)
        intent.putExtra("id", id)
        intent.putExtra("otp", otp)
        intent.putExtra("mobile", mobile)
        startActivity(intent)

    }

    override fun OnError(message: String) {
        hideLoading()
        showtoast(message)
    }

    override fun OnSuccess(message: String) {
        hideLoading()

    }


    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.darkblue)
        }
    }

}
