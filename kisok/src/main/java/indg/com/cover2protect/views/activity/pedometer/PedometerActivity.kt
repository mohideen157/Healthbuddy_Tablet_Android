package indg.com.cover2protect.views.activity.pedometer

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import androidx.annotation.NonNull
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import indg.com.cover2protect.R
import kotlinx.android.synthetic.main.activity_pedometer.*
import indg.com.cover2protect.views.activity.pedometer.history.HistoryActivity
import kotlinx.android.synthetic.main.content_pedometer.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import indg.com.cover2protect.BR
import indg.com.cover2protect.base.BaseActivityBinding
import indg.com.cover2protect.helper.SaveSharedPreference
import indg.com.cover2protect.databinding.ActivityPedometerBinding
import indg.com.cover2protect.model.pedometer.PedometerRequest
import indg.com.cover2protect.model.pedometer.PedometerResponse
import indg.com.cover2protect.model.pedometer.pedometerresponse.PedometerResponsedata
import indg.com.cover2protect.navigator.response_navigator
import indg.com.cover2protect.viewmodel.pedometer.PedometerViewModel
import indg.com.cover2protect.views.activity.pedometer.add_session_activity.AddActivity
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class PedometerActivity : BaseActivityBinding<ActivityPedometerBinding>(), View.OnClickListener, response_navigator {



    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.activity_pedometer


    @Inject
    lateinit var viewmodel: PedometerViewModel
    val TAG = "StepCounter"
    private val REQUEST_OAUTH_REQUEST_CODE = 0x1001
    private var pedometerRequest: PedometerRequest = PedometerRequest()
    private var selecteddate: String = ""
    private var footstep_count: String = ""
    private var Pedometer_Target: String = ""
    private var binding:ActivityPedometerBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeStatusBarColor()
        binding = viewDataBinding
        setSupportActionBar(toolbar)
        title = resources.getString(R.string.pedometer)
        viewmodel.setNavigator(this)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp)
        toolbar.setNavigationOnClickListener { arrow -> onBackPressed() }
        history_btn.setOnClickListener(this)
        initGoogleFit()
        fab2.setOnClickListener(this)
        target.setOnClickListener {
            OpenPedometerDialog()
        }


    }


    private fun OpenPedometerDialog() {
        val viewGroup = findViewById<ViewGroup>(android.R.id.content)
        var dialogView = LayoutInflater.from(this).inflate(R.layout.pedometer_dialog, viewGroup, false)
        var btn = dialogView.findViewById<Button>(R.id.cancel)
        var savebtn = dialogView.findViewById<Button>(R.id.save)
        var notes_et = dialogView.findViewById<EditText>(R.id.notes_et)
        var builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        var alertDialog = builder.create()
        btn.setOnClickListener { alertDialog.dismiss() }
        if (!Pedometer_Target.isNullOrEmpty()) {
            notes_et.setText(Pedometer_Target)
        }
        savebtn.setOnClickListener {
            if (!notes_et.text.isNullOrEmpty()) {
                setData(notes_et.text.toString())
                alertDialog.dismiss()
            } else {
                Toast.makeText(this, "Target Required", Toast.LENGTH_LONG).show()
            }
        }
        alertDialog.show()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.history_btn -> SwitchToActivity()
            R.id.fab2 -> switchtoAddActivity()
        }
    }

    private fun switchtoAddActivity() {
        startActivity(Intent(this, AddActivity::class.java))
    }

    private fun SwitchToActivity() {
        startActivity(Intent(this, HistoryActivity::class.java))
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_OAUTH_REQUEST_CODE) {
                subscribe()
            }
        }
    }



    private fun setData(toString: String) {
        val date = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        selecteddate = dateFormat.format(date)
        viewmodel.UpsertPedometer(selecteddate, toString)
    }

    private fun getPedometerdata() {
        val date = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        selecteddate = dateFormat.format(date)
        if (!selecteddate.isNullOrEmpty()) {
            viewmodel.GetPedometer(selecteddate)
        }
    }

    fun subscribe() {
        // To create a subscription, invoke the Recording API. As soon as the subscription is
        // active, fitness data will start recording.
        try {
            Fitness.getRecordingClient(this, GoogleSignIn.getLastSignedInAccount(this)!!)
                    .subscribe(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                    .addOnCompleteListener(
                            object : OnCompleteListener<Void> {
                                override fun onComplete(@NonNull task: Task<Void>) {
                                    if (task.isSuccessful()) {
                                        Log.i(TAG, "Successfully subscribed!")
                                        readData()
                                    } else {
                                        Log.w(TAG, "There was a problem subscribing.", task.getException())
                                    }
                                }
                            })
        } catch (ex: Exception) {
        }
    }

    private fun readData() {
        try {
            Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this)!!)
                    .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
                    .addOnSuccessListener { dataSet ->
                        val total = (if (dataSet.isEmpty())
                            0
                        else
                            dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt()).toLong()

                        Log.i(TAG, "Total steps: $total")
                        step_line.setmValueText(total.toString())
                        try {
                            pedometerRequest!!.steps = total.toString()
                            footstep_count = total.toString()
                            readData_Calories()
                        } catch (ex: Exception) {
                        }
                    }
                    .addOnFailureListener(
                            object : OnFailureListener {
                                override fun onFailure(e: Exception) {
                                    Log.w(TAG, "There was a problem getting the step count.", e)
                                }
                            })
        } catch (ex: Exception) {
        }
    }

    private fun readData_Calories() {
        try {
            Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this)!!)
                    .readDailyTotal(DataType.TYPE_CALORIES_EXPENDED)
                    .addOnSuccessListener { dataSet ->
                        val total = (if (dataSet.isEmpty())
                            0
                        else
                            dataSet.getDataPoints().get(0).getValue(Field.FIELD_CALORIES))


                        Log.i(TAG, "Total Calories: " + total.toString())
                        val data_cal = Math.round(total.toString().toDouble())
                        calories_line.setmValueText(data_cal.toString())
                        try {
                            pedometerRequest.calories = data_cal.toString()
                            viewmodel.PostPedometerData(pedometerRequest)
                            getPedometerdata()
                        } catch (ex: Exception) {
                        }


                    }
                    .addOnFailureListener(
                            object : OnFailureListener {
                                override fun onFailure(e: Exception) {
                                    Log.w(TAG, "There was a problem getting the step count.", e)
                                }
                            })
        } catch (ex: Exception) {
        }
    }


    private fun initGoogleFit() {
        try {
            val fitnessOptions = FitnessOptions.builder()
                    .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                    .addDataType(DataType.TYPE_STEP_COUNT_DELTA)
                    .addDataType(DataType.TYPE_CALORIES_EXPENDED)
                    .addDataType(DataType.TYPE_DISTANCE_DELTA)
                    .build()
            if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), fitnessOptions)) {
                GoogleSignIn.requestPermissions(
                        this,
                        REQUEST_OAUTH_REQUEST_CODE,
                        GoogleSignIn.getLastSignedInAccount(this),
                        fitnessOptions)

            } else {
                subscribe()
            }
        } catch (ex: Exception) {
        }

    }


    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.colorAccent)
        }
    }

    override fun onSuccess(data: Any) {
        if (data is PedometerResponse) {
            var dataval = data as PedometerResponse
            Toast.makeText(this, "Data Saved", Toast.LENGTH_LONG)
            viewmodel.GetPedometer(selecteddate)

        } else if (data is PedometerResponsedata) {
            var datavar = data as PedometerResponsedata
            Toast.makeText(this, "Goal Updated", Toast.LENGTH_LONG).show()
            viewmodel.GetPedometer(selecteddate)
        } else {
            if (!data.toString().isNullOrEmpty()) {
               if(TextUtils.isDigitsOnly(data.toString())){
                   target_pl.setmValueText(data.toString())
                   Pedometer_Target = data.toString()
                   if (!footstep_count.isNullOrEmpty()) {
                       if (footstep_count.toDouble() < Pedometer_Target.toDouble()) {
                           var percentage = ((footstep_count.toFloat() * 100) / data.toString().toFloat())
                           target_pl.setmPercentage(percentage.toInt())
                           var finalper = DecimalFormat("##.##").format(percentage)
                           progress_value.text = "$finalper %"
                           wheelprogress.progress = percentage.toInt()
                       } else {
                           var per = ((footstep_count.toFloat() / data.toString().toFloat()) * 100)
                           var finalper = DecimalFormat("##.##").format(per)
                           progress_value.text = "$finalper %"
                           target_pl.setmPercentage(100)
                           wheelprogress.progress = 100
                       }
                   }
                   if (SaveSharedPreference.getFirstTime(applicationContext)) {
                       setSelecteddata()
                       SaveSharedPreference.setFirstTime(applicationContext, false)
                   }
               }
            }
        }
    }

    private fun setSelecteddata() {
        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(findViewById(R.id.target), "Pedometer Target", "Click to Add Pedometer Target")
                        .outerCircleColor(R.color.colorAccent)
                        .outerCircleAlpha(0.96f)
                        .targetCircleColor(R.color.white)
                        .titleTextSize(20)
                        .titleTextColor(R.color.white)
                        .descriptionTextSize(10)
                        .descriptionTextColor(R.color.defaultTextColor)
                        .textColor(R.color.white)
                        .dimColor(R.color.defaultTextColor)
                        .drawShadow(true)
                        .cancelable(false)
                        .tintTarget(true)
                        .transparentTarget(false)
                        .targetRadius(60),
                object : TapTargetView.Listener() {
                    override fun onTargetClick(view: TapTargetView) {
                        super.onTargetClick(view)
                    }
                })

    }

    override fun onError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG)

    }


}

