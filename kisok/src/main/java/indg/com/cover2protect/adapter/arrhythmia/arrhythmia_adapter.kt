package indg.com.cover2protect.adapter.arrhythmia

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import indg.com.cover2protect.R
import indg.com.cover2protect.databinding.ArrhythmiaRvLayoutBinding
import indg.com.cover2protect.model.arrhythmia.arrythmialist.Data

class arrhythmia_adapter(private var context: Context, private var response: ArrayList<Data>) : androidx.recyclerview.widget.RecyclerView.Adapter<arrhythmia_adapter.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val arrhythmiaRvLayoutBinding: ArrhythmiaRvLayoutBinding = DataBindingUtil.inflate(layoutInflater, R.layout.arrhythmia_rv_layout, parent, false)
        return CustomViewHolder(context, arrhythmiaRvLayoutBinding)
    }


    override fun getItemCount(): Int {
        return response!!.size
    }



    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.arrhythmiaRvLayoutBinding.data = response[position]

    }

    class CustomViewHolder(var context: Context, var arrhythmiaRvLayoutBinding: ArrhythmiaRvLayoutBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(arrhythmiaRvLayoutBinding.root)
}