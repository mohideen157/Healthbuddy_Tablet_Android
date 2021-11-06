package indg.com.cover2protect.adapter.nutrition.NutritionListAdapter

import android.content.Context
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import indg.com.cover2protect.presenter.OnItemClick
import indg.com.cover2protect.model.nutrition.nutitionmodel.Common
import indg.com.cover2protect.R
import indg.com.cover2protect.databinding.ItemLayoutNutritionBinding

class NutritionListAdapter(private var context: Context, private var response: List<Common>) : androidx.recyclerview.widget.RecyclerView.Adapter<NutritionListAdapter.CustomViewHolder>() {

    private var listener: indg.com.cover2protect.presenter.OnItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemLayoutNutritionBinding: ItemLayoutNutritionBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_layout_nutrition, parent, false)
        return CustomViewHolder(context, itemLayoutNutritionBinding)
    }


    override fun getItemCount(): Int {
        return response!!.size
    }

    fun SetListener(listener: indg.com.cover2protect.presenter.OnItemClick) {
        this.listener = listener
    }


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.itemLayoutNutritionBinding.data = response!![position]
        holder.itemLayoutNutritionBinding.itemSearchCommonLayout.setOnClickListener {
            listener!!.onClick(response!![position].tag_name)
        }
        Glide.with(context).load(response!![position].photo.thumb).into(holder.itemLayoutNutritionBinding.itemSearchCommonIv)
    }

    class CustomViewHolder(var context: Context, var itemLayoutNutritionBinding: ItemLayoutNutritionBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemLayoutNutritionBinding.root)
}
