

package indg.com.cover2protect.util.crash_handle.activity

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import indg.com.cover2protect.R
import indg.com.cover2protect.util.crash_handle.CustomActivityOnCrash


class DefaultErrorActivity : AppCompatActivity() {

    @SuppressLint("PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //This is needed to avoid a crash if the developer has not specified
        //an app-level theme that extends Theme.AppCompat
        val a = obtainStyledAttributes(R.styleable.AppCompatTheme)
        if (!a.hasValue(R.styleable.AppCompatTheme_windowActionBar)) {
            setTheme(R.style.Theme_AppCompat_Light_DarkActionBar)
        }
        a.recycle()
        setContentView(R.layout.customactivityoncrash_default_error_activity)
        //Close/restart button logic:
        //If a class if set, use restart.
        //Else, use close and just finish the app.
        //It is recommended that you follow this logic if implementing a custom error activity.
        val restartButton = findViewById<Button>(R.id.customactivityoncrash_error_activity_restart_button)
        val config = CustomActivityOnCrash.getConfigFromIntent(intent)
        if (config == null) {
            //This should never happen - Just finish the activity to avoid a recursive crash.
            finish()
            return
        }
        if (config.isShowRestartButton && config.restartActivityClass != null) {
            restartButton.setText(R.string.customactivityoncrash_error_activity_restart_app)
            restartButton.setOnClickListener { CustomActivityOnCrash.restartApplication(this@DefaultErrorActivity, config) }
        } else {
            restartButton.setOnClickListener { CustomActivityOnCrash.closeApplication(this@DefaultErrorActivity, config) }
        }
        val moreInfoButton = findViewById<Button>(R.id.customactivityoncrash_error_activity_more_info_button)
        if (config.isShowErrorDetails) {
            moreInfoButton.setOnClickListener {
                //We retrieve all the error data and show it

                val dialog = AlertDialog.Builder(this@DefaultErrorActivity)
                        .setTitle(R.string.customactivityoncrash_error_activity_error_details_title)
                        .setMessage(CustomActivityOnCrash.getAllErrorDetailsFromIntent(this@DefaultErrorActivity, intent))
                        .setPositiveButton(R.string.customactivityoncrash_error_activity_error_details_close, null)
                        .setNeutralButton(R.string.customactivityoncrash_error_activity_error_details_copy
                        ) { dialog, which -> copyErrorToClipboard() }
                        .show()
                val textView = dialog.findViewById<TextView>(android.R.id.message)
                textView?.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.customactivityoncrash_error_activity_error_details_text_size))
            }
        } else {
            moreInfoButton.visibility = View.GONE
        }

        val defaultErrorActivityDrawableId = config.errorDrawable
        val errorImageView = findViewById<ImageView>(R.id.customactivityoncrash_error_activity_image)

        if (defaultErrorActivityDrawableId != null) {
            errorImageView.setImageDrawable(ResourcesCompat.getDrawable(resources, defaultErrorActivityDrawableId, theme))
        }
    }

    private fun copyErrorToClipboard() {
        val errorInformation = CustomActivityOnCrash.getAllErrorDetailsFromIntent(this@DefaultErrorActivity, intent)

        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        //Are there any devices without clipboard...?
        if (clipboard != null) {
            val clip = ClipData.newPlainText(getString(R.string.customactivityoncrash_error_activity_error_details_clipboard_label), errorInformation)
            clipboard.primaryClip = clip
            Toast.makeText(this@DefaultErrorActivity, R.string.customactivityoncrash_error_activity_error_details_copied, Toast.LENGTH_SHORT).show()
        }
    }
}
