package indg.com.cover2protect.views.activity.pedometer.add_session_activity

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import indg.com.cover2protect.R
import kotlinx.android.synthetic.main.activity_add.*
import android.app.DatePickerDialog
import android.widget.DatePicker
import indg.com.cover2protect.util.material_text_view.MaterialTextView
import java.util.*
import java.text.SimpleDateFormat
import android.app.TimePickerDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.FitnessOptions
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.annotation.NonNull
import com.google.android.material.snackbar.Snackbar
import androidx.core.app.ActivityCompat
import android.util.Log
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessActivities
import com.google.android.gms.fitness.data.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.fitness.request.SessionInsertRequest
import com.google.android.gms.tasks.Task
import indg.com.cover2protect.BuildConfig
import indg.com.cover2protect.data.errordialog.Sneaker
import java.util.concurrent.TimeUnit


class AddActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, View.OnClickListener {

    val myCalendar = Calendar.getInstance()
    var mHour: Int = 0
    var mMinute: Int = 0
    var mHour1: Int = 0
    var mMinute1: Int = 0
    private var current_date:String=""
    private var time_start:String=""
    private var time_end:String=""
    private var startTime:String=""
    private var endTime:String=""
    val TAG = "BasicSessions"
    var SAMPLE_SESSION_NAME:String=""
    private val DATE_FORMAT = "yyyy.MM.dd HH:mm:ss"
    var stepCountDelta:String=""
    private var description:String=""

