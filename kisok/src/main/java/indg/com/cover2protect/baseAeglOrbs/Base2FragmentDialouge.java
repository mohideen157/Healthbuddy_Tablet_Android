package indg.com.cover2protect.baseAeglOrbs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import indg.com.cover2protect.BuildConfig;
import indg.com.cover2protect.R;
import indg.com.cover2protect.baseAeglOrbs.networking.ApiInterface;
import indg.com.cover2protect.baseAeglOrbs.networking.ApiUtils;
import indg.com.cover2protect.util.SharedPrefUtils;
import io.reactivex.disposables.CompositeDisposable;


public class Base2FragmentDialouge extends DialogFragment {


    private ProgressDialog mProgressDialog;
    private int PROGRESS_DIALOG_COUNTER = 0;

    private SharedPreferences spGetter;
    private SharedPreferences.Editor spEdit;



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //mDumpDb();
    }

    private void mDumpDb() {

        File f=new File("/data/data/"+ BuildConfig.APPLICATION_ID +"/databases/c2phealthfit.db3");
        FileInputStream fis=null;
        FileOutputStream fos=null;

        try
        {
            fis=new FileInputStream(f);
            fos=new FileOutputStream("/mnt/sdcard/db_dump.db");
            while(true)
            {
                int i=fis.read();
                if(i!=-1)
                {fos.write(i);}
                else
                {break;}
            }
            fos.flush();
            Toast.makeText(getActivity(), "DB dump OK", Toast.LENGTH_LONG).show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Toast.makeText(getActivity(), "DB dump ERROR", Toast.LENGTH_LONG).show();
        }
        finally
        {
            try
            {
                fos.close();
                fis.close();
            }
            catch(IOException ioe)
            {}
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spGetter = this.getActivity().getSharedPreferences(SharedPrefUtils.AppPreference, Context.MODE_PRIVATE);
        spEdit = this.spGetter.edit();
    }

    public SharedPreferences spGetter(){
        return spGetter;
    }

    public SharedPreferences.Editor spEditor(){
        return spEdit;
    }


    public CompositeDisposable mCompositeDisposable;
    public CompositeDisposable mCompositeDisposableGetter(){

        return new CompositeDisposable();
    }

    private ApiInterface mApiInterface;
    public ApiInterface mApiGetter() {
        mApiInterface = ApiUtils.getAPIServiceRx(getActivity());
        return mApiInterface;
    }

    public void showAlert(String message, boolean dismissible){

        final Dialog dialog = new Dialog(getActivity(), R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if(!dismissible){
            dialog.setCancelable(false);}
        dialog.setContentView(R.layout.dialouge_alert);

        TextView text = (TextView) dialog.findViewById(R.id.tv_alert_dialogue_message);
        text.setText(message);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_alert_dialogue_ok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public Activity getViewContext() {
        return getActivity();
    }


    public void showToast(String message) {
        if (message != null) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        } else {
            //Toast.makeText(this, getString(R.string.some_error), Toast.LENGTH_SHORT).show();
        }
    }




    public void hideSoftKeyboard() {

        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                    0);
        }

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void showLoadingDialog(boolean iScancellable) {

        if (mProgressDialog == null) {

            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Please wait...");
            if(!iScancellable){
                mProgressDialog.setCancelable(false);
                mProgressDialog.setIndeterminate(true);}
            try{
                mProgressDialog.show();
            } catch (Exception e){
                e.printStackTrace();
            }
            PROGRESS_DIALOG_COUNTER++;

        } else {
            PROGRESS_DIALOG_COUNTER++;
            try{
                mProgressDialog.show();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public void hideLoadingDialog() {

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            PROGRESS_DIALOG_COUNTER--;
            if (PROGRESS_DIALOG_COUNTER == 0){
                try {
                    mProgressDialog.cancel();
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mProgressDialog != null && mProgressDialog.isShowing()){
            try {
                mProgressDialog.cancel();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
