package indg.com.cover2protect.views.activity.health_profile

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.getmedcheck.activity.fitBitDashBoard.ActFitbitDashBoard
import com.getmedcheck.activity.sugarMonitor.ActBgmChooseDevice
import indg.com.cover2protect.R
import indg.com.cover2protect.helper.DeviceHelper
import indg.com.cover2protect.helper.NetworkUtils
import indg.com.cover2protect.helper.SaveSharedPreference
import indg.com.cover2protect.views.activity.deviceConnection.bmiScale.ActBmiScale
import indg.com.cover2protect.views.activity.deviceConnection.bpMonitor.ActBpChooseDevice
import indg.com.cover2protect.views.activity.health_report.HealthReportActivity
import indg.com.cover2protect.views.activity.login.LoginActivity
import indg.com.cover2protect.views.activity.pedometer.PedometerActivity
import indg.com.cover2protect.views.activity.upload_file.UploadFileActivity
import kotlinx.android.synthetic.main.activity_health_profile.*


class HealthProfileActivity : AppCompatActivity(),
        View.OnClickListener{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_profile)
        changeStatusBarColor()
        back_rl.setOnClickListener(this)
        ripple_pedometer.setOnClickListener { switchtoPedometer() }

        SaveSharedPreference.setUserId(applicationContext,SaveSharedPreference.getMobile(applicationContext))


        upload_ripple.setOnClickListener {
            var intent = Intent(this, UploadFileActivity::class.java)
            startActivity(intent)
        }

        ripple_nutritionhistory.setOnClickListener {

        }

        health_Trends.setOnClickListener {
            var intent = Intent(this, HealthReportActivity::class.java)
            startActivity(intent)
        }
        hhi.setOnClickListener {
            var intent = Intent(this, HHIActivity::class.java)
            startActivity(intent)
        }
        cal_ripple.setOnClickListener {
            var intent = Intent(this, CaloriesTrends::class.java)
            startActivity(intent)
        }

        ripple_activity.setOnClickListener{
            var intent = Intent(this, ActFitbitDashBoard::class.java)
            startActivity(intent)
        }
        ripple_bp.setOnClickListener{
            var intent = Intent(this, ActBpChooseDevice::class.java)
            startActivity(intent)
        }
        ripple_glucose.setOnClickListener{

            var intent = Intent(this, ActBgmChooseDevice::class.java)
            startActivity(intent)

        }
        ripple_bmi.setOnClickListener{
            var intent = Intent(this, ActBmiScale::class.java)
            startActivity(intent)
        }
        button_logout.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setCancelable(false)
            builder.setMessage("Do you want to Logout?")
            builder.setPositiveButton("Yes") { dialog, which ->
                //if user pressed "yes", then he is allowed to exit from application
                logout()
            }
            builder.setNegativeButton("No") { dialog, which ->
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel()
            }
            val alert = builder.create()
            alert.show()
        }

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.back_rl -> onBackPressed()
        }

    }

    private fun logout() {
        try {
            SaveSharedPreference.setDeviceStatus(applicationContext, false)
            SaveSharedPreference.setLoggedIn(applicationContext, false)
            SaveSharedPreference.setDeviceExistence(applicationContext, false)
            SaveSharedPreference.setUserMobile(applicationContext, "")
            SaveSharedPreference.setFirstTime(applicationContext, true)
            DeviceHelper.Clear()
            this.finish()
            var intent = Intent(this, LoginActivity::class.java)
            intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)

        } catch (ex: Exception) {
        }

    }
    private fun switchtoPedometer() {
        startActivity(Intent(this, PedometerActivity::class.java))

    }

    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.colorAccent)
        }
    }

    fun isNetworkConnected(): Boolean {
        return NetworkUtils.isNetworkConnected(getApplicationContext())
    }
}
