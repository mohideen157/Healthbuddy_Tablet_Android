
package indg.com.cover2protect.views.activity.deviceConnection.kisok

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import indg.com.cover2protect.R
import indg.com.cover2protect.baseAeglOrbs.Base2Activity
import indg.com.cover2protect.views.activity.health_profile.HealthProfileActivity
import kotlinx.android.synthetic.main.activity_kisok_dash.*


class kioskDash : Base2Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kisok_dash)
        btn_takereading.setOnClickListener {
            val intent = Intent(this, HealthProfileActivity::class.java)
            startActivity(intent)
        }
        btn_questionnaire.setOnClickListener {
            val intent = Intent(this, QuestionnaireActivityJava::class.java)
            startActivity(intent)
        }
        btn_wellness.setOnClickListener {
           /* val intent3 = Intent(this, wellnessfirstscreen::class.java)
            // start your next activity
            startActivity(intent3)*/
        }
    }
}
