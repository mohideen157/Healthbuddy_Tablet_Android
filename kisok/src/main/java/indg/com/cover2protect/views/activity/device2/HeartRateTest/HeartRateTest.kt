package indg.com.cover2protect.views.activity.device2.HeartRateTest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import indg.com.cover2protect.R
import android.app.TimePickerDialog
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_heart_rate_test.*
import java.util.*
import kotlinx.android.synthetic.main.activity_add.toolbar
import kotlinx.android.synthetic.main.toolbar_heartratetest.*
import android.app.Activity
import android.content.Intent




class HeartRateTest : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.start_rl ->{
                showStartTimePicker()
            }
            R.id.endtime_rl ->{
                showEndTimePicker()
            }
            R.id.done->{
                SubmitRequest()
            }
        }
    }


    private var startTime:String=""
    private var endtime:String=""
    private var enabled:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heart_rate_test)
        setSupportActionBar(toolbar)
        title = resources.getString(R.string.heartratetest)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp)
        toolbar.setNavigationOnClickListener { arrow -> onBackPressed() }
        start_rl.setOnClickListener(this)
        endtime_rl.setOnClickListener(this)
        switchButton.setOnCheckedChangeListener { view, isChecked ->
            enabled = isChecked
        }
        done.setOnClickListener(this)


    }

    private fun SubmitRequest() {
      if(enabled){
          if(!startTime.isNullOrEmpty()){
              if(!endtime.isNullOrEmpty()){
                  val returnIntent = Intent()
                  returnIntent.putExtra("starttime", startTime)
                  returnIntent.putExtra("endtime", endtime)
                  setResult(Activity.RESULT_OK, returnIntent)
                  finish()
              }else{
                  Toast.makeText(this,"Select End Time",Toast.LENGTH_LONG).show()

              }
          }else{
              Toast.makeText(this,"Select Start Time",Toast.LENGTH_LONG).show()
          }
      }else{
          val returnIntent = Intent()
          setResult(Activity.RESULT_CANCELED, returnIntent)
          finish()
      }
    }


    fun showStartTimePicker() {

        val myCalender = Calendar.getInstance()
        val hour = myCalender.get(Calendar.HOUR_OF_DAY)
        val minute = myCalender.get(Calendar.MINUTE)


        val myTimeListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            if (view.isShown) {
                myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay)
                myCalender.set(Calendar.MINUTE, minute)
                start_time.text = "$hourOfDay:$minute"
                startTime = "$hourOfDay:$minute"

            }
        }
        val timePickerDialog = TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true)
        timePickerDialog.setTitle("Choose hour:")
        timePickerDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        timePickerDialog.show()
    }

    fun showEndTimePicker() {

        val myCalender = Calendar.getInstance()
        val hour = myCalender.get(Calendar.HOUR_OF_DAY)
        val minute = myCalender.get(Calendar.MINUTE)


        val myTimeListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            if (view.isShown) {
                myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay)
                myCalender.set(Calendar.MINUTE, minute)
                end_time.text = "$hourOfDay:$minute"
                endtime = "$hourOfDay:$minute"

            }
        }
        val timePickerDialog = TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true)
        timePickerDialog.setTitle("Choose hour:")
        timePickerDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        timePickerDialog.show()
    }
}
