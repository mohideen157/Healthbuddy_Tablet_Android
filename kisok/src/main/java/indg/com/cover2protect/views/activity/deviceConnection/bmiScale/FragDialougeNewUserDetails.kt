package indg.com.cover2protect.views.activity.deviceConnection.bmiScale

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import indg.com.cover2protect.R
import indg.com.cover2protect.baseAeglOrbs.Base2FragmentDialouge
import indg.com.cover2protect.util.SharedPrefUtils
import kotlinx.android.synthetic.main.fragment_new_user_details.*
import kotlinx.android.synthetic.main.toolbar_normal.*


class FragDialougeNewUserDetails : Base2FragmentDialouge() {

    internal var interfaceResponse: InterfaceResponse? = null



    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is InterfaceResponse) {
            interfaceResponse = context
        } else {
            throw RuntimeException("$context must implement InterfaceSelectListItemInteractionListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog((activity as Context?)!!, theme) {
            override fun onBackPressed() {
              dismiss()
              interfaceResponse?.dialogClosed()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppTheme)

    }


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_new_user_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        iv_tb_normal_back.setOnClickListener {
            interfaceResponse?.dialogClosed()
        }


        val itemOnClick: (Int) -> Unit = {
            interfaceResponse!!.dialogClosed()
            dismissAllowingStateLoss()
        }



        btn_user_save.setOnClickListener {

            if(et_user_age.text!!.length >= 2){

                spEditor().putString(SharedPrefUtils.USER_AGE,et_user_age.text.toString())

            }else{
                mSetEror(et_user_age);
            }

            if(et_user_height.text!!.length >= 2){

                spEditor().putString(SharedPrefUtils.USER_HEIGHT,et_user_height.text.toString())

            }else{
                mSetEror(et_user_height);
            }

            if(et_user_target.text!!.isNotEmpty()){

                spEditor().putString(SharedPrefUtils.USER_TARGET,et_user_target.text.toString())

            }else{
                mSetEror(et_user_target);
            }

            if(rb_male.isChecked){
                spEditor().putBoolean(SharedPrefUtils.USER_SEX,true)//is male
            }else{
                spEditor().putBoolean(SharedPrefUtils.USER_SEX,false)
            }


            spEditor().putBoolean(SharedPrefUtils.USER_DETAILS_SAVED,true)
            spEditor().commit()
            dismiss()
        }

    }

    private fun mSetEror(et: TextInputEditText) {
        et.error = "Required"
        return
    }


    interface InterfaceResponse {

        fun dialogClosed()

    }

   /* companion object {
        *//**
         * Create a new instance of MyDialogFragment, providing "num"
         * as an argument.
         *//*
        fun newInstance(title: String,data: WeightData_A3): FragmentDialougeBodyFat {

            val f = FragmentDialougeBodyFat()
            val args = Bundle()
            args.putString("title", title)
            args.putParcelable("data3",data)

            f.arguments = args

            return f
        }
    }*/

}
