package indg.com.cover2protect.base

import android.annotation.TargetApi
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.android.AndroidInjection
import indg.com.cover2protect.helper.NetworkUtils
import indg.com.cover2protect.util.CommonUtils
import indg.com.cover2protect.views.activity.deviceConnection.kisok.kioskDash

abstract class BaseActivityBinding<T : ViewDataBinding> : AppCompatActivity(), BaseFragmentBinding.Callback {

    private var mProgressDialog: ProgressDialog? = null
    var viewDataBinding: T? = null
        private set


    abstract val bindingVariable: Int

    @get:LayoutRes
    abstract val layoutId: Int



    val isNetworkConnected: Boolean
        get() = NetworkUtils.isNetworkConnected(applicationContext)

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.cancel()
        }
    }

    fun skip_Click() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setMessage("Do you really want to skip?")
        builder.setPositiveButton("Yes") { dialog, which ->
            //if user pressed "yes", then he is allowed to exit from application
            startActivity(Intent(this, kioskDash::class.java))
        }
        builder.setNegativeButton("No") { dialog, which ->
            //if user select "No", just cancel this dialog and continue with app
            dialog.cancel()
        }
        val alert = builder.create()
        alert.show()
    }



    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    fun showLoading() {
        hideLoading()
        mProgressDialog = CommonUtils.showLoadingDialog(this)
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        viewDataBinding!!.setVariable(bindingVariable,"")
        viewDataBinding!!.executePendingBindings()
    }
}
