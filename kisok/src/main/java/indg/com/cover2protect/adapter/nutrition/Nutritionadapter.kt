package indg.com.cover2protect.adapter.nutrition

import android.content.Context
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import indg.com.cover2protect.presenter.OnItemClick
import indg.com.cover2protect.model.nutrition.nutitionmodel.Common
import indg.com.cover2protect.R
import indg.com.cover2protect.databinding.NutrionLayoutBinding

class Nutritionadapter(private var context:Context,private var response: List<Common>,var listener: indg.com.cover2protect.presenter.OnItemClick): androidx.recyclerview.widget.RecyclerView.Adapter<Nutritionadapter.CustomViewHolder>() {

    private var onclick: indg.com.cover2protect.presenter.OnItemClick? = null

    init {
        this.onclick = listener
    }



    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val nutrionLayoutBinding: NutrionLayoutBinding = DataBindingUtil.inflate(layoutInflater, R.layout.nutrion_layout, parent, false)
        return CustomViewHolder(context, nutrionLayoutBinding)
    }




    override fun getItemCount(): Int {
        return  response!!.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.nutrionLayoutBinding.data = response.get(position)
        holder.nutrionLayoutBinding!!.cardItem.setOnClickListener(View.OnClickListener {
            listener!!.onClick(response.get(position).tag_name)
        })
       Glide.with(context)
                .load(response.get(position).photo.thumb)
                .into(holder!!.nutrionLayoutBinding.foodimg)
    }

    class CustomViewHolder( var context: Context,  var nutrionLayoutBinding: NutrionLayoutBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(nutrionLayoutBinding.root)
}