package indg.com.cover2protect.views.activity.health_profile

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import indg.com.cover2protect.BR
import indg.com.cover2protect.R
import indg.com.cover2protect.base.BaseActivityBinding
import indg.com.cover2protect.data.marker.YourMarkerView
import indg.com.cover2protect.databinding.ActivityHhiBinding
import indg.com.cover2protect.model.hhi.hhigraph.Data
import indg.com.cover2protect.navigator.hhinavigator
import indg.com.cover2protect.viewmodel.hhi.HHIViewmodel
import kotlinx.android.synthetic.main.activity_hhi.*
import javax.inject.Inject

class HHIActivity : BaseActivityBinding<ActivityHhiBinding>(), hhinavigator {


    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.activity_hhi


    @Inject
    lateinit var viewmodel: HHIViewmodel
    private var binding: ActivityHhiBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        setSupportActionBar(toolbar)
        title = resources.getString(R.string.hhi)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp)
        toolbar.setNavigationOnClickListener { arrow -> onBackPressed() }
        viewmodel.setNavigator(this)
        getData()
    }

    private fun getData() {
        if (isNetworkConnected) {
            viewmodel.GetHHI()
        } else {
            Toast.makeText(this@HHIActivity, resources.getText(R.string.internet), Toast.LENGTH_LONG).show()

        }
    }

    override fun onSuccess(data: List<Data>) {
        if (data != null) {
            if (data.isNotEmpty()) {
                if (!data.get(0).date.isNullOrEmpty()) {
                    startdate_tv.text = data.get(0).date
                }
                if (!data.get(data.size - 1).date.isNullOrEmpty()) {
                    enddate_tv.text = data.get(data.size - 1).date
                }
                setGraph(data)
            }
        }
    }

    private fun setGraph(it: List<Data>) {
        try {
            val entries = ArrayList<Entry>()
            val entriesavg = ArrayList<Entry>()
            val entries_date = ArrayList<String>()
            for (i in it!!.indices) {
                entries.add(Entry(i.toFloat(), it[i].hhi.toFloat()))
                entriesavg.add(Entry(i.toFloat(), it[i].avg.toFloat()))
            }
            for (i in it!!.indices) {
                entries_date.add(it[i].date)
            }
            var set1 = LineDataSet(entries, resources.getString(R.string.hhi))
            val drawable = ContextCompat.getDrawable(this, R.drawable.fadeblue)
            set1.fillDrawable = drawable
            set1.lineWidth = 3f
            set1.setDrawFilled(true)
            set1.setDrawValues(false)
            set1.mode = LineDataSet.Mode.LINEAR
            set1.circleRadius = 3f
            set1.color = Color.LTGRAY
            var set2 = LineDataSet(entriesavg, "Healthy Heart Index Average")
            set2.lineWidth = 3f
            set2.setDrawFilled(true)
            set2.setDrawValues(false)
            set2.mode = LineDataSet.Mode.LINEAR
            set2.setDrawCircles(false)
            set2.color = Color.BLUE
            var data = LineData(set1, set2)
            hhi_lineChart!!.setTouchEnabled(true)
            val marker = YourMarkerView(this, R.layout.layout_marker)
            hhi_lineChart!!.marker = marker
            hhi_lineChart.animateX(1000)
            hhi_lineChart.data = data
            hhi_lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(entries_date)
        } catch (ex: Exception) {

        }
    }

    override fun onError(message: String) {
        if (message.equals("No Data")) {
            graph_ll.visibility = View.GONE
            nodate_ll.visibility = View.VISIBLE
        }
    }
}
