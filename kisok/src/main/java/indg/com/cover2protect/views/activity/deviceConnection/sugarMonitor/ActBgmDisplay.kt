package com.getmedcheck.activity.sugarMonitor

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import butterknife.ButterKnife

import indg.com.cover2protect.DB.DBhelper
import com.getmedcheck.lib.MedCheckActivity

import com.google.android.material.bottomnavigation.BottomNavigationView
import indg.com.cover2protect.R
import indg.com.cover2protect.baseAeglOrbs.ObjGlucose
import indg.com.cover2protect.views.activity.deviceConnection.Adapter.RViewAdapterGlucoseHistory
import kotlinx.android.synthetic.main.activity_glucose_reading.*
import kotlinx.android.synthetic.main.fragment_glucose.*
import kotlinx.android.synthetic.main.toolbar_normal.*
import java.util.ArrayList


class ActBgmDisplay : MedCheckActivity(),
        BottomNavigationView.OnNavigationItemSelectedListener  {

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val dbHelper = DBhelper(this)
    lateinit var glucoseObj : ObjGlucose
    val listHistory = ArrayList<ObjGlucose>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glucose_reading)
        ButterKnife.bind(this)


        val ib_back = findViewById<ImageButton>(R.id.iv_tb_normal_back)
        ib_back.setOnClickListener { v -> finish() }

        btn_new_reading.setOnClickListener {
            finish()
        }


        listHistory.clear()


        rv_glucose_history.apply {

            layoutManager = GridLayoutManager(this@ActBgmDisplay, 1)
            adapter = RViewAdapterGlucoseHistory(listHistory, context)

        }

    }

    override fun onStart() {
        super.onStart()

        getDBDataAll()

        tb_normal_title.setText("Blood Glucose Reading")
    }

    private fun mGetSample() {

        if(intent.getSerializableExtra("GlucoseHistoric") != null){
            glucoseObj = intent.getSerializableExtra("GlucoseHistoric") as ObjGlucose

            tv_glucose_reading.text = glucoseObj.mmol

            var glucoseValue = glucoseObj.mmol.toFloat()

            if(glucoseValue < 6.3 ){

                tv_glucose_low.setTextColor(getResources().getColor(R.color.md_red_400))



            }else if(glucoseValue > 6.4 && glucoseValue < 12 ){

                tv_glucose_normal.setTextColor(getResources().getColor(R.color.md_red_500))


            }else{

                tv_glucose_high.setTextColor(getResources().getColor(R.color.md_red_300))

            }



            tv_max_mg.text = glucoseObj.High
            tv_min_mg.text = glucoseObj.Low

            tv_type.text = glucoseObj.Type
        }
    }


     private fun getDBDataAll() {

        val res = dbHelper.allBGMData
        if (res.count == 0) {

            listHistory.clear()
           /* listHistory.add(ObjGlucose(  "Sample Data","2.8","51","0","Random"))
            listHistory.add(ObjGlucose(  "Sample Data","2.4","51","0","Fasting"))
            listHistory.add(ObjGlucose(  "Sample Data","2.6","51","0","PP"))
*/
            rv_glucose_history.adapter?.notifyDataSetChanged()

            return
        }


        while (res.moveToNext()) {

            if(intent.getSerializableExtra("GlucoseHistoric") == null){

            tv_glucose_reading.text = res.getString(2)
            tv_max_mg.text = res.getString(3)
            tv_min_mg.text = res.getString(4)
            tv_type.text = res.getString(5)

            }else{

                glucoseObj = intent.getSerializableExtra("GlucoseHistoric") as ObjGlucose

                tv_glucose_reading.text = glucoseObj.mmol
                tv_max_mg.text = glucoseObj.High
                tv_min_mg.text = glucoseObj.Low
                tv_type.text = glucoseObj.Type


                var glucoseValue = glucoseObj.mmol.toFloat()

                if(glucoseValue < 6.3 ){

                    tv_glucose_low.setTextColor(getResources().getColor(R.color.md_green_300))

                }else if(glucoseValue > 6.4 && glucoseValue < 12 ){

                    tv_glucose_normal.setTextColor(getResources().getColor(R.color.md_yellow_600))

                }else{

                    tv_glucose_high.setTextColor(getResources().getColor(R.color.md_red_500))

                }

            }
            /*var Date : String, var mmol : String,var High : String,
            var Low : String,var Type : String*/
            listHistory.add(ObjGlucose(res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    res.getString(5)
            ))


        }

         rv_glucose_history.adapter?.notifyDataSetChanged()

    }


}
