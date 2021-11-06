package com.getmedcheck.activity.sugarMonitor

import android.bluetooth.BluetoothAdapter
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.ButterKnife
import com.getmedcheck.lib.MedCheck
import com.getmedcheck.lib.MedCheckActivity
import com.getmedcheck.lib.model.BleDevice
import indg.com.cover2protect.DB.DBhelper
import indg.com.cover2protect.R
import indg.com.cover2protect.baseAeglOrbs.ObjGlucose
import indg.com.cover2protect.views.activity.deviceConnection.Adapter.RViewAdapterGlucoseHistory
import indg.com.cover2protect.views.activity.deviceConnection.OnItemClickListener
import indg.com.cover2protect.views.activity.deviceConnection.sugarMonitor.ActBgmRetriveData
import indg.com.cover2protect.views.activity.deviceConnection.sugarMonitor.AdapterBgmDeviceScanner
import kotlinx.android.synthetic.main.activity_device_scanner.*
import kotlinx.android.synthetic.main.toolbar_normal.*
import no.nordicsemi.android.support.v18.scanner.ScanResult
import java.util.*


class ActBgmChooseDevice : MedCheckActivity(), OnItemClickListener<BleDevice> {
    private val mDeviceHashMap = HashMap<String, BleDevice>()
    private var mRvScanResult: RecyclerView? = null
    private var mBtnStartScan: Button? = null
    private var mBtnStopScan: Button? = null
    private var mBtnGetDBData: Button? = null
    private var mLlProgressLayout: LinearLayout? = null
    private var mScanResultAdapter: AdapterBgmDeviceScanner? = null

    private val dbHelper = DBhelper(this)


    val listHistory = ArrayList<ObjGlucose>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_scanner)
        ButterKnife.bind(this)

        tb_normal_title.setText("Glucose Machine")

        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (mBluetoothAdapter == null) {
            showToast("Bluetooth not in device !")
        } else if (!mBluetoothAdapter.isEnabled) {
            showToast("Turn on BLUETOOTH to proceed")
            finish()
        } else {
            initViews()
            requestLocationPermission()
        }


    }

    private fun initViews() {
        iv_tb_normal_back.visibility = View.INVISIBLE
        mLlProgressLayout = findViewById(R.id.llProgressLayout)
        mBtnStopScan = findViewById(R.id.btnStopScan)
        //mBtnStopScan!!.setOnClickListener(this)
        mBtnStartScan = findViewById(R.id.btnStartScan)
       // mBtnStartScan!!.setOnClickListener(this)
        mBtnGetDBData = findViewById(R.id.btnGetDBData)
       // mBtnGetDBData!!.setOnClickListener(this)


        mScanResultAdapter = AdapterBgmDeviceScanner(this)
        mScanResultAdapter!!.setOnItemClickListener(this)
        mRvScanResult = findViewById(R.id.rvScanResult)
        mRvScanResult!!.layoutManager = LinearLayoutManager(this)
        mRvScanResult!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        mRvScanResult!!.adapter = mScanResultAdapter

        mBtnStartScan!!.performClick()
        mBtnStartScan!!.visibility = View.GONE

        startScan()

    }


    override fun onStart() {
        super.onStart()
        rv_bp_history.apply {

            layoutManager = GridLayoutManager(this@ActBgmChooseDevice, 1)
            adapter = RViewAdapterGlucoseHistory(listHistory, context)

        }

        tv_ap_bp_history_bpm_averag.text = "MMOL"
        tv_ap_bp_history_low_hig.text = "TYPE"

        listHistory.clear()
        mGetGlucoseHistory()
    }


    private fun mGetGlucoseHistory() {

        val res = dbHelper.allBGMData
        if (res.count == 0) {

            if (res.count == 0) {

              /*  listHistory.add(ObjGlucose(  "Sample Data","2.8","51","0","Random"))
                listHistory.add(ObjGlucose(  "Sample Data","2.4","51","0","Fasting"))
                listHistory.add(ObjGlucose(  "Sample Data","2.6","51","0","PP"))*/

                rv_bp_history.adapter?.notifyDataSetChanged()

            }

            return
        }


        while (res.moveToNext()) {


            listHistory.add(ObjGlucose(res.getString(1),
                                       res.getString(2),
                                        res.getString(3),
                                        res.getString(4),
                                        res.getString(5)
                    ))

        }
        rv_bp_history.adapter?.notifyDataSetChanged()


    }


    override fun onResume() {
        super.onResume()
        registerCallback()

        mBtnStartScan!!.setBackgroundResource(android.R.color.transparent)
        mBtnStopScan!!.setBackgroundResource(android.R.color.transparent)
        mBtnGetDBData!!.setBackgroundResource(android.R.color.transparent)
    }

    override fun onDeviceScanResult(scanResult: ScanResult) {
        super.onDeviceScanResult(scanResult)
        val bleDevice = BleDevice(scanResult.device)
        if(bleDevice.deviceName.equals("SFBGBLE",true)){
            mDeviceHashMap[bleDevice.device.address] = bleDevice
        }


        if (mScanResultAdapter != null) {
            mScanResultAdapter!!.setItems(ArrayList(mDeviceHashMap.values))
            llProgressLayout.visibility = View.INVISIBLE
        }else{
            llProgressLayout.visibility = View.VISIBLE
        }
    }

    override fun onItemClick(view: View, `object`: BleDevice, position: Int) {
        if (mBtnStopScan!!.isClickable) {
            mBtnStopScan!!.performClick()
        }

        ActBgmRetriveData.start(this, `object`)

    }


    override fun startScan() {
        super.startScan()
        mDeviceHashMap.clear()
        if (mScanResultAdapter != null) {
            mScanResultAdapter!!.clear()
        }
        MedCheck.getInstance().startScan(this)
        mBtnStopScan!!.isClickable = true
        mBtnStartScan!!.isClickable = false
        mLlProgressLayout!!.visibility = View.VISIBLE
    }

    override fun onPermissionGrantedAndBluetoothOn() {
        super.onPermissionGrantedAndBluetoothOn()
    }


    companion object {

        private val TAG = ActBgmChooseDevice::class.java.simpleName
    }

}


