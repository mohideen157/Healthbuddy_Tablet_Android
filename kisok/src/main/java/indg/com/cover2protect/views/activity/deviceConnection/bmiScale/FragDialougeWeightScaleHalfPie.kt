package indg.com.cover2protect.views.activity.deviceConnection.bmiScale

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.DialogFragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import indg.com.cover2protect.R
import indg.com.cover2protect.baseAeglOrbs.Base2FragmentDialouge
import indg.com.cover2protect.baseAeglOrbs.W3Obj
import indg.com.cover2protect.util.SharedPrefUtils
import kotlinx.android.synthetic.main.fragment_bmi_graph.tv_bmi_chart_bmr
import kotlinx.android.synthetic.main.fragment_bmi_graph.tv_bmi_chart_body_fat
import kotlinx.android.synthetic.main.fragment_bmi_graph.tv_bmi_chart_body_water
import kotlinx.android.synthetic.main.fragment_bmi_graph.tv_bmi_chart_bone_mass
import kotlinx.android.synthetic.main.fragment_bmi_graph.tv_bmi_chart_muscle_mass
import kotlinx.android.synthetic.main.fragment_bmi_graph.tv_bmi_chart_target_weight
import kotlinx.android.synthetic.main.fragment_bmi_graph.tv_bmi_chart_visceral_fat
import kotlinx.android.synthetic.main.fragment_bmi_graph.tv_bmi_chart_weight_current
import kotlinx.android.synthetic.main.fragment_bmi_graph.tv_bmi_chart_weight_to_lose
import kotlinx.android.synthetic.main.fragment_bmi_pi_graph.*
import kotlinx.android.synthetic.main.toolbar_normal.*
import java.text.DecimalFormat
import java.util.*


class FragDialougeWeightScaleHalfPie : Base2FragmentDialouge() {

    internal var interfaceResponse: InterfaceResponse? = null
    internal var w3Obj : W3Obj? = null
    val chartValues = ArrayList<PieEntry>()

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


        return inflater.inflate(R.layout.fragment_bmi_pi_graph, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        iv_tb_normal_back.setOnClickListener {

            dismiss()
        }

        tb_normal_title.text = "BMI Values"

        try {

            w3Obj = arguments?.get("weightData")as W3Obj


        } catch (e: Exception) {

            showToast("No data")

        }



        tv_bmi_chart_weight_current.text = w3Obj!!.WeigthKg+" Kg"

        val userTarget  =  spGetter().getString(SharedPrefUtils.USER_TARGET,"")
        tv_bmi_chart_target_weight.text = userTarget+" Kg"

        val toLoseValue = userTarget!!.toDouble() - w3Obj!!.WeigthKg.toDouble()
        var df = DecimalFormat("#.##")
        tv_bmi_chart_weight_to_lose.text = df.format(toLoseValue).toString()+" Kg"

        tv_bmi_chart_body_fat.text = w3Obj!!.BodyFat +" %"
        tv_bmi_chart_muscle_mass.text = w3Obj!!.MuscleMass +" Kg"
        tv_bmi_chart_visceral_fat.text = w3Obj!!.BMI+""
        tv_bmi_chart_body_water.text = w3Obj!!.BodyWater +" %"
        tv_bmi_chart_bone_mass.text = w3Obj!!.BoneMass+" Kg"

        tv_bmi_chart_bmr.text = w3Obj!!.BMR+" Kcal"

        val bmi = mCalcBMI(w3Obj!!.WeigthKg,spGetter().getString(SharedPrefUtils.USER_HEIGHT,"10"))
        //weight: String, height_meters: String, sex: Boolean, age: String
        val bmr = mCalcBMR(w3Obj!!.WeigthKg,
                spGetter().getString(SharedPrefUtils.USER_HEIGHT,"10")!!,
                spGetter().getBoolean(SharedPrefUtils.USER_SEX,false),
                spGetter().getString(SharedPrefUtils.USER_AGE,"10")!!)

        chartValues.add(PieEntry(w3Obj!!.WeigthKg.toFloat(),"Your Weight"))
        chartValues.add(PieEntry(w3Obj!!.BodyFat.toFloat(),"Body Fat"))
        chartValues.add(PieEntry(w3Obj!!.MuscleMass.toFloat(),"Muscle Mass"))
        chartValues.add(PieEntry(w3Obj!!.BodyWater.toFloat(),"Body Water"))
        chartValues.add(PieEntry(w3Obj!!.BoneMass.toFloat(),"Bone Mass"))
        chartValues.add(PieEntry(bmi!!.toFloat(),"BMI"))
        chartValues.add(PieEntry(bmr!!.toFloat(),"BMR"))




        val itemOnClick: (Int) -> Unit = {

            interfaceResponse!!.notificationItemSelected(it)
            dismissAllowingStateLoss()
        }

        btn_bmi_history.setOnClickListener {
            interfaceResponse!!.showHistory()
        }

        mHalfPie();

    }

    private fun mHalfPie() {


        chart.setBackgroundColor(Color.TRANSPARENT)

//        moveOffScreen()

        chart.setUsePercentValues(true)
        chart.description.isEnabled = false

        chart.isDrawHoleEnabled = true
        chart.setHoleColor(Color.TRANSPARENT)

        chart.setTransparentCircleColor(Color.WHITE)
        chart.setTransparentCircleAlpha(110)

        chart.holeRadius = 58f
        chart.transparentCircleRadius = 61f

        chart.setDrawCenterText(true)

        chart.isRotationEnabled = false
        chart.isHighlightPerTapEnabled = true

        chart.maxAngle = 360f // HALF CHART

        chart.rotationAngle = 180f
        chart.setCenterTextOffset(0f, -20f)

        setData(4, 100f)

        chart.animateY(1400, Easing.EaseInOutQuad)

        val l = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.xEntrySpace = 7f
        l.yEntrySpace = 0f
        l.yOffset = 0f

        // entry label styling
        // entry label styling
        chart.setEntryLabelColor(Color.WHITE)
        chart.setEntryLabelTextSize(0f)

    }

    private fun moveOffScreen() {
        val displayMetrics = DisplayMetrics()
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val offset = (height * 0.65).toInt() /* percent to move */
        val rlParams = chart.layoutParams as RelativeLayout.LayoutParams
        rlParams.setMargins(0, 0, 0, -offset)
        chart.layoutParams = rlParams
    }

    private fun setData(count: Int, range: Float) {

        val dataSet = PieDataSet(chartValues, "BMI readings")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        dataSet.setColors(*ColorTemplate.MATERIAL_COLORS)
        //dataSet.setSelectionShift(0f);
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        chart.data = data
        chart.invalidate()
    }


    //	private TextView showConnectMsgTextView;
    var heightStr = "0"
    var ageStr = "0"
    var sexUser: Boolean = false





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
        fun showHistory()

    }





}
