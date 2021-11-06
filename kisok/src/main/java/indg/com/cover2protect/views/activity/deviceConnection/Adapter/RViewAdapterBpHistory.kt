package indg.com.cover2protect.views.activity.deviceConnection.Adapter
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import indg.com.cover2protect.R
import indg.com.cover2protect.baseAeglOrbs.BpObj
import indg.com.cover2protect.views.activity.deviceConnection.bpMonitor.ActBpDisplay
import kotlinx.android.synthetic.main.adapter_bp_history.view.*


class RViewAdapterBpHistory(
        private val mValues: ArrayList<BpObj>,
        val context: Context)
    : RecyclerView.Adapter<RViewAdapterBpHistory.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as BpObj
            val intentGlucose = Intent(context, ActBpDisplay::class.java)
            intentGlucose.putExtra("BpHistoric",item)
            context.startActivity(intentGlucose)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_bp_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues?.get(position)


        holder.tvDate.text = item.Date
        holder.tvBPM.text = item.PUL
        holder.tvLowHigh.text = item.SYS_mmHg +"/"+ item.DIA_mmHg


        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues!!.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val tvDate: TextView = mView.tv_ap_bp_history_date
        val tvBPM: TextView = mView.tv_ap_bp_history_bpm_average
        val tvLowHigh: TextView = mView.tv_ap_bp_history_low_high


    }
}
