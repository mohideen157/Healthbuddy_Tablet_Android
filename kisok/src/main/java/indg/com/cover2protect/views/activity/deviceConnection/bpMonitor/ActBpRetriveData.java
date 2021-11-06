package indg.com.cover2protect.views.activity.deviceConnection.bpMonitor;

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
import android.widget.TextView;
import android.widget.Toast;


import indg.com.cover2protect.DB.DBhelper;
import com.getmedcheck.lib.MedCheck;
import com.getmedcheck.lib.MedCheckActivity;
import com.getmedcheck.lib.constant.Constants;
import com.getmedcheck.lib.events.EventClearCommand;
import com.getmedcheck.lib.events.EventReadingProgress;
import com.getmedcheck.lib.model.BleDevice;
import com.getmedcheck.lib.model.BloodPressureData;
import com.getmedcheck.lib.model.IDeviceData;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import indg.com.cover2protect.R;
import indg.com.cover2protect.baseAeglOrbs.networking.ApiInterface;
import indg.com.cover2protect.baseAeglOrbs.networking.ApiUtils;
import indg.com.cover2protect.helper.MedicalRecordRest;
import indg.com.cover2protect.helper.SaveSharedPreference;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import no.nordicsemi.android.support.v18.scanner.ScanResult;

public class ActBpRetriveData extends MedCheckActivity implements View.OnClickListener {

    public static void start(Context context, BleDevice bleDevice) {
        Intent starter = new Intent(context, ActBpRetriveData.class);
        starter.putExtra("DATA", bleDevice);
        context.startActivity(starter);
    }

    private BleDevice mBleDevice;
    private TextView mTvDeviceName;
    private TextView mTvConnectionState, tvDeviceName;
    private Button mBtnConnect, mBtnReadData, mBtnClearData, mBtnTimeSync, mBtnDisconnect,mBtnSampleData;
    private LinearLayout mLlCoreOperations, mLlBLEDeviceOperation;
    private TextView mTvResult;
    private boolean mAllPermissionsReady;
    private DBhelper dbHelper = new DBhelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_connection);

        if (getIntent() != null && getIntent().hasExtra("DATA")) {
            mBleDevice = getIntent().getParcelableExtra("DATA");
        }
        initView();
        requestLocationPermission();

        TextView tv_title = findViewById(R.id.tb_normal_title);
        tv_title.setText("Synchronizing with your device");

        ImageButton ib_back = findViewById(R.id.iv_tb_normal_back);
        ib_back.setOnClickListener(v -> finish());

        if (mAllPermissionsReady) {
            checkDeviceOnline();
        } else {
            Toast.makeText(this, "Some of the Permissions are missing", Toast.LENGTH_SHORT).show();
        }

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
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH);


        for (IDeviceData deviceDatum : deviceData) {

            if (deviceDatum.getType().equals(Constants.TYPE_BPM)) {
                BloodPressureData bloodPressureData = (BloodPressureData) deviceDatum;

                stringBuilder.append("SYS: ").append(bloodPressureData.getSystolic()).append(" mmHg, ");
                stringBuilder.append("DIA: ").append(bloodPressureData.getDiastolic()).append(" mmHg, ");
                stringBuilder.append("PUL: ").append(bloodPressureData.getHeartRate()).append(" min\n");
                stringBuilder.append("IHB: ").append(bloodPressureData.getIHB()).append(", ");
                stringBuilder.append("DATE: ").append(sdf.format(bloodPressureData.getDateTime()));
                stringBuilder.append("\n------------------------\n");
                //Insert Data to SQLite DB Start...
                try {
                    dbHelper.insertBPData(
                            bloodPressureData.getSystolic(),
                            bloodPressureData.getDiastolic(),
                            bloodPressureData.getHeartRate(),
                            bloodPressureData.getIHB(),
                            sdf.format(currentTime),
                            SaveSharedPreference.getUserId(getApplicationContext()),
                            sdf.format(bloodPressureData.getDateTime()));
                }catch (Exception e){
                    e.printStackTrace();
                    //showToast(e.message.toString());
                    Log.e("Exception","Error"+e);
                }

                mPushBpData( bloodPressureData.getSystolic(),
                        bloodPressureData.getDiastolic(),
                        bloodPressureData.getHeartRate(),
                        bloodPressureData.getIHB(),
                        sdf.format(currentTime),
                        SaveSharedPreference.getUserId(getApplicationContext()),
                        sdf.format(bloodPressureData.getDateTime()));

                Intent inDash = new Intent(this, ActBpDisplay.class);
                startActivity(inDash);
                finish();

            }  else if (deviceDatum.getType().equals(Constants.TYPE_BGM)) {

                Toast.makeText(this,"Please choose Pressure meter ",Toast.LENGTH_LONG).show();
                finish();

            }
        }

    }

    private void mPushBpData(String systolic, String diastolic, String heartRate, String ihb, String PhoneDateTime, String userId, String systemDateTime) {

        JsonObject job1= new JsonObject();
        JsonObject job2 = new JsonObject();

        job2.addProperty("systolic",systolic);
        job2.addProperty("diastolic",diastolic);
        job2.addProperty("heartRate",heartRate);
        job2.addProperty("ihb",ihb);
        job2.addProperty("PhoneDateTime",PhoneDateTime);
        job2.addProperty("userId",userId);
        job2.addProperty("systemDateTime",systemDateTime);

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
