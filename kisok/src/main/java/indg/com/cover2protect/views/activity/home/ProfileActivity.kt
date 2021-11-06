package indg.com.cover2protect.views.activity.home

import android.app.ProgressDialog.show
import androidx.lifecycle.Observer
import android.bluetooth.BluetoothAdapter
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import indg.com.cover2protect.BR
import indg.com.cover2protect.BuildConfig
import indg.com.cover2protect.helper.DeviceHelper
import indg.com.cover2protect.data.errordialog.Sneaker
import indg.com.cover2protect.helper.SaveSharedPreference
import indg.com.cover2protect.model.profile.Profile
import indg.com.cover2protect.R
import indg.com.cover2protect.base.BaseActivityBinding
import indg.com.cover2protect.databinding.ActivityProfileBinding
import indg.com.cover2protect.viewmodel.mainprofileviewmodel.ProfileViewModel
import indg.com.cover2protect.views.activity.guide_activity.GuideActivity
import indg.com.cover2protect.views.activity.login.LoginActivity
import indg.com.cover2protect.views.fragments.profile.ProfileFragment
import indg.com.cover2protect.views.activity.scandevice.ScanActivity
import indg.com.cover2protect.views.activity.device2.HistoryActivity
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.app_bar_profile.*
import javax.inject.Inject

