package indg.com.cover2protect.views.activity.deviceConnection.Adapter
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.getmedcheck.activity.sugarMonitor.ActBgmDisplay
import indg.com.cover2protect.R
import indg.com.cover2protect.baseAeglOrbs.ObjGlucose
import kotlinx.android.synthetic.main.adapter_bp_history.view.*
import java.util.ArrayList


class RViewAdapterGlucoseHistory(
        private val mValues: ArrayList<ObjGlucose>,
        private val context: Context)
    : RecyclerView.Adapter<RViewAdapterGlucoseHistory.ViewHolder>() {


    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as ObjGlucose

            val intentGlucose = Intent(context,ActBgmDisplay::class.java)
            intentGlucose.putExtra("GlucoseHistoric",item)
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
        holder.tvBPM.text = item.mmol
        holder.tvLowHigh.text = item.Type


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
