package indg.com.cover2protect.views.activity.deviceConnection.sugarMonitor;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.getmedcheck.activity.sugarMonitor.ActBgmDisplay;
import indg.com.cover2protect.DB.DBhelper;
import com.getmedcheck.lib.MedCheck;
import com.getmedcheck.lib.MedCheckActivity;
import com.getmedcheck.lib.constant.Constants;
import com.getmedcheck.lib.events.EventClearCommand;
import com.getmedcheck.lib.events.EventReadingProgress;
import com.getmedcheck.lib.model.BleDevice;
import com.getmedcheck.lib.model.BloodGlucoseData;
import com.getmedcheck.lib.model.IDeviceData;
import com.getmedcheck.lib.utils.StringUtils;
import com.google.gson.JsonObject;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import indg.com.cover2protect.R;
import indg.com.cover2protect.baseAeglOrbs.networking.ApiInterface;
import indg.com.cover2protect.baseAeglOrbs.networking.ApiUtils;
import indg.com.cover2protect.helper.SaveSharedPreference;
import indg.com.cover2protect.views.activity.deviceConnection.bpMonitor.ActBpDisplay;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import no.nordicsemi.android.support.v18.scanner.ScanResult;

public class ActBgmRetriveData extends MedCheckActivity implements View.OnClickListener {

    public static void start(Context context, BleDevice bleDevice) {
        Intent starter = new Intent(context, ActBgmRetriveData.class);
        starter.putExtra("DATA", bleDevice);
        context.startActivity(starter);
    }

    private BleDevice mBleDevice;
    private TextView mTvDeviceName;
    private TextView mTvConnectionState, tvDeviceName;
    private Button mBtnConnect, mBtnReadData, mBtnClearData,
            mBtnTimeSync, mBtnDisconnect,mBtnSampleData,btnSave;

