package indg.com.cover2protect.adapter.pedometer_adapter.GoogleFit

import android.content.Context
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import indg.com.cover2protect.model.googlefit.PedometerResponse
import indg.com.cover2protect.R
import indg.com.cover2protect.databinding.PedometerWeeklyRvBinding

class GooglefitWeeklyAdapter(private var context: Context, private var response: ArrayList<PedometerResponse>): androidx.recyclerview.widget.RecyclerView.Adapter<GooglefitWeeklyAdapter.CustomViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val weekLayoutBinding: PedometerWeeklyRvBinding = DataBindingUtil.inflate(layoutInflater, R.layout.pedometer_weekly_rv, parent, false)
        return CustomViewHolder(context, weekLayoutBinding)    }




    override fun getItemCount(): Int {
        return  response!!.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.weekLayoutBinding.startdate.text = response.get(position).start
        holder.weekLayoutBinding.stepLine.setmValueText(response.get(position).steps)
    }

    class CustomViewHolder(var context: Context, var weekLayoutBinding: PedometerWeeklyRvBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(weekLayoutBinding.root) {

    }
}