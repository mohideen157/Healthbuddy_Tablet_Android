package indg.com.cover2protect.adapter.afib

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import indg.com.cover2protect.R
import indg.com.cover2protect.databinding.AfibRvLayoutBinding
import indg.com.cover2protect.model.trends.afib.Data
import indg.com.cover2protect.util.BASE_URL

class afib_adapter(private var context: Context, private var response: ArrayList<Data>) : androidx.recyclerview.widget.RecyclerView.Adapter<afib_adapter.CustomViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val afibRvLayoutBinding: AfibRvLayoutBinding = DataBindingUtil.inflate(layoutInflater, R.layout.afib_rv_layout, parent, false)
        return CustomViewHolder(context, afibRvLayoutBinding)
    }


    override fun getItemCount(): Int {
        return response!!.size
    }



    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.afibRvLayoutBinding.data = response[position]

    }

    class CustomViewHolder(var context: Context, var afibRvLayoutBinding: AfibRvLayoutBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(afibRvLayoutBinding.root)
}