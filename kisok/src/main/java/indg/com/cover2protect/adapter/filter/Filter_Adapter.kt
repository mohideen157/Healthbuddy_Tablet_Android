package indg.com.cover2protect.adapter.filter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import indg.com.cover2protect.R
import indg.com.cover2protect.util.multiselector.Filter_Object
import android.view.LayoutInflater
import indg.com.cover2protect.presenter.DeleteListener
import indg.com.cover2protect.presenter.DiseaseListener
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.*


class Filter_Adapter(var context: Context, var arrMenu: ArrayList<Filter_Object>) : BaseAdapter() {
    internal var `val` = 0
    private var listener: indg.com.cover2protect.presenter.DiseaseListener? = null
    private var deletelistener: indg.com.cover2protect.presenter.DeleteListener? = null
    private var arrayList: ArrayList<Filter_Object>?=null

    private var click = PublishSubject.create<String>()
    val clickevent:Observable<String> = click

    init {

        this.arrayList = ArrayList()
        this.arrayList!!.addAll(arrMenu)

    }

    fun updateListView(mArray: ArrayList<Filter_Object>) {
        this.arrMenu = mArray
        notifyDataSetChanged()
    }

    fun setListener(listener: indg.com.cover2protect.presenter.DiseaseListener) {
        this.listener = listener
    }
    fun setDeleteListener(listener: indg.com.cover2protect.presenter.DeleteListener) {
        this.deletelistener = listener
    }

    override fun getCount(): Int {
        return this.arrMenu.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val viewHolder: ViewHolder
        if (convertView == null) {
            val li = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = li.inflate(R.layout.filter_list_item, null)
            viewHolder = ViewHolder()
            viewHolder.mTtvName = convertView!!.findViewById<View>(R.id.tvName) as TextView
            viewHolder.mTvSelected = convertView.findViewById<View>(R.id.tvSelected) as TextView
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }
        val mService_Object = arrMenu[position]
        viewHolder.mTtvName!!.setText(mService_Object.mName)
        if (mService_Object.mIsSelected) {
            viewHolder.mTvSelected!!.visibility = View.VISIBLE
        } else {
            viewHolder.mTvSelected!!.visibility = View.INVISIBLE
        }

        convertView.setOnClickListener {
            mService_Object.mIsSelected = !mService_Object.mIsSelected
            if(mService_Object.mIsSelected)
            {
                listener!!.OnClickView(mService_Object.mName.toString())
            }else{
                deletelistener!!.OnDelete(mService_Object.mName!!.toString())
            }
            notifyDataSetChanged()
        }
        return convertView
    }

    fun filter(charText: String) {
        var charText = charText
        charText = charText.toLowerCase(Locale.getDefault())
        arrMenu.clear()
        if (charText.length == 0) {

            arrMenu!!.addAll(this!!.arrayList!!)

        } else {
            for (wp in this!!.arrayList!!) {
                if (wp.mName!!.toLowerCase(Locale.getDefault()).contains(charText)) {
                    arrMenu.add(wp)
                }
            }
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder {
        internal var mTtvName: TextView? = null
        internal var mTvSelected: TextView? = null

    }
}