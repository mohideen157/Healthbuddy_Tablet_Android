package indg.com.cover2protect.views.activity.health_report

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import indg.com.cover2protect.BR
import indg.com.cover2protect.R
import indg.com.cover2protect.adapter.medicalreport.medical_report_adapter
import indg.com.cover2protect.base.BaseActivity
import indg.com.cover2protect.base.BaseActivityBinding
import indg.com.cover2protect.databinding.ActivityHealthReportBinding
import indg.com.cover2protect.presenter.reportlistener
import indg.com.cover2protect.model.MedicalReportResponse
import indg.com.cover2protect.model.medicalreport.medical_report_get.Data
import indg.com.cover2protect.model.medicalreport.medical_report_get.get_medical_response
import indg.com.cover2protect.navigator.MedicalReportNavigator
import indg.com.cover2protect.viewmodel.medicalreport.MedicalReportViewModel
import kotlinx.android.synthetic.main.activity_health_report.*
import javax.inject.Inject

class HealthReportActivity : BaseActivityBinding<ActivityHealthReportBinding>(), MedicalReportNavigator, indg.com.cover2protect.presenter.reportlistener {


    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.activity_health_report


    @Inject
    lateinit var viewmodel: MedicalReportViewModel
    private var adapter: medical_report_adapter? = null
    private var binding:ActivityHealthReportBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        setSupportActionBar(toolbar)
        title = resources.getString(R.string.healthreport)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp)
        toolbar.setNavigationOnClickListener { arrow -> onBackPressed() }
        viewmodel.setNavigator(this)
        getdata()

    }

    private fun getdata() {
       if(isNetworkConnected){
           showLoading()
           viewmodel.GetMedicalReportResponse()
       }else{
           Toast.makeText(this@HealthReportActivity, resources.getText(R.string.internet), Toast.LENGTH_LONG).show()

       }
    }


    override fun OnSuccess(data: MedicalReportResponse) {
        hideLoading()
    }

    override fun OnError(message: String) {
        hideLoading()
        if (message.equals("No Data")) {
            medical_rv.visibility = View.GONE
            nodata_ll.visibility = View.VISIBLE
        }

    }

    override fun OnUpdate(msg: Any) {
        hideLoading()
        if (msg is get_medical_response) {
            medical_rv.visibility = View.VISIBLE
            nodata_ll.visibility = View.GONE
            var data = msg as get_medical_response
            adapter = medical_report_adapter(this, data.data as ArrayList<Data>)
            medical_rv.adapter = adapter
            adapter!!.setListener(this)


        }

    }

    override fun onSelect(listener: Data) {
        var intent = Intent(this, HealthReportInfoActivity::class.java)
        intent.putExtra("data", listener)
        startActivity(intent)
    }


}
