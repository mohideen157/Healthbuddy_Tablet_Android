package indg.com.cover2protect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_questionnaire1.*

class questionnaire1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionnaire1)
        // access the items of the list
        /*val questionnairespinner1 = resources.getStringArray(R.array.questionnaire1)

        // access the spinner
        val spinner = findViewById<Spinner>(R.id.questionnairespinner1)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, questionnairespinner1)
            spinner.adapter = adapter
        }*/
    }
}