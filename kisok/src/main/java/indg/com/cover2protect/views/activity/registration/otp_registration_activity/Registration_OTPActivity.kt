package indg.com.cover2protect.views.activity.registration.otp_registration_activity

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
import indg.com.cover2protect.base.BaseActivity
import indg.com.cover2protect.data.errordialog.Sneaker
import indg.com.cover2protect.helper.AppSignatureHelper
import indg.com.cover2protect.helper.SMSBroadcastReceiver
import indg.com.cover2protect.R
import indg.com.cover2protect.model.registrationmodel.otp.RegisterOtpVerification
import indg.com.cover2protect.model.send_otp.SendOTPResponse
import indg.com.cover2protect.navigator.response_navigator
import indg.com.cover2protect.viewmodel.registration.RegistrationViewModel
import indg.com.cover2protect.views.activity.login.LoginActivity
import kotlinx.android.synthetic.main.activity_registration__otp.*
import javax.inject.Inject

class Registration_OTPActivity : BaseActivity(), SMSBroadcastReceiver.OTPReceiveListener, View.OnClickListener, response_navigator {

    @Inject
    lateinit var viewModel:RegistrationViewModel
    var mCredentialsApiClient: GoogleApiClient? = null
    private val KEY_IS_RESOLVING = "is_resolving"
    private val RC_HINT = 2
    private var otpReceiver: SMSBroadcastReceiver.OTPReceiveListener = this
    private var mobile:String?=null
    private var id:String?=null
    private var finalotp:String?=null

    val smsBroadcast = SMSBroadcastReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration__otp)
        mCredentialsApiClient = GoogleApiClient.Builder(this)
                .addApi(Auth.CREDENTIALS_API)
                .build()
        viewModel.setNavigator(this)
        setSupportActionBar(toolbar)
        title = resources.getString(R.string.register)
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
        submit.setOnClickListener(this)
        resend.setOnClickListener(View.OnClickListener {
            resendOTP()
        })
    }

    override fun onSuccess(data: Any) {
        hideLoading()
        if(data is RegisterOtpVerification){
            var intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }else if(data is SendOTPResponse){
            showinfo("OTP RESENT SUCCESSFULLY")
            var dataval = data as SendOTPResponse
            finalotp = dataval.otp.toString()
        }
    }

    override fun onError(msg: String) {
        hideLoading()
        showtoast(msg)    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.submit -> sendDatatoViewmodel()
        }
    }

    private fun sendDatatoViewmodel() {
        if(!pinview.value.isNullOrEmpty()){
            if(pinview.value == finalotp){
                showLoading()
                viewModel.OTPVerified(this!!.mobile!!, this!!.id!!)
            }else{
                showtoast("OTP NOT MATCHED")
            }
        }
    }



    override fun onOTPReceived(otp: String) {
        androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance(this).unregisterReceiver(smsBroadcast)
        pinview.value = otp
        finalotp = otp
        sendData(otp)
    }

    private fun sendData(otp_view: String) {
        if(otp_view == finalotp){
            viewModel.OTPVerified(this!!.mobile!!, this!!.id!!)
        }else{
            showtoast("OTP NOT MATCHED")
        }
    }

    override fun onOTPTimeOut() {
        showtoast(" OTP TIMEOUT ")

    }

    private fun resendOTP() {
        if (!mobile.isNullOrEmpty()) {
            viewModel.PostForgotPass(mobile!!)
        } else {
            showtoast("Mobile Number is required")
        }
    }

    private fun startSMSListener() {
        SmsRetriever.getClient(this).startSmsRetriever()
                .addOnSuccessListener {
                    showinfo("Waiting for OTP")
                }.addOnFailureListener {
                    showtoast("Cannot Start SMS Retriever")
                }    }

    private fun requestHint() {
        val hintRequest = HintRequest.Builder().setPhoneNumberIdentifierSupported(true).build()
        val intent = Auth.CredentialsApi.getHintPickerIntent(mCredentialsApiClient, hintRequest)

        try {
            startIntentSenderForResult(intent.intentSender, RC_HINT, null, 0, 0, 0)
        } catch (e: Exception) {
            Log.e("Error In getting Msg", e.message)
        }    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_HINT && resultCode == Activity.RESULT_OK) {
            val credential: Credential = data!!.getParcelableExtra(Credential.EXTRA_KEY)
            print("credential : $credential")
        }
    }

    private fun getIntentdata() {
        if(!intent.getStringExtra("id").isNullOrEmpty()){
            id = intent.getStringExtra("id")
        }
        if(!intent.getStringExtra("mobile").isNullOrEmpty()){
            mobile = intent.getStringExtra("mobile")
        }
        if(!intent.getStringExtra("otp").isNullOrEmpty()){
            finalotp = intent.getStringExtra("otp")
        }
    }


    private fun showtoast(it: String?) {

        if (it != null) {
            Sneaker.with(this)
                    .setMessage(it)
                    .sneakError()
        }

    }

    private fun showinfo(it: String?) {

        if (it != null) {
            Sneaker.with(this)
                    .setMessage(it)
                    .sneakWarning()
        }

    }
}