class ProfileActivity : BaseActivityBinding<ActivityProfileBinding>(), HasSupportFragmentInjector,
        indg.com.cover2protect.presenter.Profile_Data_Response, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {


    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.activity_profile


    private var currentMenuItem = 0
    private var navigationPosition: Int = 0
    private val REQUEST_ENABLE_BT = 3

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<androidx.fragment.app.Fragment>

    @Inject
    lateinit var viewModel: ProfileViewModel
    private var builder: AlertDialog.Builder? = null
    private var dialog: AlertDialog? = null
    private var toast: Toast? = null
    private var thatThingHappened: Boolean = false
    private var binding: ActivityProfileBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        changeStatusBarColor()
        title = "Home"
        viewModel!!.setNavigator(this)
        if (savedInstanceState == null) {
            navigationPosition = R.id.profile_ll
            navigateToFragment(ProfileFragment.newInstance())
            nav_view.setCheckedItem(navigationPosition)
        }
        builder = AlertDialog.Builder(this)
        builder!!.setCancelable(false)
        builder!!.setView(R.layout.dialog_progress)
        dialog = builder!!.create()
        initializeFirebase()
        val headerView = nav_view.getHeaderView(0)
        getdata(headerView)
        setSupportActionBar(toolbar)
        currentMenuItem = R.id.profile_ll//default value set to first item
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)



    }


    override fun onResponse(data: Profile) {
        hideLoading()
    }

    override fun onError(msg: String) {
        hideLoading()
    }


    override fun supportFragmentInjector(): AndroidInjector<androidx.fragment.app.Fragment> {

        return fragmentDispatchingAndroidInjector
    }


    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.colorAccent)
        }
    }

    private fun initializeFirebase() {
        if (FirebaseApp.getApps(applicationContext).isEmpty()) {
            FirebaseApp.initializeApp(applicationContext, FirebaseOptions.fromResource(applicationContext)!!)
        }
        var remoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .setMinimumFetchIntervalInSeconds(4200)
                .build()
        remoteConfig.setConfigSettings(configSettings)
        var playStoreVersionCode = remoteConfig.getString("android_latest_version_code")
        var pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
        var currentAppVersionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            pInfo.longVersionCode
        } else {
            pInfo.versionCode
        }
        remoteConfig.fetchAndActivate()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        if(BuildConfig.DEBUG){
                            return@addOnCompleteListener
                        }

                        val updated = task.result
                        if (!playStoreVersionCode.isNullOrEmpty()) {
                            if (playStoreVersionCode.toDouble() > currentAppVersionCode.toString().toDouble()) {
                                AlertDialog.Builder(this)
                                        .setTitle("UPDATE")
                                        .setMessage("A new Android Update is now Available Please Update it From Google Play")
                                        .setPositiveButton(R.string.update) { dialog, which ->
                                            var appPackageName = applicationContext.packageName
                                            try {
                                                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
                                            } catch (e: ActivityNotFoundException) {
                                                // if there is no Google Play on device
                                                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
                                            }
                                            dialog.dismiss()
                                        }
                                        .setNegativeButton(R.string.cancel) { dialog, which ->
                                            dialog.dismiss()
                                        }
                                        .show()

                            }
                        }
                    } else {

                    }
                }


    }




    private fun enableBluetooth() {
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
    }

    private fun bluetoothEnabled(): Boolean {
        val adapter = BluetoothAdapter.getDefaultAdapter()

        // Ensures Bluetooth is available on the device and it is enabled. If not,
        // displays a dialog requesting user permission to enable Bluetooth.
        return adapter != null && adapter.isEnabled
    }

    override fun onResume() {
        super.onResume()
        thatThingHappened = false
    }


    private fun showMessage(s: String) {
        Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setTitle("Message", R.color.white)
                .setMessage(s, R.color.white)
                .sneak(R.color.fbutton_color_belize_hole)
    }


    private fun getdata(headerView: View) {
        val navUsername = headerView.findViewById<View>(R.id.username) as TextView
        val profileimg = headerView.findViewById<View>(R.id.img_user) as de.hdodenhof.circleimageview.CircleImageView

        showLoading()
        viewModel.GetProfileData()!!.observe(this, androidx.lifecycle.Observer {
            hideLoading()
            if (it != null) {
                viewModel.GetProfileImage().observe(this, Observer {
                    if (!it.isNullOrEmpty()) {
                        Glide.with(this).load(it).into(profileimg)
                    }
                })
                if (!it.data!!.name.isNullOrEmpty()) {
                    navUsername.text = it.data!!.name
                }
            }
        })
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            if (navigationPosition == R.id.profile_ll) {
                askforexit()
            } else {
                navigationPosition = R.id.profile_ll
                navigateToFragment(ProfileFragment.newInstance())
                nav_view.setCheckedItem(navigationPosition)

            }
        }
    }

    private fun askforexit() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setMessage("Do you really want to exit?")
        builder.setPositiveButton("Yes") { dialog, which ->
            //if user pressed "yes", then he is allowed to exit from application
            finishAffinity()
        }
        builder.setNegativeButton("No") { dialog, which ->
            //if user select "No", just cancel this dialog and continue with app
            dialog.cancel()
        }
        val alert = builder.create()
        alert.show()
    }

    private fun navigateToFragment(fragmentToNavigate: androidx.fragment.app.Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.content_frame, fragmentToNavigate)
        fragmentTransaction.setTransition(androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }


    private fun logout() {
        try {
            SaveSharedPreference.setDeviceStatus(applicationContext, false)
            SaveSharedPreference.setLoggedIn(applicationContext, false)
            SaveSharedPreference.setDeviceExistence(applicationContext, false)
            SaveSharedPreference.setUserMobile(applicationContext, "")
            SaveSharedPreference.setFirstTime(applicationContext, true)
            DeviceHelper.Clear()
            var intent = Intent(this, LoginActivity::class.java)
            intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            this.finish()
        } catch (ex: Exception) {
        }

    }

    override fun onClick(v: View?) {
        when (v!!.id) {


        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profile_ll -> {
                navigationPosition = R.id.profile_ll
                navigateToFragment(ProfileFragment.newInstance())

            }
            R.id.connectdev_ll -> {
               if(SaveSharedPreference.getDeviceStatus(applicationContext)){
                   navigationPosition = R.id.connectdev_ll
                   val intent = Intent(this, HistoryActivity::class.java)
                   startActivity(intent)
               }else{
                   navigationPosition = R.id.connectdev_ll
                   val intent = Intent(this, ScanActivity::class.java)
                   startActivity(intent)
               }

            }
            R.id.ep_ll -> {


            }
            R.id.history_ll -> {
                navigationPosition = R.id.history_ll

            }
            R.id.nutrionhis_ll -> {


            }

            R.id.howtouse_ll -> {
                navigationPosition = R.id.howtouse_ll
                startActivity(Intent(this, GuideActivity::class.java))
            }

            R.id.logout_ll -> {
                val builder = AlertDialog.Builder(this)
                builder.setCancelable(false)
                builder.setMessage("Do you want to Logout?")
                builder.setPositiveButton("Yes") { dialog, which ->
                    //if user pressed "yes", then he is allowed to exit from application
                    logout()
                }
                builder.setNegativeButton("No") { dialog, which ->
                    //if user select "No", just cancel this dialog and continue with app
                    dialog.cancel()
                }
                val alert = builder.create()
                alert.show()

            }
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}
