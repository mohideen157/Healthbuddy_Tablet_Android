package indg.com.cover2protect.adapter.device_list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ficat.easyble.BleDevice
import indg.com.cover2protect.R
import indg.com.cover2protect.databinding.DeviceViewconnectBinding
import indg.com.cover2protect.events.device_select_listener

class devicelistadapter(private var context: Context, private var response: ArrayList<BleDevice>) : androidx.recyclerview.widget.RecyclerView.Adapter<devicelistadapter.CustomViewHolder>() {


    private var listener: device_select_listener?=null


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val deviceViewconnectBinding: DeviceViewconnectBinding = DataBindingUtil.inflate(layoutInflater, R.layout.device_viewconnect, parent, false)
        return CustomViewHolder(context, deviceViewconnectBinding)
    }


    override fun getItemCount(): Int {
        return response!!.size
    }

    fun setListener(listener:device_select_listener){
        this.listener = listener
    }



    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
       holder.deviceViewconnectBinding.deviceName.text = response!![position].name
        holder.deviceViewconnectBinding.itemview.setOnClickListener {
            listener!!.onSelect(response!![position].name,response!![position].address)
        }
    }

    class CustomViewHolder(var context: Context, var deviceViewconnectBinding: DeviceViewconnectBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(deviceViewconnectBinding.root)
}