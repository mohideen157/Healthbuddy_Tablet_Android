package indg.com.cover2protect.adapter.nutrition.HistoryNutrition

import android.content.Context
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import indg.com.cover2protect.presenter.DeleteExcerciseListener
import indg.com.cover2protect.model.excerciseapi.excercise_get.Data
import indg.com.cover2protect.R
import indg.com.cover2protect.databinding.ExcerciseRvLayoutBinding
import androidx.annotation.NonNull


class Excercise_History_Adapter(private var context: Context, private var response: List<Data>) : androidx.recyclerview.widget.RecyclerView.Adapter<Excercise_History_Adapter.CustomViewHolder>() {

    private var listener: indg.com.cover2protect.presenter.DeleteExcerciseListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val excerciseRvLayoutBinding: ExcerciseRvLayoutBinding = DataBindingUtil.inflate(layoutInflater, R.layout.excercise_rv_layout, parent, false)
        return CustomViewHolder(context, excerciseRvLayoutBinding)
    }


    override fun getItemCount(): Int {
        return response!!.size
    }

    fun SetListener(listener: indg.com.cover2protect.presenter.DeleteExcerciseListener){
        this.listener = listener
    }


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.excerciseRvLayoutBinding.data = response[position].extra_info
        if(!response!![position].extra_info.desc.isNullOrEmpty()){
            holder.excerciseRvLayoutBinding.itemHomeNameTv.text = capitalize(response!![position].extra_info.desc)

        }
        holder.excerciseRvLayoutBinding.deleteImg.setOnClickListener {
            listener!!.Delete(response!![position].id.toString())
        }
    }

    class CustomViewHolder(var context: Context, var excerciseRvLayoutBinding: ExcerciseRvLayoutBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(excerciseRvLayoutBinding.root)

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
