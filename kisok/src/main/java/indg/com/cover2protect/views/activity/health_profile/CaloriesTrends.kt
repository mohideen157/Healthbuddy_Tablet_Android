package indg.com.cover2protect.views.activity.health_profile

import androidx.lifecycle.Observer
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import indg.com.cover2protect.base.BaseActivity
import indg.com.cover2protect.data.marker.YourMarkerView
import indg.com.cover2protect.model.caloriesgraphresponse.Data
import indg.com.cover2protect.navigator.TrendsNavigator
import indg.com.cover2protect.R
import indg.com.cover2protect.viewmodel.calories.CaloriesViewModel
import kotlinx.android.synthetic.main.activity_calories_trends.*
import javax.inject.Inject

class CaloriesTrends : BaseActivity(), TrendsNavigator {
    override fun OnSuccess(startdate: String, enddate: String) {
        hideLoading()
        startdate_tv.text = startdate
        enddate_tv.text = enddate
    }

    override fun OnError(message: String) {
        hideLoading()
        if (message == "No Data") {
            nodata_ll.visibility = View.VISIBLE
            main_ll.visibility = View.GONE
        }
    }


    @Inject
    lateinit var viewmodel: CaloriesViewModel
    private var mchart: LineChart? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calories_trends)
        viewmodel.setNavigator(this)
        setTitle(resources.getString(R.string.calories))
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp)
        toolbar.setNavigationOnClickListener { arrow -> onBackPressed() }
        mchart = findViewById<LineChart>(R.id.lineChart)
        getData()

    }

    private fun getData() {
        if(isNetworkConnected()){
            viewmodel.GetCalTrends().observe(this, Observer {
                if (it!!.isNotEmpty()) {
                    nodata_ll.visibility = View.GONE
                    main_ll.visibility = View.VISIBLE
                    setChart(it)

                }
            })
        }else{
            Toast.makeText(this@CaloriesTrends, resources.getText(R.string.internet), Toast.LENGTH_LONG).show()

        }
    }

    private fun setChart(it: ArrayList<Data>) {
        var yVals1 = java.util.ArrayList<Entry>()
        for (i in it.indices) {
            yVals1.add(Entry(i.toFloat(), it.get(i).calories_burned.toFloat()))
        }
        var yVals2 = java.util.ArrayList<Entry>()
        for (i in it.indices) {
            yVals2.add(Entry(i.toFloat(), it.get(i).calories_gained.toFloat()))
        }
        var yVals3 = java.util.ArrayList<Entry>()
        for (i in it.indices) {
            if (!it!![i].calories_target.isNullOrEmpty()) {
                yVals3.add(Entry(i.toFloat(), it.get(i).calories_target.toFloat()))
            } else {
                yVals3.add(Entry(i.toFloat(), 0.0F))
            }
        }
        val entries_date = java.util.ArrayList<String>()
        for (i in it!!.indices) {
            entries_date.add(it[i].date)
        }
        var set1 = LineDataSet(yVals1, "Calories Burned")
        set1.color = Color.BLUE
        set1.lineWidth = 3f
        set1.setDrawValues(false)
        set1.mode = LineDataSet.Mode.CUBIC_BEZIER

        var set2 = LineDataSet(yVals2, "Calories Gained")
        set2.color = Color.GRAY
        set2.lineWidth = 3f
        set2.setDrawValues(false)
        set2.mode = LineDataSet.Mode.CUBIC_BEZIER

        var set3 = LineDataSet(yVals3, "Calories Target")
        set3.color = Color.CYAN
        set3.lineWidth = 3f
        set3.setDrawValues(false)
        set3.mode = LineDataSet.Mode.CUBIC_BEZIER
        var data = LineData(set1, set2, set3)
        lineChart!!.setTouchEnabled(true)
        val marker = YourMarkerView(this, R.layout.layout_marker)
        lineChart!!.marker = marker
        lineChart.animateX(1000)
        lineChart.data = data
        lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(entries_date)
    }
}
