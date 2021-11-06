package indg.com.cover2protect.views.fragments.pedometer.week


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataSet
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.fitness.result.DataReadResponse

import indg.com.cover2protect.R
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import com.google.android.gms.fitness.Fitness
import android.app.Activity
import android.content.Intent
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import indg.com.cover2protect.adapter.pedometer_adapter.GoogleFit.GooglefitWeeklyAdapter
import indg.com.cover2protect.data.marker.YourMarkerView
import indg.com.cover2protect.model.googlefit.GetPedometerRes
import indg.com.cover2protect.model.googlefit.PedometerResponse
import kotlinx.android.synthetic.main.fragment_weekly_step.view.*
import kotlin.collections.ArrayList
import java.text.SimpleDateFormat


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class WeeklyStepFragment : androidx.fragment.app.Fragment() {

    val TAG = "BasicHistoryApi"
    private var dataview: View? = null
    private val REQUEST_OAUTH_REQUEST_CODE = 1
    private var arrayList = ArrayList<PedometerResponse>()
    private var data_Pedometer: GetPedometerRes? = null
    private var pedometer_response: PedometerResponse? = null
    private var mValueLineChart: LineChart? = null
    private var adapter:GooglefitWeeklyAdapter?=null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        dataview = inflater.inflate(R.layout.fragment_weekly_step, container, false)
        mValueLineChart = dataview!!.findViewById<LineChart>(R.id.rpw_lineChart)
        initgooglefit()
        return dataview
    }

    private fun setGraph(it: ArrayList<PedometerResponse>) {
        try{
            val entries = ArrayList<Entry>()
            val entries_date = ArrayList<String>()
            for (i in it!!.indices) {
                entries.add(Entry(i.toFloat(), it[i].steps.toFloat()))
            }
            for (i in it!!.indices) {
                entries_date.add(it!![i].start)
            }
            var set1 = LineDataSet(entries,"FootStep")
            set1.color =  resources.getColor(R.color.fbutton_color_belize_hole)
            set1.lineWidth = 3f
            set1.setDrawFilled(true)
            set1.setDrawValues(false)
            set1.fillColor = resources.getColor(R.color.fbutton_color_belize_hole)
            set1.mode = LineDataSet.Mode.CUBIC_BEZIER
            var data = LineData(set1)
            mValueLineChart!!.setTouchEnabled(true)
            val marker = YourMarkerView(activity,R.layout.layout_marker)
            mValueLineChart!!.marker = marker
            mValueLineChart!!.animateX(1000)
            mValueLineChart!!.data = data
            mValueLineChart!!.xAxis.valueFormatter = IndexAxisValueFormatter(entries_date)
            setadapter(arrayList)
        }catch (ex:Exception){}
    }

    private fun setadapter(arrayList: ArrayList<PedometerResponse>) {
        adapter = GooglefitWeeklyAdapter(activity!!.applicationContext, arrayList)
        dataview!!.rv_weekly.adapter = adapter
    }

    private fun initgooglefit() {
        val fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_WRITE)
                .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_WRITE)
                .build()
        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(activity!!.applicationContext), fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    this,
                    REQUEST_OAUTH_REQUEST_CODE,
                    GoogleSignIn.getLastSignedInAccount(activity!!.applicationContext),
                    fitnessOptions)
        } else {
            readdata()
        }
    }

    private fun readdata() {
        val readRequest = queryFitnessData()
        Fitness.getHistoryClient(this!!.activity!!, GoogleSignIn.getLastSignedInAccount(activity!!.applicationContext)!!)
                .readData(readRequest)
                .addOnSuccessListener { dataReadResponse ->
                    // For the sake of the sample, we'll print the data so we can see what we just
                    // added. In general, logging fitness information should be avoided for privacy
                    // reasons.
                    printData(dataReadResponse)
                }
                .addOnFailureListener { e -> Log.e(TAG, "There was a problem reading the data.", e) }
    }

    private fun printData(dataReadResult: DataReadResponse?) {
        if (dataReadResult!!.getBuckets().size > 0) {
            Log.i(
                    TAG, "Number of returned buckets of DataSets is: " + dataReadResult.getBuckets().size)
            for (bucket in dataReadResult.getBuckets()) {
                val dataSets = bucket.getDataSets()
                for (dataSet in dataSets) {
                    dumpDataSet(dataSet)
                }
            }
        } else if (dataReadResult.getDataSets().size > 0) {
            Log.i(TAG, "Number of returned DataSets is: " + dataReadResult.getDataSets().size)
            for (dataSet in dataReadResult.getDataSets()) {
                dumpDataSet(dataSet)
            }
        }
        if(arrayList!=null){
            dataview!!.graph_ll.visibility = View.VISIBLE
            dataview!!.nodate_ll.visibility = View.GONE
            setGraph(arrayList)
            dataview!!.startdate.setText(data_Pedometer!!.startdate)
            dataview!!.endage.setText(data_Pedometer!!.enddate)
        }else{
            dataview!!.graph_ll.visibility = View.GONE
            dataview!!.nodate_ll.visibility = View.VISIBLE
        }
    }

    private fun dumpDataSet(dataSet: DataSet) {
        Log.i(TAG, "Data returned for Data type: " + dataSet.getDataType().getName())
        val dateFormat = DateFormat.getTimeInstance()
        val cal = Calendar.getInstance()
        val now = Date()
        cal.time = now
        val endTime = cal.timeInMillis
        cal.add(Calendar.WEEK_OF_YEAR, -1)
        val startTime = cal.timeInMillis
        val df = SimpleDateFormat("EEE")
        val date_format = SimpleDateFormat("dd-MM")

        val dateFormat_new = DateFormat.getDateInstance()
        Log.i(TAG, "Range Start: " + dateFormat_new.format(startTime))
        Log.i(TAG, "Range End: " + dateFormat_new.format(endTime))

        pedometer_response = PedometerResponse()

        for (dp in dataSet.getDataPoints()) {
            pedometer_response!!.start = date_format.format(dp.getStartTime(TimeUnit.MILLISECONDS))
            pedometer_response!!.end = date_format.format(dp.getEndTime(TimeUnit.MILLISECONDS))
            Log.i(TAG, "Data point:")
            Log.i(TAG, "\tType: " + dp.getDataType().getName())
            Log.i(TAG, "\tStart: " + df.format(dp.getStartTime(TimeUnit.MILLISECONDS)))
            Log.i(TAG, "\tEnd: " + df.format(dp.getEndTime(TimeUnit.MILLISECONDS)))
            for (field in dp.getDataType().getFields()) {
                Log.i(TAG, "\tField: " + field.getName() + " Value: " + dp.getValue(field))
                pedometer_response!!.steps = dp.getValue(field).toString()
            }
            arrayList.add(pedometer_response!!)
        }

        data_Pedometer = GetPedometerRes(arrayList, dateFormat_new.format(startTime), dateFormat_new.format(endTime))



    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_OAUTH_REQUEST_CODE) {
                readdata()
            }
        }
    }

    fun queryFitnessData(): DataReadRequest {
        // [START build_read_data_request]
        // Setting a start and end date using a range of 1 week before this moment.
        val cal = Calendar.getInstance()
        val now = Date()
        cal.time = now
        val endTime = cal.getTimeInMillis()
        cal.add(Calendar.WEEK_OF_YEAR, -1)
        val startTime = cal.getTimeInMillis()
        val dateFormat = DateFormat.getDateInstance()
        Log.i(TAG, "Range Start: " + dateFormat.format(startTime))
        Log.i(TAG, "Range End: " + dateFormat.format(endTime))

// [END build_read_data_request]

        return DataReadRequest.Builder()
                // The data request can specify multiple data types to return, effectively
                // combining multiple data queries into one call.
                // In this example, it's very unlikely that the request is for several hundred
                // datapoints each consisting of a few steps and a timestamp.  The more likely
                // scenario is wanting to see how many steps were walked per day, for 7 days.
                .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                // Analogous to a "Group By" in SQL, defines how data should be aggregated.
                // bucketByTime allows for a time span, whereas bucketBySession would allow
                // bucketing by "sessions", which would need to be defined in code.
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build()
    }


}
