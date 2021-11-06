package indg.com.cover2protect.adapter.medicalreport

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import indg.com.cover2protect.R
import indg.com.cover2protect.databinding.ArrhythmiaRvLayoutBinding
import indg.com.cover2protect.databinding.HealthReportRvLayoutBinding
import indg.com.cover2protect.presenter.reportlistener
import indg.com.cover2protect.model.medicalreport.medical_report_get.Data

class medical_report_adapter(private var context: Context, private var response: ArrayList<Data>) : androidx.recyclerview.widget.RecyclerView.Adapter<medical_report_adapter.CustomViewHolder>() {


    private var listener: indg.com.cover2protect.presenter.reportlistener?=null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val healthReportRvLayoutBinding: HealthReportRvLayoutBinding = DataBindingUtil.inflate(layoutInflater, R.layout.health_report_rv_layout, parent, false)
        return CustomViewHolder(context, healthReportRvLayoutBinding)
    }


    override fun getItemCount(): Int {
        return response!!.size
    }

    fun setListener(listener: indg.com.cover2protect.presenter.reportlistener){
        this.listener = listener
    }



    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.healthReportRvLayoutBinding.data = response[position]
        holder.healthReportRvLayoutBinding.itemLayout.setOnClickListener {
            listener!!.onSelect(response!![position])
        }


    }

    class CustomViewHolder(var context: Context, var healthReportRvLayoutBinding: HealthReportRvLayoutBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(healthReportRvLayoutBinding.root)
}