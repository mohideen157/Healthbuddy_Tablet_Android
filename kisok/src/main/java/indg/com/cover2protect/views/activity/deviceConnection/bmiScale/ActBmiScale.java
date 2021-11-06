package indg.com.cover2protect.views.activity.deviceConnection.bmiScale;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

import androidx.annotation.Nullable;
import com.lifesense.ble.LsBleManager;
import com.lifesense.ble.bean.LsDeviceInfo;

import indg.com.cover2protect.R;
import indg.com.cover2protect.baseAeglOrbs.Base2Activity;
import indg.com.cover2protect.baseAeglOrbs.W3Obj;
import indg.com.cover2protect.util.SharedPrefUtils;
import indg.com.cover2protect.views.activity.deviceConnection.bmiScale.lifesense.ble.ui.ScreenFragmentMessage;
import indg.com.cover2protect.views.activity.deviceConnection.bmiScale.lifesense.ble.ui.menu.SearchDeviceFragment;
import indg.com.cover2protect.views.activity.deviceConnection.bmiScale.lifesense.ble.ui.view.ConnectDeviceFragment;


public class ActBmiScale extends Base2Activity
implements FragDialougeWeightScaleHalfPie.InterfaceResponse,
FragDialougeNewUserDetails.InterfaceResponse,
FragDialougeWeightScaleData.InterfaceResponse,
PairedBmiListFragment.showParingFragment{

    private LsBleManager mlsBleManager;
    TextView tvTbToolbar;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_holder_activity);
        tvTbToolbar = findViewById(R.id.tb_normal_title);
        tvTbToolbar.setText("BMI Scale");


        mlsBleManager = LsBleManager.newInstance();
        mlsBleManager.initialize(getApplicationContext());

        screenFragmentHandler = new ActBmiScale.ScreenFragmentHandler(getMainLooper());


        if(!spGetter().getBoolean(SharedPrefUtils.BMI_PAIRED,false)){

        Fragment searchFragment = new SearchBmiFragment();
        displayView(searchFragment);

        }else {

            Fragment searchFragment = new PairedBmiListFragment();
            displayView(searchFragment);

        }



    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!spGetter().getBoolean(SharedPrefUtils.USER_DETAILS_SAVED,false)){

            FragmentManager fm = getFragmentManager();
            FragDialougeNewUserDetails dialogFragment = new FragDialougeNewUserDetails ();
            dialogFragment.setCancelable(false);
            dialogFragment.show(fm, "NewUser");

        }


    }

    private void displayView (Fragment targetFragment){
        if (targetFragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.fragment_holder, targetFragment).commit();
        }

    }


    private Handler screenFragmentHandler;
    public Handler getScreenFragmentHandler()
    {
        return screenFragmentHandler;
    }

    @Override
    public void notificationItemSelected(int it) {

    }

    @Override
    public void dialogClosed() {
        finish();
        showToast("Details needed to proceed.");
    }

    @Override
    public void onClickParing(W3Obj item) {

        Bundle args = new Bundle();
        args.putSerializable("weightData", item);

        FragmentManager fm = getFragmentManager();
        FragDialougeWeightScaleHalfPie dialogFragment = new FragDialougeWeightScaleHalfPie ();
        dialogFragment.setArguments(args);
        dialogFragment.show(fm, "FragmentWeightData");

    }

    @Override
    public void showHistory() {

        Bundle args = new Bundle();

        FragmentManager fm = getFragmentManager();
        FragDialougeWeightScaleData dialogFragment = new FragDialougeWeightScaleData();
        dialogFragment.setArguments(args);
        dialogFragment.show(fm, "FragmentWeightData");

    }


    public class ScreenFragmentHandler extends Handler
    {
        public ScreenFragmentHandler(Looper myLooper)
        {
            super(myLooper);
        }

        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg)
        {
            switch(msg.arg1)
            {
                case ScreenFragmentMessage.MSG_SCREEN_FRAGMENT_CONNECT_DEVICE:
                {
                    LsDeviceInfo deviceInfo=(LsDeviceInfo) msg.obj;

                    Fragment connectFragment = new ConnectDeviceFragment();

                    final Bundle args = new Bundle();
                    args.putParcelable("LS_DEVICE_INFO", deviceInfo);
                    connectFragment.setArguments(args);
                    displayView(connectFragment);
                }break;
                case ScreenFragmentMessage.MSG_ADD_PRODUCT_BARCODE:
                {
                    Fragment searchFragment = new SearchDeviceFragment();
                    displayView(searchFragment);
                    //updateDrawerLayout(MSG_FRAGMENT_POSITION_SEARCH_DEVICE);
                }break;
                case ScreenFragmentMessage.MSG_SHOW_PAIRED_DEVICE_LIST:
                {
                    spEditor().putBoolean(SharedPrefUtils.BMI_PAIRED,true);
                    spEditor().commit();


                    Fragment deviceListFragment = new PairedBmiListFragment();
                    displayView(deviceListFragment);
                   // updateDrawerLayout(MSG_FRAGMENT_POSITION_PAIRED_DEVICE_LIST);

                }break;
                case ScreenFragmentMessage.MSG_ADD_DEVICE:
                {
                    Fragment addDeviceFragment = new SearchDeviceFragment();
                    displayView(addDeviceFragment);
                   // updateDrawerLayout(MSG_FRAGMENT_POSITION_SEARCH_DEVICE);
                }break;
            }
        }
    }



}