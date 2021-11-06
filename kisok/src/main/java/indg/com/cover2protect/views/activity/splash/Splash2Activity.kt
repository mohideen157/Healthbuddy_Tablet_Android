package indg.com.cover2protect.views.activity.splash

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.Toast
import indg.com.cover2protect.helper.NetworkUtils
import indg.com.cover2protect.R
import indg.com.cover2protect.views.activity.login.LoginActivity

class Splash2Activity : AppCompatActivity() {

    private val SPLASH_DISPLAY_LENGTH = 2500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeStatusBarColor()
        setContentView(R.layout.activity_splash2)
        if(isNetworkConnected()){
            Handler().postDelayed({
                try{
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
                }catch (ex:Exception){

                }

            }, SPLASH_DISPLAY_LENGTH.toLong())
        }
        else{

            Toast.makeText(this,"Check Internet Connection", Toast.LENGTH_SHORT).show()
        }

    }

    fun isNetworkConnected(): Boolean {
        return NetworkUtils.isNetworkConnected(getApplicationContext())
    }
    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.darkblue)
        }    }
}
