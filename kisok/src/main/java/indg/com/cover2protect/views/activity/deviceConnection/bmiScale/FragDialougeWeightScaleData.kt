package indg.com.cover2protect.views.activity.deviceConnection.bmiScale

import android.app.Dialog
import android.content.Context
import android.database.Cursor
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import indg.com.cover2protect.DB.DBHelper2
import indg.com.cover2protect.R
import indg.com.cover2protect.baseAeglOrbs.Base2FragmentDialouge
import indg.com.cover2protect.baseAeglOrbs.W3Obj
import indg.com.cover2protect.util.SharedPrefUtils
import kotlinx.android.synthetic.main.fragment_bmi_graph.*
import kotlinx.android.synthetic.main.toolbar_normal.*
import java.text.DecimalFormat
import java.util.*


class FragDialougeWeightScaleData : Base2FragmentDialouge() {

    internal var interfaceResponse: InterfaceResponse? = null
    internal var w3Obj : W3Obj? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is InterfaceResponse) {
            interfaceResponse = context
        } else {
            throw RuntimeException("$context must implement InterfaceSelectListItemInteractionListener") as Throwable
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog((activity as Context?)!!, theme) {
            override fun onBackPressed() {
              dismiss()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppTheme)

    }


  lateinit var w3 :  W3Obj
  var listBMI = ArrayList<W3Obj>()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.fragment_bmi_graph, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        iv_tb_normal_back.setOnClickListener {

            dismiss()
        }

        tb_normal_title.text = "Historic values"


        mLoadGraphData();


        val itemOnClick: (Int) -> Unit = {

            interfaceResponse!!.notificationItemSelected(it)
            dismissAllowingStateLoss()
        }



    }

    //	private TextView showConnectMsgTextView;
    var heightStr = "0"
    var ageStr = "0"
    var sexUser: Boolean = false

    private fun mLoadGraphData() {


        heightStr = spGetter().getString(SharedPrefUtils.USER_HEIGHT, "").toString()
        sexUser = spGetter().getBoolean(SharedPrefUtils.USER_SEX, false)
        ageStr = spGetter().getString(SharedPrefUtils.USER_AGE, "").toString()


        val sqliteDBHelper2  = DBHelper2(activity)

        val cursor: Cursor = sqliteDBHelper2.allBMIData



        if (cursor.count == 0) {

           /* w3 = W3Obj("64", "18.11", "15.2", "48.7",
                    "0.0", "63.4", "2.9", "680", "60",
                    "", "sample Reading", "7")

            listBMI.add(w3)

            w3 = W3Obj("63", "18.9", "14.2", "48.7",
                    "0.0", "62.4", "2.9", "690", "60",
                    "", "sample Reading", "7")

            listBMI.add(w3)

            w3 = W3Obj("65", "19", "16.2", "48.7",
                    "0.0", "64.4", "2.9", "700", "60",
                    "", "sample Reading", "7")

            w3 = W3Obj("64", "18.11", "15.2", "48.7",
                    "0.0", "63.4", "2.9", "680", "60",
                    "", "sample Reading", "7")

            listBMI.add(w3)

            w3 = W3Obj("63", "18.9", "14.2", "48.7",
                    "0.0", "62.4", "2.9", "690", "60",
                    "", "sample Reading", "7")

            listBMI.add(w3)

            w3 = W3Obj("65", "19", "16.2", "48.7",
                    "0.0", "64.4", "2.9", "700", "60",
                    "", "sample Reading", "7")

            w3 = W3Obj("64", "18.11", "15.2", "48.7",
                    "0.0", "63.4", "2.9", "680", "60",
                    "", "sample Reading", "7")

            listBMI.add(w3)

            w3 = W3Obj("63", "18.9", "14.2", "48.7",
                    "0.0", "62.4", "2.9", "690", "60",
                    "", "sample Reading", "7")

            listBMI.add(w3)

            w3 = W3Obj("65", "19", "16.2", "48.7",
                    "0.0", "64.4", "2.9", "700", "60",
                    "", "sample Reading", "7")

            listBMI.add(w3)*/


        }else{

            listBMI.clear()

            var iteratio = 0

        cursor.use { cursor ->


            while (cursor.moveToNext()) {

                iteratio++

                Log.d("date->"+iteratio,cursor.getString(11) + "")

             val  w32 = W3Obj(cursor.getString(1) + "",
                         mCalcBMI(cursor.getString(1), heightStr).toString(),
                        cursor.getString(3) + "",
                        cursor.getString(4) + "",
                        cursor.getString(5) + "",
                        cursor.getString(6) + "",
                        cursor.getString(7) + "",
                         mCalcBMR(cursor.getString(1), heightStr, sexUser, ageStr).toString(),
                        cursor.getString(9) + "",
                        cursor.getString(10) + "",
                        cursor.getString(11) + "",
                        cursor.getString(12) + "")


                if (cursor.getString(1).trim { it <= ' ' }.isNotEmpty()) {

                    listBMI.add(w32)

                }

            }
        }

        }

        mGrapModule()

    }

    private fun mGrapModule() {

        chart_bmi.animateX(500)

        // get the legend (only possible after setting data)
        val l = chart_bmi.legend

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE)
        l.setTextSize(11f)
        l.setTextColor(Color.WHITE)
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM)
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT)
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL)
        l.setDrawInside(false)

        val xAxis = chart_bmi.getXAxis()

        xAxis.setTextSize(11f)
        xAxis.setTextColor(Color.WHITE)
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        val leftAxis = chart_bmi.getAxisLeft()

        leftAxis.setTextColor(ColorTemplate.getHoloBlue())
        leftAxis.setAxisMaximum(100f)
        leftAxis.setAxisMinimum(0f)
        leftAxis.setDrawGridLines(true)
        leftAxis.setGranularityEnabled(true)

        leftAxis.isEnabled = false


        val rightAxis = chart_bmi.getAxisRight()

        rightAxis.setTextColor(Color.RED)
        rightAxis.setAxisMaximum(100f)
        rightAxis.setAxisMinimum(0f)
        rightAxis.setDrawGridLines(false)
        rightAxis.setDrawZeroLine(false)
        rightAxis.setGranularityEnabled(false)

        rightAxis.isEnabled = false

        chart_bmi.setPadding(20,0,0,20)


        val set1: LineDataSet
        val set2: LineDataSet
        val set3: LineDataSet


        val values1 = ArrayList<Entry>()

        for (i in 0 until listBMI.size) {
            val weightFat: Float = listBMI.get(i).BodyFat.toFloat()
            values1.add(Entry(i.toFloat(), weightFat))
        }

        val values2 = ArrayList<Entry>()

        for (i in 0 until listBMI.size) {
            val bmrFloat: Float = listBMI.get(i).MuscleMass.toFloat()
            values2.add(Entry(i.toFloat(), bmrFloat))
        }

        val values3 = ArrayList<Entry>()

        for (i in 0 until listBMI.size) {
            val weightFloat: Float = listBMI.get(i).WeigthKg.toFloat()
            values3.add(Entry(i.toFloat(), weightFloat))
        }




        if (chart_bmi.getData() != null && chart_bmi.getData().getDataSetCount() > 0) {
            set1 = chart_bmi.getData().getDataSetByIndex(0) as LineDataSet
            set2 = chart_bmi.getData().getDataSetByIndex(1) as LineDataSet
            set3 = chart_bmi.getData().getDataSetByIndex(2) as LineDataSet
            set1.values = values1
            set2.values = values2
            set3.values = values3
            chart_bmi.getData().notifyDataChanged()
            chart_bmi.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values1, "Body Fat")

            set1.axisDependency = YAxis.AxisDependency.LEFT
            set1.color = ColorTemplate.getHoloBlue()
            set1.setCircleColor(Color.WHITE)
            set1.lineWidth = 4f
            set1.circleRadius = 3f
            set1.fillAlpha = 65
            set1.fillColor = ColorTemplate.getHoloBlue()
            set1.highLightColor = Color.rgb(244, 117, 117)
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            set1.setDrawCircleHole(false)

            // create a dataset and give it a type
            set2 = LineDataSet(values2, "Muscle Mass")
            set2.axisDependency = YAxis.AxisDependency.RIGHT
            set2.color = Color.RED
            set2.setCircleColor(Color.WHITE)
            set2.lineWidth = 4f
            set2.circleRadius = 3f
            set2.fillAlpha = 65
            set2.setDrawCircles(false)
            set2.fillColor = Color.RED
            set2.highLightColor = Color.rgb(244, 117, 117)

            set2.setDrawCircleHole(false)
            //set2.setFillFormatter(new MyFillFormatter(900f));

            set3 = LineDataSet(values3, "Weight")
            set3.axisDependency = YAxis.AxisDependency.RIGHT
            set3.color = Color.YELLOW
            set3.setCircleColor(Color.WHITE)
            set3.lineWidth = 4f
            set3.circleRadius = 3f
            set3.fillAlpha = 65
            set3.fillColor = ColorTemplate.colorWithAlpha(Color.YELLOW, 200)
            set3.setDrawCircleHole(false)
            set3.highLightColor = Color.rgb(244, 117, 117)

            set3.setDrawCircles(false)

            // create a data object with the data sets
            val data = LineData(set1, set2, set3)
            data.setValueTextColor(Color.WHITE)
            data.setValueTextSize(12f)

            // set data
            chart_bmi.data = data
        }


    }


    private fun mCalcBMI(weight: String, height_meters: String?): String? {
        var bmi = 0.0
        var weightValue = 0.0
        var heightCm = 0.0
        try {
            weightValue = weight.toDouble()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        try {
            heightCm = height_meters!!.toDouble()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        heightCm = heightCm / 100
        bmi = weightValue / (heightCm * 2)
        val df = DecimalFormat("#.##")
        return "" + df.format(bmi)
    }

    private fun mCalcBMR(weight: String, height_meters: String, sex: Boolean, age: String): String? {
        return if (!sex) { /* Men: (88.3 + (13.4 x weight*) + (4.8 x height*) - (5.7 x age in years))*/
            var bmr = 0.0
            var weightValue = 0.0
            var heightCm = 0.0
            var ageVal = 0.0
            try {
                weightValue = weight.toDouble()
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
            try {
                heightCm = height_meters.toDouble()
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
            heightCm = heightCm / 100 // to M
            try {
                ageVal = age.toDouble()
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
            bmr = weightValue * 13.4 + 88.3 / (heightCm * 4.8) - 5.7 * ageVal
            val df = DecimalFormat("#")
            "" + df.format(bmr)
        } else { //BMR Women: = (447.6 + (9.2 x weight*) + (3.1 x height*) - (4.3 x age in years))
            var bmr = 0.0
            var weightValue = 0.0
            var heightCm = 0.0
            var ageVal = 0.0
            try {
                weightValue = weight.toDouble()
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
            try {
                heightCm = height_meters.toDouble()
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
            heightCm = heightCm / 100 // to M
            try {
                ageVal = age.toDouble()
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
            bmr = weightValue * 9.2 + 447.6 / (heightCm * 3.1) - 4.3 * ageVal
            val df = DecimalFormat("#")
            "" + df.format(bmr)
        }
    }

    interface InterfaceResponse {

        fun notificationItemSelected(it: Int)

    }





}
