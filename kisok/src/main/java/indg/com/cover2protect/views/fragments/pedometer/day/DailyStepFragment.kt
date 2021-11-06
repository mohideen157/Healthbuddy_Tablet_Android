package indg.com.cover2protect.views.fragments.pedometer.day


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import android.util.Log
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.tasks.OnFailureListener
import kotlinx.android.synthetic.main.fragment_daily_step.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class DailyStepFragment : androidx.fragment.app.Fragment() {

    val TAG = "BasicHistoryApi"
    // Identifier to identify the sign in activity.
    private val REQUEST_OAUTH_REQUEST_CODE = 1
    private var data:View?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        data = inflater.inflate(indg.com.cover2protect.R.layout.fragment_daily_step, container, false)
        initgooglefit()
        return data
    }

    fun subscribe() {
        // To create a subscription, invoke the Recording API. As soon as the subscription is
        // active, fitness data will start recording.
        Fitness.getRecordingClient(this!!.activity!!, GoogleSignIn.getLastSignedInAccount(activity!!.applicationContext)!!)
                .subscribe(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.i(TAG, "Successfully subscribed!")
                        ReadData()
                    } else {
                        Log.w(TAG, "There was a problem subscribing.", task.getException())
                    }
                }
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
            subscribe()
        }
    }

    private fun ReadData() {
        Fitness.getHistoryClient(this!!.activity!!, GoogleSignIn.getLastSignedInAccount(activity!!.applicationContext)!!)
                .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener { dataSet ->
                    val total = (if (dataSet.isEmpty())
                        0
                    else
                        dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt()).toLong()

                    Log.i(TAG, "Total steps: $total")
                    readData_Calories()
                    data!!.step_line.setmValueText(total.toString())
                    data!!.wheelprogress.setStepCountText(total.toString())
                }
                .addOnFailureListener { e -> Log.w(TAG, "There was a problem getting the step count.", e) }

    }

    private fun readData_Calories() {
        Fitness.getHistoryClient(this!!.activity!!, GoogleSignIn.getLastSignedInAccount(activity!!.applicationContext)!!)
                .readDailyTotal(DataType.TYPE_CALORIES_EXPENDED)
                .addOnSuccessListener { dataSet ->
                    val total = (if (dataSet.isEmpty())
                        0
                    else
                        dataSet.dataPoints[0].getValue(Field.FIELD_CALORIES))

                    Log.i(TAG, "Total Calories: "+total.toString())
                    val data_cal = Math.round(total.toString().toDouble())
                    data!!.calories_line.setmValueText(data_cal.toString())

                }
                .addOnFailureListener(
                        object : OnFailureListener {
                            override fun onFailure(e: Exception) {
                                Log.w(TAG, "There was a problem getting the step count.", e)
                            }
                        })
    }





}
