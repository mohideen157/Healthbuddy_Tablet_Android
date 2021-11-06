package indg.com.cover2protect.base

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import indg.com.cover2protect.helper.NetworkUtils
import indg.com.cover2protect.util.CommonUtils
import indg.com.cover2protect.views.activity.home.ProfileActivity

abstract class  BaseActivity: AppCompatActivity(),BaseFragment.Callback {


    private var mProgressDialog:ProgressDialog?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    fun showLoading() {
        hideLoading()
        mProgressDialog = CommonUtils.showLoadingDialog(this)
    }



    fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing()) {
            mProgressDialog!!.cancel()
        }
    }
     fun skip_Click() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setMessage("Do you really want to skip?")
        builder.setPositiveButton("Yes") { dialog, which ->
            //if user pressed "yes", then he is allowed to exit from application
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        builder.setNegativeButton("No") { dialog, which ->
            //if user select "No", just cancel this dialog and continue with app
            dialog.cancel()
        }
        val alert = builder.create()
        alert.show()
    }

    fun isNetworkConnected(): Boolean {
        return NetworkUtils.isNetworkConnected(getApplicationContext())
    }
    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }




}