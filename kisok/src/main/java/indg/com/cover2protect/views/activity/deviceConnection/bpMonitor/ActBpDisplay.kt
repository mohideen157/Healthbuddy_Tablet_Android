package indg.com.cover2protect.views.activity.deviceConnection.bpMonitor

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import butterknife.ButterKnife
import indg.com.cover2protect.DB.DBhelper
import com.getmedcheck.lib.MedCheckActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import indg.com.cover2protect.R
import indg.com.cover2protect.baseAeglOrbs.BpObj
import indg.com.cover2protect.views.activity.deviceConnection.Adapter.RViewAdapterBpHistory
import kotlinx.android.synthetic.main.activity_bp_graphs.*
import java.util.ArrayList

import kotlin.math.roundToInt


class ActBpDisplay : MedCheckActivity(),
        BottomNavigationView.OnNavigationItemSelectedListener  {
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

        private val dbHelper = DBhelper(this)
        val listHistory = ArrayList<BpObj>()
        val listPulse = arrayListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bp_graphs)
        ButterKnife.bind(this)

        val tv_title = findViewById<TextView>(R.id.tb_normal_title)
        tv_title.text = "Your Blood Pressure Data"

        val ib_back = findViewById<ImageButton>(R.id.iv_tb_normal_back)
        ib_back.setOnClickListener { v -> finish() }

        swipe_refresh_bp.setOnRefreshListener {
            swipe_refresh_bp.isRefreshing = false
        }

        btn_take_new_reading.setOnClickListener {

            val bpScannerAct = Intent(this,ActBpChooseDevice::class.java);
            startActivity(bpScannerAct)
            finish()

        }

    }


    override fun onStart() {
        super.onStart()

        recyclerview_bp.apply {

            layoutManager = GridLayoutManager(this@ActBpDisplay, 1)
            adapter = RViewAdapterBpHistory(listHistory, context)

        }

        getDBDataAll()

    }

    private fun mUpdateDatas() {


        try {
            val bpObj: BpObj = intent.getSerializableExtra("BpHistoric") as BpObj

            tvBPvalue.setText(bpObj.DIA_mmHg+"/"+bpObj.SYS_mmHg)
            tvPulse.text = bpObj.PUL
            tv_heart_beat.text = bpObj.PUL
        } catch (e: Exception) {
        }


    }

     private fun getDBDataAll() {

        val res = dbHelper.allData
        if (res.count == 0) {

            mUpdateDatas()

            listHistory.clear()

            /*listHistory.add(BpObj("137",
                    "87",
                    "91",
                    "",
                    "Sample"))

            listHistory.add(BpObj("141",
                    "126",
                    "93",
                    "",
                    "Sample"))

            listHistory.add(BpObj("104",
                    "87",
                    "101",
                    "",
                    "Sample"))

            listHistory.add(BpObj("106",
                    "82",
                    "88",
                    "",
                    "Sample"))*/

            recyclerview_bp.adapter!!.notifyDataSetChanged()


            return
        }

       // val buffer = StringBuffer()
        while (res.moveToNext()) {


            if(intent.getSerializableExtra("BpHistoric")== null){

                val bpObj = BpObj(res.getString(1),
                        res.getString(2),
                        res.getString(3),
                        res.getString(4),
                        res.getString(5))

                listHistory.add(bpObj)

                listPulse.add(res.getString(3).toInt())
                tvBPvalue.text = res.getString(1).toInt().toString()+" / "+res.getString(2).toInt().toString()
                spark_line_pressure_graph.invalidate()
                spark_line_pressure_graph.setData(listPulse)
                try {
                    tvPulse.text = listPulse.average().roundToInt().toString()
                } catch (e: Exception) {
                }
                try {
                    tv_heart_beat.text = listPulse.average().roundToInt().toString()
                } catch (e: Exception) {
                }

            }else{
                spark_line_pressure_graph.visibility = View.GONE

                val bpObj :BpObj = intent.getSerializableExtra("BpHistoric") as BpObj
                tvBPvalue.text = bpObj.SYS_mmHg+" / "+bpObj.DIA_mmHg
                tvPulse.text = bpObj.PUL
                tv_heart_beat.text = bpObj.PUL

                val bpObj2 = BpObj(res.getString(1),
                        res.getString(2),
                        res.getString(3),
                        res.getString(4),
                        res.getString(5))

                listHistory.add(bpObj2)

            }


        }

        recyclerview_bp.adapter?.notifyDataSetChanged()

    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()


    }


}