    private val REQUEST_OAUTH_REQUEST_CODE = 1
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 34

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.submit -> InsertActivity()
        }
    }

    private fun InsertActivity() {
        if(!steps.text.isNullOrEmpty()){
            if(!notes.text.isNullOrEmpty()){
                if(time_start.isNullOrEmpty() || time_end.isNullOrEmpty() || current_date.isNullOrEmpty()){
                    showToast("Please Select Date and Time")
                }else{
                    startTime = GetTimeinMill(time_start)
                    endTime = GetTimeinMill(time_end)
                    description = notes.text.toString()
                    stepCountDelta = steps.text.toString()
                    if (hasRuntimePermissions()) {
                        insertAndVerifySessionWrapper()
                    } else {
                        requestRuntimePermissions()
                    }
                }

            }else{
                showToast("Enter Description")

            }

        }else{
            showToast("Enter Steps")
        }

    }


    private fun GetTimeinMill(time:String):String{
        var  dateInString = current_date+" "+time
        var  format_sdf = SimpleDateFormat("dd-M-yyyy hh:mm");
        var  date = format_sdf.parse(dateInString)
        return  date.time.toString()

    }


    private fun OpendatePicker() {


        val date = object : DatePickerDialog.OnDateSetListener {

            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabel()
            }

        }
        DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun updateLabel() {
        val myFormat = "dd-M-yyyy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        date_tv.setContentText(sdf.format(myCalendar.getTime()), MaterialTextView.ANIMATE_TYPE.NONE)
        current_date = sdf.format(myCalendar.time)
    }


    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        SAMPLE_SESSION_NAME = spinner_activity.getItemAtPosition(position).toString()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeStatusBarColor()
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar)
        setTitle(resources.getString(R.string.AddSteps))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp)
        toolbar.setNavigationOnClickListener { arrow -> onBackPressed() }
        spinner_activity.setOnItemSelectedListener(this)
        date_tv.setOnClickListener(View.OnClickListener { OpendatePicker() })
        starttime_tv.setOnClickListener(View.OnClickListener { OpenTimePicker() })
        endtime_tv.setOnClickListener(View.OnClickListener { OpenNewTimePicker() })
        submit.setOnClickListener(this)


    }

    private fun showToast(it: String) {
        if (it != null) {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                    .setTitle("Error")
                    .setMessage(it)
                    .sneakError()
        }    }

    private fun showSuccess(it: String) {
        if (it != null) {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                    .setTitle("Success")
                    .setMessage(it)
                    .sneakSuccess()
        }    }

    private fun requestRuntimePermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")
            Snackbar.make(
                    findViewById(R.id.main_activity_view),
                    R.string.permission_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, object : View.OnClickListener {
                        override fun onClick(view: View) {
                            // Request permission
                            ActivityCompat.requestPermissions(this@AddActivity,
                                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                    REQUEST_PERMISSIONS_REQUEST_CODE)
                        }
                    })
                    .show()
        } else {
            Log.i(TAG, "Requesting permission")
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(this@AddActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSIONS_REQUEST_CODE)
        }
    }


    private fun hasRuntimePermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        return permissionState == PackageManager.PERMISSION_GRANTED
    }


    private fun insertAndVerifySessionWrapper() {
        if (hasOAuthPermission()) {
            insertAndVerifySession()
        } else {
            requestOAuthPermission()
        }
    }

    private fun hasOAuthPermission(): Boolean {
        val fitnessOptions = getFitnessSignInOptions()
        return GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), fitnessOptions)
    }

    private fun requestOAuthPermission() {
        val fitnessOptions = getFitnessSignInOptions()
        GoogleSignIn.requestPermissions(
                this,
                REQUEST_OAUTH_REQUEST_CODE,
                GoogleSignIn.getLastSignedInAccount(this),
                fitnessOptions)
    }

    private fun getFitnessSignInOptions(): FitnessOptions {
        return FitnessOptions.builder()
                .addDataType(DataType.TYPE_ACTIVITY_SEGMENT, FitnessOptions.ACCESS_WRITE)
                .addDataType(DataType.TYPE_SPEED, FitnessOptions.ACCESS_WRITE)
                .build()
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>,
                                            @NonNull grantResults: IntArray) {
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.size <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.")
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted.
                insertAndVerifySessionWrapper()

            } else {
                Snackbar.make(
                        findViewById(R.id.main_activity_view),
                        R.string.permission_denied_explanation,
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.settings, object : View.OnClickListener {
                            override fun onClick(view: View) {
                                // Build intent that displays the App settings screen.
                                val intent = Intent()
                                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                val uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null)
                                intent.data = uri
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                            }
                        })
                        .show()
            }
        }
    }


    private fun insertAndVerifySession() {

        insertSession()

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_OAUTH_REQUEST_CODE) {
                insertAndVerifySession()
            }
        }
    }


    private fun insertSession(): Task<Void> {
        //First, create a new session and an insertion request.
        val insertRequest = insertFitnessSession()

        // [START insert_session]
        // Then, invoke the Sessions API to insert the session and await the result,
        // which is possible here because of the AsyncTask. Always include a timeout when
        // calling await() to avoid hanging that can occur from the service being shutdown
        // because of low memory or other conditions.
        Log.i(TAG, "Inserting the session in the Sessions API")
        return Fitness.getSessionsClient(this, GoogleSignIn.getLastSignedInAccount(this)!!)
                .insertSession(insertRequest)
                .addOnSuccessListener(OnSuccessListener<Void> {
                    // At this point, the session has been inserted and can be read.
                    Log.i(TAG, "Session insert was successful!")
                    showSuccess("Session Inserted Successfully")

                })
                .addOnFailureListener(OnFailureListener { e -> Log.i(TAG, "There was a problem inserting the session: " + e.localizedMessage) })
        // [END insert_session]
    }


    private fun insertFitnessSession(): SessionInsertRequest {
        Log.i(TAG, "Creating a new session for an afternoon run")
        // Setting start and end times for our run.
        val cal = Calendar.getInstance()
        val now = Date()
        cal.setTime(now)

        // Create a data source
        val speedDataSource = DataSource.Builder()
                .setAppPackageName(this.packageName)
                .setDataType(DataType.TYPE_STEP_COUNT_DELTA)
                .setName(SAMPLE_SESSION_NAME)
                .setType(DataSource.TYPE_RAW)
                .build()


        val walkdataSet = DataSet.create(speedDataSource)
        val dataPoint =
                walkdataSet.createDataPoint().setTimeInterval(startTime.toLong(), endTime.toLong(), TimeUnit.MILLISECONDS);
        dataPoint.getValue(Field.FIELD_STEPS).setInt(stepCountDelta.toInt())
        walkdataSet.add(dataPoint);


        val activitySegmentDataSource = DataSource.Builder()
                .setAppPackageName(this.packageName)
                .setDataType(DataType.TYPE_ACTIVITY_SEGMENT)
                .setName(SAMPLE_SESSION_NAME)
                .setType(DataSource.TYPE_RAW)
                .build()
        val activitySegments = DataSet.create(activitySegmentDataSource)

        val firstRunningDp = activitySegments.createDataPoint()
                .setTimeInterval(startTime.toLong(), endTime.toLong(), TimeUnit.MILLISECONDS)
        firstRunningDp.getValue(Field.FIELD_ACTIVITY).setActivity(FitnessActivities.WALKING)
        activitySegments.add(firstRunningDp)


        val session = Session.Builder()
                .setName(SAMPLE_SESSION_NAME)
                .setDescription(description)
                .setIdentifier("UniqueIdentifierHere")
                .setActivity(FitnessActivities.WALKING)
                .setStartTime(startTime.toLong(), TimeUnit.MILLISECONDS)
                .setEndTime(endTime.toLong(), TimeUnit.MILLISECONDS)
                .build()

        return SessionInsertRequest.Builder()
                .setSession(session)
                .addDataSet(walkdataSet)
                .addDataSet(activitySegments)
                .build()
    }


    private fun OpenNewTimePicker() {
        val c = Calendar.getInstance()
        mHour1 = c.get(Calendar.HOUR_OF_DAY)
        mMinute1 = c.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(this,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    endtime_tv.setContentText("$hourOfDay:$minute", MaterialTextView.ANIMATE_TYPE.NONE)
                    time_end = "$hourOfDay:$minute"
                }, mHour1, mMinute1, true)
        timePickerDialog.show()
    }

    private fun OpenTimePicker() {
        val c = Calendar.getInstance()
        mHour = c.get(Calendar.HOUR_OF_DAY)
        mMinute = c.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(this,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    starttime_tv.setContentText("$hourOfDay:$minute", MaterialTextView.ANIMATE_TYPE.NONE)
                    time_start = "$hourOfDay:$minute"

                }, mHour, mMinute, true)
        timePickerDialog.show()
    }


    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.colorAccent)
        }
    }
}
