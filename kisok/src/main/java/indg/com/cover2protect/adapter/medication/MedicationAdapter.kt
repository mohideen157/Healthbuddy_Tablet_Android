package indg.com.cover2protect.adapter.medication

import android.content.Context
import androidx.databinding.DataBindingUtil
import android.graphics.Color
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import indg.com.cover2protect.presenter.Medicationlistener
import indg.com.cover2protect.model.medicationmodel.medicationinfo.Data
import indg.com.cover2protect.R
import indg.com.cover2protect.databinding.MedicationLayoutBinding
import java.lang.Exception

class MedicationAdapter(private var context:Context, private var response: ArrayList<Data>, var listener: indg.com.cover2protect.presenter.Medicationlistener): androidx.recyclerview.widget.RecyclerView.Adapter<MedicationAdapter.CustomViewHolder>()  {

    private var onclick: indg.com.cover2protect.presenter.Medicationlistener? = null
    private var type:String = ""
    private var dosage:String =""
    private var count: String = ""
    private var counter:Int = 0

    init {
        this.onclick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val medicationLayoutBinding: MedicationLayoutBinding = DataBindingUtil.inflate(layoutInflater, R.layout.medication_layout, parent, false)
        return CustomViewHolder(context, medicationLayoutBinding)
    }

    override fun getItemCount(): Int {
        return  response!!.size

    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder!!.medicationLayoutBinding.model = response!!.get(position)
        if(!response!![position].per_day.isNullOrEmpty()){
            holder.medicationLayoutBinding.numberButton.number = response!![position].per_day
        }
        holder!!.medicationLayoutBinding.cancel.setOnClickListener(View.OnClickListener {
            onclick!!.OnClick(response!!.get(position).value)
        })
        try{


            holder.medicationLayoutBinding.numberButton.setOnValueChangeListener { view, oldValue, newValue ->
                  count = newValue.toString()
            }


            holder.medicationLayoutBinding.save.setOnClickListener(View.OnClickListener {
                dosage = holder.medicationLayoutBinding.dosage.getText().toString()
                onclick!!.OnSubmit(type,count,dosage,response.get(position).id.toString())
            })

            if(!response!![position].type.isNullOrEmpty()){
                var typeinfo = response!![position].type
                if(typeinfo == "Syrup"){
                    type = "Syrup"
                    val colorValue = ContextCompat.getColor(context, R.color.colorAccent)
                    holder.medicationLayoutBinding.syrupBtn.buttonColor = colorValue
                    holder.medicationLayoutBinding.syrupBtn.setTextColor(Color.WHITE)
                    holder.medicationLayoutBinding.vialBtn.buttonColor = Color.LTGRAY
                    holder.medicationLayoutBinding.tabletBtn.buttonColor = Color.LTGRAY
                    holder.medicationLayoutBinding.tabletBtn.setTextColor(Color.BLACK)
                    holder.medicationLayoutBinding.vialBtn.setTextColor(Color.BLACK)
                }else if(typeinfo == "Vial"){
                    type = "Vial"
                    val colorValue = ContextCompat.getColor(context, R.color.colorAccent)
                    holder.medicationLayoutBinding.syrupBtn.buttonColor = Color.LTGRAY
                    holder.medicationLayoutBinding.syrupBtn.setTextColor(Color.BLACK)
                    holder.medicationLayoutBinding.vialBtn.buttonColor = colorValue
                    holder.medicationLayoutBinding.tabletBtn.buttonColor = Color.LTGRAY
                    holder.medicationLayoutBinding.tabletBtn.setTextColor(Color.BLACK)
                    holder.medicationLayoutBinding.vialBtn.setTextColor(Color.WHITE)
                }else if(typeinfo == "Tablet"){
                    type = "Tablet"
                    val colorValue = ContextCompat.getColor(context, R.color.colorAccent)
                    holder.medicationLayoutBinding.syrupBtn.buttonColor = Color.LTGRAY
                    holder.medicationLayoutBinding.syrupBtn.setTextColor(Color.BLACK)
                    holder.medicationLayoutBinding.vialBtn.buttonColor = Color.LTGRAY
                    holder.medicationLayoutBinding.tabletBtn.buttonColor = colorValue
                    holder.medicationLayoutBinding.tabletBtn.setTextColor(Color.WHITE)
                    holder.medicationLayoutBinding.vialBtn.setTextColor(Color.BLACK)
                }
            }

            holder.medicationLayoutBinding.syrupBtn.setOnClickListener(View.OnClickListener {
                type = "Syrup"
                val colorValue = ContextCompat.getColor(context, R.color.colorAccent)
                holder.medicationLayoutBinding.syrupBtn.buttonColor = colorValue
                holder.medicationLayoutBinding.syrupBtn.setTextColor(Color.WHITE)
                holder.medicationLayoutBinding.vialBtn.buttonColor = Color.LTGRAY
                holder.medicationLayoutBinding.tabletBtn.buttonColor = Color.LTGRAY
                holder.medicationLayoutBinding.tabletBtn.setTextColor(Color.BLACK)
                holder.medicationLayoutBinding.vialBtn.setTextColor(Color.BLACK)
            })

            holder.medicationLayoutBinding.vialBtn.setOnClickListener(View.OnClickListener {
                type = "Vial"
                val colorValue = ContextCompat.getColor(context, R.color.colorAccent)
                holder.medicationLayoutBinding.syrupBtn.buttonColor = Color.LTGRAY
                holder.medicationLayoutBinding.syrupBtn.setTextColor(Color.BLACK)
                holder.medicationLayoutBinding.vialBtn.buttonColor = colorValue
                holder.medicationLayoutBinding.tabletBtn.buttonColor = Color.LTGRAY
                holder.medicationLayoutBinding.tabletBtn.setTextColor(Color.BLACK)
                holder.medicationLayoutBinding.vialBtn.setTextColor(Color.WHITE)
            })

            holder.medicationLayoutBinding.tabletBtn.setOnClickListener(View.OnClickListener {
                type = "Tablet"
                val colorValue = ContextCompat.getColor(context, R.color.colorAccent)
                holder.medicationLayoutBinding.syrupBtn.buttonColor = Color.LTGRAY
                holder.medicationLayoutBinding.syrupBtn.setTextColor(Color.BLACK)
                holder.medicationLayoutBinding.vialBtn.buttonColor = Color.LTGRAY
                holder.medicationLayoutBinding.tabletBtn.buttonColor = colorValue
                holder.medicationLayoutBinding.tabletBtn.setTextColor(Color.WHITE)
                holder.medicationLayoutBinding.vialBtn.setTextColor(Color.BLACK)
            })


        }catch(ex:Exception){}

    }


    class CustomViewHolder(var context: Context, var medicationLayoutBinding: MedicationLayoutBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(medicationLayoutBinding.root) {

    }
}