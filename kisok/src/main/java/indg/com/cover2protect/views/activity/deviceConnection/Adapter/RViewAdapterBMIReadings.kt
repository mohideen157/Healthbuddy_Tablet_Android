package indg.com.cover2protect.views.activity.deviceConnection.Adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import indg.com.cover2protect.R
import indg.com.cover2protect.baseAeglOrbs.W3Obj
import kotlinx.android.synthetic.main.adapter_bp_history.view.*
import java.util.*


class RViewAdapterBMIReadings(
        private val mValues: ArrayList<W3Obj>,
        private val context: Context)
    : RecyclerView.Adapter<RViewAdapterBMIReadings.ViewHolder>() {



    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->


            val item = v.tag as W3Obj
            //bmiHistoricInterface.onHistoricItemClicked(item)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_bp_history, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mValues?.get(holder.adapterPosition)

        holder.tvDate.text = item.DateOfReading
        holder.tvBPM.text = item.WeigthKg
        holder.tvLowHigh.text = item.BMI

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
