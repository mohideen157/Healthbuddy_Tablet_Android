package indg.com.cover2protect.adapter.tabacco

import android.app.Activity
import android.content.Context
import androidx.databinding.DataBindingUtil
import android.graphics.Color
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.ViewGroup
import indg.com.cover2protect.presenter.ItemListener
import indg.com.cover2protect.model.count.Digits_Model
import indg.com.cover2protect.R
import indg.com.cover2protect.databinding.TabaccoLayoutBinding
import java.util.ArrayList


class TabaccoAdapter(var context: Activity, var response: ArrayList<Digits_Model>?, var listener: indg.com.cover2protect.presenter.ItemListener) : androidx.recyclerview.widget.RecyclerView.Adapter<TabaccoAdapter.TabaccoAdapterViewHolder>() {


    override fun onBindViewHolder(holder: TabaccoAdapterViewHolder, position: Int) {
        holder.mItemPeopleBinding.numbermodel = response!!.get(position)
        holder.mItemPeopleBinding.numberRl.setOnClickListener { v ->

            mCallback!!.ontobaccoClick(response!!.get(position).getName()!!);
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

    var pos:Int?=null
    private var mCallback: indg.com.cover2protect.presenter.ItemListener? = null

    init {
        this.mCallback = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabaccoAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val tabaccoLayoutBinding: TabaccoLayoutBinding = DataBindingUtil.inflate(layoutInflater, R.layout.tabacco_layout, parent, false)

        return TabaccoAdapterViewHolder(context, tabaccoLayoutBinding)
    }



    override fun getItemCount(): Int {
        return response!!.size
    }


    class TabaccoAdapterViewHolder(var context: Context, var mItemPeopleBinding: TabaccoLayoutBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(mItemPeopleBinding.root) {


    }
}