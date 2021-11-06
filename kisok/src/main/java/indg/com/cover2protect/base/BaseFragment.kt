package indg.com.cover2protect.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection
import indg.com.cover2protect.helper.NetworkUtils
import indg.com.cover2protect.util.CommonUtils

abstract class BaseFragment : Fragment() {



    private var mProgressDialog:ProgressDialog?=null
    private var mActivity: BaseActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }


    fun showLoading() {
        hideLoading()
        mProgressDialog = CommonUtils.showLoadingDialog(this!!.mActivity!!)
    }

    fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing()) {
            mProgressDialog!!.cancel()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context!!)
        if (context is BaseActivity) {
            val activity = context as BaseActivity?
            this.mActivity = activity
            activity!!.onFragmentAttached()
        }
    }

    fun isNetworkConnected(): Boolean {
        return NetworkUtils.isNetworkConnected(this!!.mActivity!!.applicationContext)
    }

    fun getBaseActivity(): BaseActivity? {
        return mActivity
    }

    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }

    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }
}