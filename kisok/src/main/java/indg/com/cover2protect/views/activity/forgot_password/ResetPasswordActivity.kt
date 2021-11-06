package indg.com.cover2protect.views.activity.forgot_password

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.HintRequest
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.GoogleApiClient
import indg.com.cover2protect.BR
import indg.com.cover2protect.data.errordialog.Sneaker
import indg.com.cover2protect.helper.AppSignatureHelper
import indg.com.cover2protect.helper.SMSBroadcastReceiver
import indg.com.cover2protect.R
import indg.com.cover2protect.base.BaseActivityBinding
import indg.com.cover2protect.viewmodel.forgotpasswordvm.ForgotPasswordViewModel
import indg.com.cover2protect.databinding.ActivityResetPasswordBinding
import kotlinx.android.synthetic.main.activity_reset_password.*
import javax.inject.Inject


class ResetPasswordActivity : BaseActivityBinding<ActivityResetPasswordBinding>(), SMSBroadcastReceiver.OTPReceiveListener, indg.com.cover2protect.presenter.OTPResponse {



    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.activity_reset_password


    @Inject
    lateinit var viewmodel: ForgotPasswordViewModel
    private var password: String = ""
    private var mobile: String = ""


    private var activityResetPasswordBinding: ActivityResetPasswordBinding? = null
    var mCredentialsApiClient: GoogleApiClient? = null
    private val RC_HINT = 2
    val smsBroadcast = SMSBroadcastReceiver()
    private var otp_final: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityResetPasswordBinding = viewDataBinding
        mCredentialsApiClient = GoogleApiClient.Builder(this)
                .addApi(Auth.CREDENTIALS_API)
                .build()
        viewmodel.setNavigator(this)
        setSupportActionBar(toolbar)
        setTitle(resources.getString(R.string.forgot_pass))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp)
        toolbar.setNavigationOnClickListener { arrow -> onBackPressed() }
        getIntentdata()
        requestHint()
        startSMSListener()
        smsBroadcast.initOTPListener(this)
        val intentFilter = IntentFilter()
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION)
        applicationContext.registerReceiver(smsBroadcast, intentFilter)
        AppSignatureHelper(applicationContext).appSignatures
        submit.setOnClickListener(View.OnClickListener {
            SubmitOTP()
        })

        resend.setOnClickListener(View.OnClickListener {
            resendOTP()
        })


    }

    private fun resendOTP() {
        if (!mobile.isNullOrEmpty()) {
            showsuccess(getString(R.string.otpresent))
            viewmodel.PostForgotPass(mobile)
        } else {
            showtoast(getString(R.string.mobilerequired))
        }
    }

    private fun getIntentdata() {
        mobile = intent.getStringExtra("mobile")
        if (!intent.getStringExtra("otp").isNullOrEmpty()) {
            otp_final = intent.getStringExtra("otp")
        }

    }

    private fun startSMSListener() {
        SmsRetriever.getClient(this).startSmsRetriever()
                .addOnSuccessListener {
                    showinfo("Waiting for OTP")
                }.addOnFailureListener {
                    showtoast(getString(R.string.smsretriver))
                }
    }

    private fun requestHint() {
        val hintRequest = HintRequest.Builder().setPhoneNumberIdentifierSupported(true).build()
        val intent = Auth.CredentialsApi.getHintPickerIntent(mCredentialsApiClient, hintRequest)

        try {
            startIntentSenderForResult(intent.intentSender, RC_HINT, null, 0, 0, 0)
        } catch (e: Exception) {
            Log.e("Error In getting Msg", e.message)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_HINT && resultCode == Activity.RESULT_OK) {
            val credential: Credential = data!!.getParcelableExtra(Credential.EXTRA_KEY)
            print("credential : $credential")
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
    private fun showsuccess(it: String?) {

        if (it != null) {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                    .setTitle("Resend OTP")
                    .setMessage(it)
                    .sneakSuccess()
        }

    }


    private fun showinfo(it: String?) {

        if (it != null) {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                    .setTitle("Waiting")
                    .setMessage(it)
                    .sneakWarning()
        }

    }

    override fun OnOTP(otp: String) {
        otp_final = otp

    }

    override fun OnSuccess(message: String) {

    }

    override fun OnError(msg: String) {
        showtoast(msg)
    }


    override fun onOTPReceived(otp: String) {
        androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance(this).unregisterReceiver(smsBroadcast)
        pinview.value = otp
        sendData(otp)

    }

    private fun sendData(otp: String) {
        if (!pinview.value.isNullOrEmpty()) {
            if (otp == otp_final) {
                var intent = Intent(this,NewPasswordActivity::class.java)
                intent.putExtra("mobile",mobile)
                startActivity(intent)
                overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out)

            } else {
                showtoast("INAVLID OTP")
            }
        }
    }

    fun SubmitOTP() {
        if (!pinview.value.isNullOrEmpty()) {
            if (pinview.value.equals(otp_final)) {
                var intent = Intent(this,NewPasswordActivity::class.java)
                intent.putExtra("mobile",mobile)
                startActivity(intent)
                overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out)

            } else {
                showtoast("INAVLID OTP")
            }
        }
    }

    override fun onOTPTimeOut() {
        showtoast("SMS retriever API Timeout")
    }
}
