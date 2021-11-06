package indg.com.cover2protect.adapter.pedometer_adapter.GoogleFit

import android.content.Context
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import indg.com.cover2protect.model.googlefit.monthly_data.data_month
import indg.com.cover2protect.R
import indg.com.cover2protect.databinding.PedometerWeeklyRvBinding

class GooglefitMonthly_Adapter(private var context: Context, private var response: ArrayList<data_month>): androidx.recyclerview.widget.RecyclerView.Adapter<GooglefitMonthly_Adapter.CustomViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val weekLayoutBinding: PedometerWeeklyRvBinding = DataBindingUtil.inflate(layoutInflater, R.layout.pedometer_weekly_rv, parent, false)
        return CustomViewHolder(context, weekLayoutBinding)    }




    override fun getItemCount(): Int {
        return  response!!.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.weekLayoutBinding.startdate.text = response[position].start_date
        holder.weekLayoutBinding.stepLine.setmValueText(response[position].steps)
    }

    class CustomViewHolder(var context: Context, var weekLayoutBinding: PedometerWeeklyRvBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(weekLayoutBinding.root)
}