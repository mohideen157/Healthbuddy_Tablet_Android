package indg.com.cover2protect.adapter.nutrition.HistoryNutrition

import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.annotation.NonNull
import android.view.LayoutInflater
import android.view.ViewGroup
import indg.com.cover2protect.presenter.NutritionUpdateListener
import indg.com.cover2protect.model.nutrition.nutritionhistory.Date
import indg.com.cover2protect.R
import indg.com.cover2protect.databinding.NutritiontypeRvLayoutBinding


class NutritionLunch_history(private var context: Context, private var response: List<Date>) : androidx.recyclerview.widget.RecyclerView.Adapter<NutritionLunch_history.CustomViewHolder>() {

    private var listener: indg.com.cover2protect.presenter.NutritionUpdateListener?=null


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val nutritionlayout: NutritiontypeRvLayoutBinding = DataBindingUtil.inflate(layoutInflater, R.layout.nutritiontype_rv_layout, parent, false)
        return CustomViewHolder(context, nutritionlayout)
    }


    override fun getItemCount(): Int {
        return response!!.size
    }

    fun setListener(listener: indg.com.cover2protect.presenter.NutritionUpdateListener) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.nutritionlayout.data = response[position].extra_info
        holder.nutritionlayout.itemHomeNameTv.text = capitalize(response[position].extra_info.foodname)
        holder.nutritionlayout.itemHomeCalTv.text = response[position].extra_info.calories
        holder.nutritionlayout.deleteImg.setOnClickListener {
            listener!!.OnDelete(response[position].id.toString())
        }
        holder.nutritionlayout.itemLayout.setOnClickListener {
            listener!!.OnUpdate(response!![position])
        }
    }

    class CustomViewHolder(var context: Context, var nutritionlayout: NutritiontypeRvLayoutBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(nutritionlayout.root)

    fun capitalize(@NonNull input: String): String {
        val words = input.toLowerCase().split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val builder = StringBuilder()
        for (i in words.indices) {
            val word = words[i]

            if (i > 0 && word.length > 0) {
                builder.append(" ")
            }

            val cap = word.substring(0, 1).toUpperCase() + word.substring(1)
            builder.append(cap)
        }
        return builder.toString()
    }


}