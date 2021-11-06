package indg.com.cover2protect.adapter.quantity

import android.app.Activity
import android.content.Context
import androidx.databinding.DataBindingUtil
import android.graphics.Color
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.ViewGroup
import indg.com.cover2protect.model.count.Digits_Model
import indg.com.cover2protect.R
import indg.com.cover2protect.databinding.NumberLayoutBinding
import java.util.ArrayList
import indg.com.cover2protect.presenter.OnItemClick



class QuantityCountAdapter(var context: Activity, var response: ArrayList<Digits_Model>?, var listener: indg.com.cover2protect.presenter.OnItemClick) : androidx.recyclerview.widget.RecyclerView.Adapter<QuantityCountAdapter.NumberAdapterViewHolder>() {

    var pos:Int?=null
    private var mCallback: indg.com.cover2protect.presenter.OnItemClick? = null

    init {
        this.mCallback = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val numberLayoutBinding: NumberLayoutBinding = DataBindingUtil.inflate(layoutInflater, R.layout.number_layout, parent, false)

        return NumberAdapterViewHolder(context, numberLayoutBinding)
    }

    override fun onBindViewHolder(holder: NumberAdapterViewHolder, position: Int) {
        holder.mItemPeopleBinding.numbermodel = response!!.get(position)
        holder.mItemPeopleBinding.numberRl.setOnClickListener { v ->

            mCallback!!.onClick(response!!.get(position).getName()!!);
            pos= position
            notifyDataSetChanged()
        }
        if(pos == position)
        {
            val colorValue = ContextCompat.getColor(context, R.color.colorPrimary)
            holder.mItemPeopleBinding.number.setTextColor(colorValue)
            holder.mItemPeopleBinding.numberRl.setBackgroundResource(R.drawable.ellipse_selector)

        }else

        {
            val colorVal = ContextCompat.getColor(context, R.color.white)
            holder.mItemPeopleBinding.number.setTextColor(colorVal)
            holder.mItemPeopleBinding.numberRl.setBackgroundColor(Color.TRANSPARENT)

        }

    }

    override fun getItemCount(): Int {
        return response!!.size
    }


    class NumberAdapterViewHolder(var context: Context, var mItemPeopleBinding: NumberLayoutBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(mItemPeopleBinding.root) {


    }
}