            RadioButton btnFasting,btnPP,btnRandom;
            RadioGroup glucodeMode;
    private LinearLayout mLlCoreOperations, mLlBLEDeviceOperation;
    private TextView mTvResult;
    private boolean mAllPermissionsReady;
    private DBhelper dbHelper = new DBhelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_connection_glucose);

        if (getIntent() != null && getIntent().hasExtra("DATA")) {
            mBleDevice = getIntent().getParcelableExtra("DATA");
        }
        initView();
        requestLocationPermission();

        TextView tv_title = findViewById(R.id.tb_normal_title);
        tv_title.setText("Synchronizing with your device");

        ImageButton ib_back = findViewById(R.id.iv_tb_normal_back);
        ib_back.setOnClickListener(v -> finish());

        mBtnConnect.performClick();

    }

    private void initView() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            if (mBleDevice != null && !TextUtils.isEmpty(mBleDevice.getDeviceName())) {
                getSupportActionBar().setTitle(mBleDevice.getDeviceName());
            } else {
                getSupportActionBar().setTitle("Device Connection");
            }
        }

        mTvDeviceName = findViewById(R.id.tvDeviceName);
        mTvConnectionState = findViewById(R.id.tvStatus);
        mLlCoreOperations = findViewById(R.id.llCoreOperations);
        mLlBLEDeviceOperation = findViewById(R.id.llBLEDeviceOperation);
        btnSave = findViewById(R.id.btn_save_glucose);
        mBtnSampleData = findViewById(R.id.btnSampleData);
        mBtnSampleData.setOnClickListener(this);
        mBtnConnect = findViewById(R.id.btnConnect);
        mBtnConnect.setOnClickListener(this);
        mBtnReadData = findViewById(R.id.btnReadData);
        mBtnReadData.setOnClickListener(this);
        mBtnClearData = findViewById(R.id.btnClearData);
        mBtnClearData.setOnClickListener(this);
        mBtnTimeSync = findViewById(R.id.btnTimeSync);
        mBtnTimeSync.setOnClickListener(this);
        mBtnDisconnect = findViewById(R.id.btnDisconnect);
        mBtnDisconnect.setOnClickListener(this);
        btnFasting = findViewById(R.id.rb_fasting);
        btnPP = findViewById(R.id.rb_pp);
        btnRandom = findViewById(R.id.rb_random);
        glucodeMode = findViewById(R.id.radio_glucose_mode);



        mTvResult = findViewById(R.id.tvResult);
        mTvResult.setOnClickListener(v -> {
            Intent inDash = new Intent(this, ActBpDisplay.class);
            startActivity(inDash);
        });

        if (mBleDevice != null) {
            mTvDeviceName.setText(mBleDevice.getDeviceName());
            tvDeviceName = findViewById(R.id.tv_device_name);
            tvDeviceName.setText("Device "+mBleDevice.getDeviceName()+" connecting...");
        }

        registerCallback();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPermissionGrantedAndBluetoothOn() {
        mAllPermissionsReady = true;
        mLlCoreOperations.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDeviceClearCommand(int state) {
        super.onDeviceClearCommand(state);
        switch (state) {
            case EventClearCommand.CLEAR_START:
                mTvResult.setText("Clear Start");
                break;
            case EventClearCommand.CLEAR_COMPLETE:
                mTvResult.setText("Clear Successfully Completed");
                break;
            case EventClearCommand.CLEAR_FAIL:
                mTvResult.setText("Clear Fail");
                break;
        }
    }

    @Override
    protected void onDeviceConnectionStateChange(BleDevice bleDevice, int status) {
        super.onDeviceConnectionStateChange(bleDevice, status);
        if (bleDevice.getMacAddress().equals(mBleDevice.getMacAddress()) && status == 1) {
            mLlBLEDeviceOperation.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDeviceDataReadingStateChange(int state, String message) {
        super.onDeviceDataReadingStateChange(state, message);
        mTvConnectionState.setText(message);
        mBtnConnect.setEnabled(!(state == EventReadingProgress.COMPLETED));
       // btnSave.setEnabled(!(state == EventReadingProgress.COMPLETED));
    }

    @Override
    protected void onDeviceDataReceive(BluetoothDevice device, ArrayList<IDeviceData> deviceData, String json, String deviceType) {
        super.onDeviceDataReceive(device, deviceData, json, deviceType);
        if (deviceData == null) {
            return;
        }

        Log.e("MedcheckJson", "onDeviceDataReceive: "+ json );

        if (deviceData.size() == 0) {
            mTvResult.setText("No Data Found!");
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Type: ").append(deviceType).append("\n\n");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH);
        Date currentTime = Calendar.getInstance().getTime();

        btnRandom.setChecked(true);
        for (IDeviceData deviceDatum : deviceData) {

            if (deviceDatum.getType().equals(Constants.TYPE_BPM)) {

            } else if (deviceDatum.getType().equals(Constants.TYPE_BGM)) {
                BloodGlucoseData bloodGlucoseData = (BloodGlucoseData) deviceDatum;

                DecimalFormat df = new DecimalFormat("0.0");
                float val = 0;
                if (StringUtils.isNumber(bloodGlucoseData.getHigh())) {
                    val = Float.parseFloat(bloodGlucoseData.getHigh()) / 18f;
                }

                stringBuilder.append(df.format(val)).append(" mmol/L (").append(bloodGlucoseData.getHigh()).append(" mg/dL)\n");
                stringBuilder.append(bloodGlucoseData.getAcPcStringValue()).append("\n");
                stringBuilder.append("DATE: ").append(sdf.format(bloodGlucoseData.getDateTime()));
                stringBuilder.append("\n------------------------\n");

                Log.d("101",df.format(val));
                Log.d("101",bloodGlucoseData.getHigh());
                Log.d("101",bloodGlucoseData.getAcPcStringValue());
                Log.d("101",sdf.format(bloodGlucoseData.getDateTime()));


                float finalVal = val;

                btnSave.setVisibility(View.VISIBLE);

                btnSave.setOnClickListener(v -> {
                    Intent inDash = new Intent(this, ActBgmDisplay.class);

                    if(btnFasting.isChecked()){
                        dbHelper.insertBGMData(sdf.format(currentTime),
                                df.format(finalVal),
                                bloodGlucoseData.getHigh(),
                                bloodGlucoseData.getLow(),
                                "Fasting",
                                SaveSharedPreference.getUserId(getApplicationContext()),
                                sdf.format(bloodGlucoseData.getDateTime())
                        );
                        startActivity(inDash);

                        mPushBGMData(sdf.format(currentTime),
                                df.format(finalVal),
                                bloodGlucoseData.getHigh(),
                                bloodGlucoseData.getLow(),
                                "PP",
                                SaveSharedPreference.getUserId(getApplicationContext()),
                                sdf.format(bloodGlucoseData.getDateTime()));

                        finish();
                    }

                    if(btnRandom.isChecked()){
                        dbHelper.insertBGMData(sdf.format(currentTime),
                                df.format(finalVal),
                                bloodGlucoseData.getHigh(),
                                bloodGlucoseData.getLow(),
                                "Random",
                                SaveSharedPreference.getUserId(getApplicationContext()),
                                sdf.format(bloodGlucoseData.getDateTime())
                        );
                        startActivity(inDash);

                        mPushBGMData(sdf.format(currentTime),
                                df.format(finalVal),
                                bloodGlucoseData.getHigh(),
                                bloodGlucoseData.getLow(),
                                "PP",
                                SaveSharedPreference.getUserId(getApplicationContext()),
                                sdf.format(bloodGlucoseData.getDateTime()));

                        finish();
                    }

                    if(btnPP.isChecked()){
                        dbHelper.insertBGMData(sdf.format(currentTime),
                                df.format(finalVal),
                                bloodGlucoseData.getHigh(),
                                bloodGlucoseData.getLow(),
                                "PP",
                                SaveSharedPreference.getUserId(getApplicationContext()),
                                sdf.format(bloodGlucoseData.getDateTime())
                        );
                        startActivity(inDash);

                        mPushBGMData(sdf.format(currentTime),
                                df.format(finalVal),
                                bloodGlucoseData.getHigh(),
                                bloodGlucoseData.getLow(),
                                "PP",
                                SaveSharedPreference.getUserId(getApplicationContext()),
                                sdf.format(bloodGlucoseData.getDateTime()));

                        finish();
                    }

                });




            }

        }


    }

    private void mPushBGMData(String CurrentTimeMobile, String Mmol, String HighSugar, String LowSuagar, String TypeOf, String userId, String systemDateTime) {


       /* sdf.format(currentTime),
                df.format(finalVal),
                bloodGlucoseData.getHigh(),
                bloodGlucoseData.getLow(),
                "PP",
                SaveSharedPreference.getUserId(getApplicationContext()),
                sdf.format(bloodGlucoseData.getDateTime()*/

        JsonObject job1= new JsonObject();
        JsonObject job2 = new JsonObject();

        job2.addProperty("CurrentTimeMobile",CurrentTimeMobile);
        job2.addProperty("Mmol",Mmol);
        job2.addProperty("HighSugar",HighSugar);
        job2.addProperty("LowSuagar",LowSuagar);
        job2.addProperty("TypeOf reading",TypeOf);
        job2.addProperty("userId",userId);
        job2.addProperty("DeviceTime",systemDateTime);
        job2.addProperty("iSBGM",true);

        job1.add("data",job2);


        ApiInterface apiInterface = ApiUtils.getAPIServiceDevice(getApplicationContext());
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(apiInterface.rfxPostDeviceData(job1)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject jsonObject) {
                        showToast("Successfully pushed data");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConnect:
                if (mAllPermissionsReady) {
                    checkDeviceOnline();
                } else {
                    Toast.makeText(this, "Some of the Permissions are missing", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnReadData:
                readData();
                break;
            case R.id.btnClearData:
                clearData();
                break;
            case R.id.btnTimeSync:
                timeSync();
                break;
            case R.id.btnDisconnect:
                disconnectDevice();
                break;
            case R.id.btnSampleData:
                Intent inDash = new Intent(this, ActBpDisplay.class);
                startActivity(inDash);
        }
    }

    @Override
    protected void onDeviceScanResult(ScanResult scanResult) {
        super.onDeviceScanResult(scanResult);
    }


    private void connectDevice() {
        if (mBleDevice == null || !mAllPermissionsReady || TextUtils.isEmpty(mBleDevice.getMacAddress())) {
            return;
        } else {
            MedCheck.getInstance().connect(this, mBleDevice.getMacAddress());
        }
    }

    private void checkDeviceOnline() {
        if (mBleDevice == null || !mAllPermissionsReady || TextUtils.isEmpty(mBleDevice.getMacAddress())) {
            return;
        } else {
            connectDevice();
        }
    }

    private void readData() {
        if (mBleDevice == null || !mAllPermissionsReady || TextUtils.isEmpty(mBleDevice.getMacAddress())) {
            return;
        }
        MedCheck.getInstance().writeCommand(this, mBleDevice.getMacAddress());
    }

    private void clearData() {
        if (mBleDevice == null || !mAllPermissionsReady || TextUtils.isEmpty(mBleDevice.getMacAddress())) {
            return;
        }
        MedCheck.getInstance().clearDevice(this, mBleDevice.getMacAddress());
    }

    private void timeSync() {
        if (mBleDevice == null || !mAllPermissionsReady || TextUtils.isEmpty(mBleDevice.getMacAddress())) {
            return;
        }
        MedCheck.getInstance().timeSyncDevice(this, mBleDevice.getMacAddress());
    }

    private void disconnectDevice() {
        if (mBleDevice == null || !mAllPermissionsReady || TextUtils.isEmpty(mBleDevice.getMacAddress())) {
            return;
        }
        MedCheck.getInstance().disconnectDevice(this);
    }
}
