package indg.com.cover2protect.views.fragments.pedometer.month


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataSet
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.fitness.result.DataReadResponse
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import indg.com.cover2protect.adapter.pedometer_adapter.GoogleFit.GooglefitMonthly_Adapter
import indg.com.cover2protect.data.marker.YourMarkerView
import indg.com.cover2protect.model.googlefit.monthly_data.Update_MonthlyData
import indg.com.cover2protect.model.googlefit.monthly_data.data_month

import indg.com.cover2protect.R
import kotlinx.android.synthetic.main.fragment_monthly_step.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MonthlyStepFragment : androidx.fragment.app.Fragment() {

    val TAG = "BasicHistoryApi"
    private val REQUEST_OAUTH_REQUEST_CODE = 1
    private var dataView:View?=null
    private var arrayList = ArrayList<data_month>()
    private var data_Pedometer: Update_MonthlyData? = null
    private var pedometer_response: data_month? = null
    private var adapter:GooglefitMonthly_Adapter?=null
    private var mchart:LineChart?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        dataView =  inflater.inflate(R.layout.fragment_monthly_step, container, false)
        mchart = dataView!!.findViewById(R.id.rpw_lineChart)
        initGoogleFit()
        return dataView
    }

    private fun initGoogleFit() {
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
                .addOnSuccessListener(
                        OnSuccessListener<DataReadResponse> { dataReadResponse ->
                            // For the sake of the sample, we'll print the data so we can see what we just
                            // added. In general, logging fitness information should be avoided for privacy
                            // reasons.
                            printData(dataReadResponse)
                        })
                .addOnFailureListener(
                        OnFailureListener { e -> Log.e(TAG, "There was a problem reading the data.", e) })
    }

    private fun printData(dataReadResult: DataReadResponse?) {
        if (dataReadResult!!.buckets.size > 0) {
            Log.i(
                    TAG, "Number of returned buckets of DataSets is: " + dataReadResult.getBuckets().size)
            for (bucket in dataReadResult.buckets) {
                val dataSets = bucket.dataSets
                for (dataSet in dataSets) {
                    dumpDataSet(dataSet)
                }
            }
        } else if (dataReadResult.dataSets.size > 0) {
            Log.i(TAG, "Number of returned DataSets is: " + dataReadResult.getDataSets().size)
            for (dataSet in dataReadResult.dataSets) {
                dumpDataSet(dataSet)
            }
        }
        if(arrayList!=null){
            dataView!!.nodata_ll.visibility = View.GONE
            dataView!!.graph_ll.visibility = View.VISIBLE
            setGraph(arrayList)
            dataView!!.startdate.text = data_Pedometer!!.startdate
            dataView!!.endage.text = data_Pedometer!!.enddate
        }else{
            dataView!!.nodata_ll.visibility = View.GONE
            dataView!!.graph_ll.visibility = View.VISIBLE
        }


    }

    private fun setGraph(it: ArrayList<data_month>) {

        try{
            val entries = ArrayList<Entry>()
            val entries_date = ArrayList<String>()
            for (i in it!!.indices) {
                entries.add(Entry(i.toFloat(), it[i].steps.toFloat()))
            }
            for (i in it!!.indices) {
                entries_date.add(it[i].start_date)
            }
            var set1 = LineDataSet(entries,"FootStep")
            set1.color =  resources.getColor(R.color.fbutton_color_belize_hole)
            set1.lineWidth = 3f
            set1.setDrawFilled(true)
            set1.setDrawValues(false)
            set1.fillColor = resources.getColor(R.color.fbutton_color_belize_hole)
            set1.mode = LineDataSet.Mode.CUBIC_BEZIER
            var data = LineData(set1)
            mchart!!.setTouchEnabled(true)
            val marker = YourMarkerView(activity,R.layout.layout_marker)
            mchart!!.marker = marker
            mchart!!.animateX(1000)
            mchart!!.data = data
            mchart!!.xAxis.valueFormatter = IndexAxisValueFormatter(entries_date)
            setadapter(arrayList)
        }catch (ex:Exception){}


        setadapter(arrayList)


    }

    private fun setadapter(arrayList: ArrayList<data_month>) {

        adapter = GooglefitMonthly_Adapter(activity!!.applicationContext, arrayList)
        dataView!!.rv_weekly.adapter = adapter
    }

    private fun dumpDataSet(dataSet: DataSet) {
        Log.i(TAG, "Data returned for Data type: " + dataSet.getDataType().getName())
        val dateFormat = DateFormat.getTimeInstance()
        val cal = Calendar.getInstance()
        val now = Date()
        cal.time = now
        val endTime = cal.timeInMillis
        cal.add(Calendar.MONTH, -1)
        val startTime = cal.timeInMillis
        val df = SimpleDateFormat("EEE")
        val date_format = SimpleDateFormat("dd-MM")

        val dateFormat_new = DateFormat.getDateInstance()
        Log.i(TAG, "Range Start: " + dateFormat_new.format(startTime))
        Log.i(TAG, "Range End: " + dateFormat_new.format(endTime))

        pedometer_response = data_month()

        for (dp in dataSet.getDataPoints()) {
            pedometer_response!!.start_date = date_format.format(dp.getStartTime(TimeUnit.MILLISECONDS))
            pedometer_response!!.end_date = date_format.format(dp.getEndTime(TimeUnit.MILLISECONDS))
            Log.i(TAG, "Data point:")
            Log.i(TAG, "\tType: " + dp.getDataType().getName())
            Log.i(TAG, "\tStart: " + date_format.format(dp.getStartTime(TimeUnit.MILLISECONDS)))
            Log.i(TAG, "\tEnd: " + date_format.format(dp.getEndTime(TimeUnit.MILLISECONDS)))
            for (field in dp.getDataType().getFields()) {
                Log.i(TAG, "\tField: " + field.getName() + " Value: " + dp.getValue(field))
                pedometer_response!!.steps = dp.getValue(field).toString()

            }
            arrayList.add(pedometer_response!!)

        }
        data_Pedometer = Update_MonthlyData(arrayList, dateFormat_new.format(startTime), dateFormat_new.format(endTime))




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
        cal.add(Calendar.MONTH, -1)
        val startTime = cal.timeInMillis
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
