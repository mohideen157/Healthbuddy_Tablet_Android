package indg.com.cover2protect.util.crash_handle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button


import indg.com.cover2protect.R

class CustomErrorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_custom_error)



       // val errorDetailsText = findViewById<TextView>(R.id.error_details)
        //errorDetailsText.text = CustomActivityOnCrash.getStackTraceFromIntent(intent)

        val restartButton = findViewById<Button>(R.id.restart_button)

        val config = CustomActivityOnCrash.getConfigFromIntent(intent)

        if (config == null) {
            //This should never happen - Just finish the activity to avoid a recursive crash.
            finish()
            return
        }

        if (config.isShowRestartButton && config.restartActivityClass != null) {
            restartButton.setText(R.string.restart_app)
            restartButton.setOnClickListener { CustomActivityOnCrash.restartApplication(this@CustomErrorActivity, config) }
        } else {
            restartButton.setOnClickListener { CustomActivityOnCrash.closeApplication(this@CustomErrorActivity, config) }
        }
    }
}
