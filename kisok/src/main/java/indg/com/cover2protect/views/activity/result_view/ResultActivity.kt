package indg.com.cover2protect.views.activity.result_view

import androidx.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.view.View
import indg.com.cover2protect.BR
import indg.com.cover2protect.helper.DeviceHelper
import indg.com.cover2protect.R
import indg.com.cover2protect.base.BaseActivityBinding
import indg.com.cover2protect.databinding.ActivityResultBinding
import indg.com.cover2protect.viewmodel.result.ResultViewModel
import indg.com.cover2protect.views.activity.home.ProfileActivity
import kotlinx.android.synthetic.main.activity_result.*
import javax.inject.Inject

class ResultActivity : BaseActivityBinding<ActivityResultBinding>(), View.OnClickListener {


    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.activity_result

    @Inject
    lateinit var viewmodel:ResultViewModel
    private var binding:ActivityResultBinding?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        back_rl.setOnClickListener(this)
        forward.setOnClickListener(this)
        getdata()

    }
    override fun onClick(view: View?) {

        when(view!!.id){

            R.id.back_rl ->onBackPressed()
            R.id.forward ->switchActivity()

        }
    }

    private fun switchActivity() {
        if(DeviceHelper.Rememberstatus){
            DeviceHelper.DeviceStatus = false
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }else{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getdata() {
        try{
            viewmodel!!.GetHra().observe(this, Observer {
                if(it!=null){
                    donut_progress.setProgress(it!!.toInt())

                }
            })
        }catch (ex:Exception){

        }
    }
}
