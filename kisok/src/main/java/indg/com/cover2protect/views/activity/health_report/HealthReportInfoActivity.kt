package indg.com.cover2protect.views.activity.health_report

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import indg.com.cover2protect.R
import indg.com.cover2protect.model.medicalreport.medical_report_get.Data
import kotlinx.android.synthetic.main.activity_health_report_info.*
import android.R.string


class HealthReportInfoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_report_info)
        setSupportActionBar(toolbar)
        title = resources.getString(R.string.healthreport)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp)
        toolbar.setNavigationOnClickListener { arrow -> onBackPressed() }
        getIntentData()

    }

    private fun getIntentData() {
        if (intent.getSerializableExtra("data") != null) {
            var data = intent.getSerializableExtra("data") as Data
            setdata(data!!)
        }
    }

    private fun setdata(datainfo: Data) {
        if (datainfo != null) {
            if (!datainfo.bp.isNullOrEmpty()) {
                bp_et.text = datainfo.bp
                val parts = datainfo.bp.split("/")
                var sys = parts[0].toString().toDouble()
                var dia = parts[1].toString().toDouble()
                if(sys <=120 && dia <=80){
                    bp_status.setImageResource(R.drawable.greenicon1)
                    bp_et.setTextColor(Color.GREEN)
                }else if(sys in 121.0..140.0 && dia in 80.0..90.0){
                    bp_status.setImageResource(R.drawable.orangeicon1)
                    bp_et.setTextColor(resources.getColor(R.color.fbutton_color_carrot))
                }else if(sys < 40 && dia > 90){
                    bp_status.setImageResource(R.drawable.redicon1)
                    bp_et.setTextColor(Color.RED)
                }
                else if(sys >= 80 && dia > 90){
                    bp_status.setImageResource(R.drawable.redicon1)
                    bp_et.setTextColor(Color.RED)
                }
            }
            if (!datainfo.heart_rate.isNullOrEmpty()) {
                heart_et.text = datainfo.heart_rate
                var hr = datainfo.heart_rate.toDouble()
                if (hr in 60.0..100.0) {
                    hr_status.setImageResource(R.drawable.greenicon1)
                    heart_et.setTextColor(Color.GREEN)
                } else if (hr in 100.0..120.0) {
                    hr_status.setImageResource(R.drawable.orangeicon1)
                    heart_et.setTextColor(resources.getColor(R.color.fbutton_color_carrot))
                } else if (hr in 50.0..60.0) {
                    hr_status.setImageResource(R.drawable.orangeicon1)
                    heart_et.setTextColor(resources.getColor(R.color.fbutton_color_carrot))
                } else if (hr <50 || hr > 120) {
                    hr_status.setImageResource(R.drawable.redicon1)
                    heart_et.setTextColor(Color.RED)
                }
            }


            if (!datainfo.sugar_fasting.isNullOrEmpty()) {
                glucose_et.text = datainfo.sugar_fasting
                var sf = datainfo.sugar_fasting.toDouble()
                if (sf in 60.0..100.0) {
                    sf_status.setImageResource(R.drawable.greenicon1)
                    glucose_et.setTextColor(Color.GREEN)
                } else if (sf in 100.0..125.0) {
                    sf_status.setImageResource(R.drawable.orangeicon1)
                    glucose_et.setTextColor(resources.getColor(R.color.fbutton_color_carrot))
                } else if (sf in 50.0..60.0) {
                    sf_status.setImageResource(R.drawable.orangeicon1)
                    glucose_et.setTextColor(resources.getColor(R.color.fbutton_color_carrot))
                } else if (sf < 50) {
                    sf_status.setImageResource(R.drawable.redicon1)
                    glucose_et.setTextColor(Color.RED)
                } else if (sf > 125) {
                    sf_status.setImageResource(R.drawable.redicon1)
                    glucose_et.setTextColor(Color.RED)
                }
            }

            if (!datainfo.sugar_non_fasting.isNullOrEmpty()) {
                glucose_pp_et.text = datainfo.sugar_non_fasting
                var snf = datainfo.sugar_non_fasting.toDouble()
                if (snf in 70.0..140.0) {
                    snf_status.setImageResource(R.drawable.greenicon1)
                    glucose_pp_et.setTextColor(Color.GREEN)
                } else if (snf in 140.0..199.0) {
                    snf_status.setImageResource(R.drawable.orangeicon1)
                    glucose_pp_et.setTextColor(resources.getColor(R.color.fbutton_color_carrot))
                } else if (snf < 70 || snf > 200) {
                    snf_status.setImageResource(R.drawable.redicon1)
                    glucose_pp_et.setTextColor(Color.RED)
                }


            }

            if (!datainfo.triglycerides.isNullOrEmpty()) {
                tri_et.text = datainfo.triglycerides
                var tri = datainfo.triglycerides.toDouble()
                when {
                    tri < 150 -> {
                        tri_status.setImageResource(R.drawable.greenicon1)
                        tri_et.setTextColor(Color.GREEN)
                    }
                    tri in 150.0..199.0 -> {
                        tri_status.setImageResource(R.drawable.orangeicon1)
                        tri_et.setTextColor(resources.getColor(R.color.fbutton_color_carrot))
                    }
                    tri > 200 -> {
                        tri_status.setImageResource(R.drawable.redicon1)
                        tri_et.setTextColor(Color.RED)
                    }
                }


            }

            if (!datainfo.hdl_cholesterol.isNullOrEmpty()) {
                hdl_et.text = datainfo.hdl_cholesterol
                var hdl = datainfo.hdl_cholesterol.toDouble()
                when {
                    hdl > 60 -> {
                        hdl_status.setImageResource(R.drawable.greenicon1)
                        hdl_et.setTextColor(Color.GREEN)
                    }
                    hdl < 40 -> {
                        hdl_status.setImageResource(R.drawable.redicon1)
                        hdl_et.setTextColor(Color.RED)
                    }
                    hdl in 40.0..59.0 -> {
                        hdl_status.setImageResource(R.drawable.orangeicon1)
                        hdl_et.setTextColor(resources.getColor(R.color.fbutton_color_carrot))
                    }

                }

            }

            if (!datainfo.ldl_cholesterol.isNullOrEmpty()) {
                ldl_et.text = datainfo.ldl_cholesterol
                var ldl = datainfo.ldl_cholesterol.toDouble()
                when {
                    ldl in 15.0..130.0 -> {
                        ldl_status.setImageResource(R.drawable.greenicon1)
                        ldl_et.setTextColor(Color.GREEN)
                    }
                    ldl in 131.0..159.0 -> {
                        ldl_status.setImageResource(R.drawable.orangeicon1)
                        ldl_et.setTextColor(resources.getColor(R.color.fbutton_color_carrot))
                    }
                    ldl in 10.0..15.0 -> {
                        ldl_status.setImageResource(R.drawable.orangeicon1)
                        ldl_et.setTextColor(resources.getColor(R.color.fbutton_color_carrot))

                    }
                    ldl < 10 -> {
                        ldl_status.setImageResource(R.drawable.redicon1)
                        ldl_et.setTextColor(Color.RED)
                    }
                    ldl > 160 -> {
                        ldl_status.setImageResource(R.drawable.redicon1)
                        ldl_et.setTextColor(Color.RED)
                    }

                }
            }

        }


    }
}
