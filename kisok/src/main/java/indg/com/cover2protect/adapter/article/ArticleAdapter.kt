package indg.com.cover2protect.adapter.article

import android.content.Context
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import indg.com.cover2protect.presenter.ArticleInterface
import indg.com.cover2protect.model.articles.Data
import indg.com.cover2protect.R
import indg.com.cover2protect.util.BASE_URL
import indg.com.cover2protect.databinding.ArticlervLayoutBinding

class ArticleAdapter(private var context: Context, private var response: ArrayList<Data>) : androidx.recyclerview.widget.RecyclerView.Adapter<ArticleAdapter.CustomViewHolder>() {


    private var listener: ArticleInterface? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val articlervLayoutBinding: ArticlervLayoutBinding = DataBindingUtil.inflate(layoutInflater, R.layout.articlerv_layout, parent, false)
        return CustomViewHolder(context, articlervLayoutBinding)
    }


    override fun getItemCount(): Int {
        return response!!.size
    }

    fun setListener(listener: ArticleInterface) {
        this.listener = listener
    }


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.articlervLayoutBinding.articleTagTv.text = response[position].title
        Glide.with(context).load(BASE_URL + response[position].image).into(holder.articlervLayoutBinding.image)
        holder.articlervLayoutBinding.articleLl.setOnClickListener {
            listener!!.Onselected(response[position])
        }
    }

    class CustomViewHolder(var context: Context, var articlervLayoutBinding: ArticlervLayoutBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(articlervLayoutBinding.root)
}