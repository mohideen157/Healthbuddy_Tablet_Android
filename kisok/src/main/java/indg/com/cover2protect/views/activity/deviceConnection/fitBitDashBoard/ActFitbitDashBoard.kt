package com.getmedcheck.activity.fitBitDashBoard

import android.app.Dialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsServiceConnection
import butterknife.ButterKnife
import butterknife.OnClick
import com.bumptech.glide.Glide

import indg.com.cover2protect.R
import indg.com.cover2protect.baseAeglOrbs.Base2Activity
import indg.com.cover2protect.baseAeglOrbs.networking.ApiUtils
import indg.com.cover2protect.util.SharedPrefUtils
import indg.com.cover2protect.views.activity.deviceConnection.fitBitDashBoard.fitbitResp.RespFitbit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_fitbit_dashboard.*
import kotlinx.android.synthetic.main.toolbar_normal.*


class ActFitbitDashBoard : Base2Activity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fitbit_dashboard)
        ButterKnife.bind(this)

        try {
            onNewIntent(intent)
        } catch (e: Exception) {
        }


        spark_line.setData(arrayListOf(
                298, 46, 87, 178, 446, 167, 855, 543, 662, 1583,1,12
        ))

        tb_normal_title.setText("Your  Activity's")

        iv_tb_normal_back.setImageResource(R.drawable.ic_arrow_back_black_24dp)
        iv_tb_normal_back.setOnClickListener {
            finish()
        }

        mDayMetrics(1090 , 6 ,61 )
        mMonthlyMetrics("1h20" , "1h32" ,"45" )

        if(SharedPrefUtils.getFBTOKEN(applicationContext).length < 3){
            showAlert("FitBit Integration needs your permission",false)
        }else{
            mLoadFitBitUserData(SharedPrefUtils.getFBTOKEN(applicationContext))
        }



    }

    private fun mFitBitPermission() {
         val url = "https://www.fitbit.com/oauth2/authorize?" +
                "response_type=token" +
                "&client_id=22B5XZ" +
                "&expires_in=60480000" +
                "&scope=activity+heartrate+location+nutrition+profile+settings+sleep+social+weight&state"+
                "&redirect_uri=c2p://fitbitpermissioncallback"


        launchTab(applicationContext , Uri.parse(url))

    }

    internal fun launchTab(context: Context, uri: Uri) {
        val connection = object : CustomTabsServiceConnection() {
            override fun onCustomTabsServiceConnected(componentName: ComponentName, client: CustomTabsClient) {
                val builder = CustomTabsIntent.Builder()
                val intent = builder.build()
                client.warmup(0L) // This prevents backgrounding after redirection
                intent.intent.flags = FLAG_ACTIVITY_NEW_TASK ; FLAG_ACTIVITY_CLEAR_TOP ; Intent.FLAG_ACTIVITY_NO_HISTORY
                intent.launchUrl(context, uri)
                finish()
            }

            override fun onServiceDisconnected(name: ComponentName) {}
        }

        CustomTabsClient.bindCustomTabsService(context, "com.android.chrome", connection)
    }

     override fun showAlert(message: String, dismissible: Boolean) {

        val dialog = Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        if (!dismissible) {
            dialog.setCancelable(false)
        }
        dialog.setContentView(R.layout.dialouge_alert)

        val text = dialog.findViewById<View>(R.id.tv_alert_dialogue_message) as TextView
        text.text = message

         val textToolBar = dialog.findViewById<View>(R.id.tb_normal_title) as TextView
         textToolBar.text = "Action required."

        val dialogButton = dialog.findViewById<View>(R.id.btn_alert_dialogue_ok) as Button
        dialogButton.setOnClickListener {
           mFitBitPermission();
            dialog.dismiss()
            finish()
        }
        val ibBack = dialog.findViewById<View>(R.id.iv_tb_normal_back)
         ibBack.setOnClickListener {
             dialog.dismiss()
             finish()
         }
        dialog.show()

    }
    var fitbitResp = ""
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        fitbitResp = intent.dataString!!

       // Log.d("fresp",fitbitResp)
      //  Toast.makeText(applicationContext,fitbitResp,Toast.LENGTH_LONG).show()

        val str = fitbitResp
        val delimiter1 = "access_token="
        val delimiter2 = "&user_id="
        val d3 = "&scope="

        val parts = str.split(delimiter1, delimiter2,d3)
        SharedPrefUtils.setFitBitToken(applicationContext,parts[1])
        SharedPrefUtils.setFitBitMemberId(applicationContext,parts[2])


        mLoadFitBitUserData(parts[1])

    }

    private fun mLoadFitBitUserData(tokenHeader: String) {

        val mApi = ApiUtils.getAPIServiceFitbit(applicationContext);
        mCompositeDisposableGetter().add(mApi.rfxFitBitResp( "Bearer "+tokenHeader)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<RespFitbit>() {
                    override fun onSuccess(t: RespFitbit) {
                        Glide.with(applicationContext).load(t.user.avatar640).into(iv_avatar)
                        txt_name.text = t.user.firstName

                    }

                    override fun onError(e: Throwable) {
                     mFitBitPermission()
                    }
                }))

                }

    val spanEnd = RelativeSizeSpan(.55f)

    private fun mDayMetrics(steps: Int,ran: Int ,bike: Int) {

        val spanTodaySteps = SpannableString(steps.toString()+" k")
        spanTodaySteps.setSpan(spanEnd,spanTodaySteps.length-2,spanTodaySteps.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)


        val spanTodayRan = SpannableString(ran.toString()+" mi")
        spanTodayRan.setSpan(spanEnd,spanTodayRan.length-2,spanTodayRan.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)


        val spanTodayBiked = SpannableString(bike.toString()+" mi")
        spanTodayBiked.setSpan(spanEnd,spanTodayBiked.length-2,spanTodayBiked.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)


        tv_today_steps.text = spanTodaySteps
        tv_today_ran.text = spanTodayRan
        tv_today_biked.text = spanTodayBiked
    }

    private fun mMonthlyMetrics(steps: String, ran: String, bike: String) {

        val spanTodaySteps = SpannableString(steps.toString()+" m")
        spanTodaySteps.setSpan(spanEnd,spanTodaySteps.length-2,spanTodaySteps.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)


        val spanTodayRan = SpannableString(ran.toString()+" m")
        spanTodayRan.setSpan(spanEnd,spanTodayRan.length-2,spanTodayRan.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)


        val spanTodayBiked = SpannableString(bike.toString()+" m")
        spanTodayBiked.setSpan(spanEnd,spanTodayBiked.length-2,spanTodayBiked.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)


        tv_overall_steps.text = spanTodaySteps
        tv_overall_ran.text = spanTodayRan
        tv_overall_biked.text = spanTodayBiked

    }


    override fun onBackPressed() {
        super.onBackPressed()
    }


}
