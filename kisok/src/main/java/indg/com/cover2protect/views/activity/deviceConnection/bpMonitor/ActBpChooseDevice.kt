package indg.com.cover2protect.views.activity.deviceConnection.bpMonitor


import android.bluetooth.BluetoothAdapter
import android.os.Bundle
import android.util.Log
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
import com.google.gson.JsonObject
import indg.com.cover2protect.DB.DBhelper
import indg.com.cover2protect.R
import indg.com.cover2protect.baseAeglOrbs.BpObj
import indg.com.cover2protect.baseAeglOrbs.networking.ApiUtils
import indg.com.cover2protect.views.activity.deviceConnection.Adapter.RViewAdapterBpHistory
import indg.com.cover2protect.views.activity.deviceConnection.OnItemClickListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_device_scanner.*
import kotlinx.android.synthetic.main.toolbar_normal.*
import no.nordicsemi.android.support.v18.scanner.ScanResult
import java.util.*


class ActBpChooseDevice : MedCheckActivity(),
        OnItemClickListener<BleDevice> {

    private val mDeviceHashMap = HashMap<String, BleDevice>()
    private var mRvScanResult: RecyclerView? = null
    private var mBtnStartScan: Button? = null
    private var mBtnStopScan: Button? = null
    private var mBtnGetDBData: Button? = null
    private var mLlProgressLayout: LinearLayout? = null
    private var mScanResultAdapter: AdapterBpDeviceScanner? = null

    private val dbHelper = DBhelper(this)
    val listHistory = ArrayList<BpObj>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_scanner)
        ButterKnife.bind(this)

        tb_normal_title.setText("Blood Pressure Machine")

        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (mBluetoothAdapter == null) {
            showToast("Bluetooth not in device !")
        } else if (!mBluetoothAdapter.isEnabled) {
            showToast("Turn on BLUETOOTH to proceed")
            finish()
        } else {

            initViews()
            requestLocationPermission()
            startScan()
        }


    }

    private fun initViews() {
        iv_tb_normal_back.visibility = View.INVISIBLE
        mLlProgressLayout = findViewById(R.id.llProgressLayout)
        mBtnStopScan = findViewById(R.id.btnStopScan)
        //mBtnStopScan!!.setOnClickListener(this)
        mBtnStartScan = findViewById(R.id.btnStartScan)
        mBtnStartScan!!.visibility = View.GONE
       // mBtnStartScan!!.setOnClickListener(this)
        mBtnGetDBData = findViewById(R.id.btnGetDBData)
       // mBtnGetDBData!!.setOnClickListener(this)


        mScanResultAdapter = AdapterBpDeviceScanner(this)
        mScanResultAdapter!!.setOnItemClickListener(this)
        mRvScanResult = findViewById(R.id.rvScanResult)
        mRvScanResult!!.layoutManager = LinearLayoutManager(this)
        mRvScanResult!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        mRvScanResult!!.adapter = mScanResultAdapter


    }


    override fun onStart() {
        super.onStart()

        rv_bp_history.apply {

            layoutManager = GridLayoutManager(this@ActBpChooseDevice, 1)
            adapter = RViewAdapterBpHistory(listHistory, context)

        }

            listHistory.clear()
            getBPHistoryData()




    }

    private fun getBPHistoryData() {

        val res = dbHelper.allData
        if (res.count == 0) {

          /*  listHistory.add(BpObj("137",
                    "87",
                    "91",
                    "",
                    "Sample"))

            listHistory.add(BpObj("141",
                    "126",
                    "93",
                    "",
                    "Sample"))

            listHistory.add(BpObj("104",
                    "87",
                    "101",
                    "",
                    "Sample"))

            listHistory.add(BpObj("106",
                    "82",
                    "88",
                    "",
                    "Sample"))
*/
            listHistory.clear()
            rv_bp_history.adapter!!.notifyDataSetChanged()


            return
        }

        val buffer = StringBuffer()
        while (res.moveToNext()) {
            buffer.append("ID :" + res.getString(0) + "n")
            buffer.append("SYS_mmHg :" + res.getString(1) + "n")
            buffer.append("DIA_mmHg :" + res.getString(2) + "n")
            buffer.append("PUL :" + res.getString(3) + "nn")
            buffer.append("IHB :" + res.getString(4) + "n")
            buffer.append("Date :" + res.getString(5) + "n")

            val bpObj = BpObj(res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    res.getString(5))

            listHistory.add(bpObj)

        }

        rv_bp_history.adapter!!.notifyDataSetChanged()

    }


    private fun mPushBpData(systolic: String, diastolic: String, heartRate: String, ihb: String, PhoneDateTime: String, userId: String, systemDateTime: String) {
        val job1 = JsonObject()
        val job2 = JsonObject()

        job2.addProperty("systolic", systolic)
        job2.addProperty("diastolic", diastolic)
        job2.addProperty("heartRate", heartRate)
        job2.addProperty("ihb", ihb)
        job2.addProperty("PhoneDateTime", PhoneDateTime)
        job2.addProperty("userId", userId)
        job2.addProperty("systemDateTime", systemDateTime)
        job1.add("Data",job2)

        Log.d("test Job",job1.toString())


        val apiInterface = ApiUtils.getAPIServiceDevice(applicationContext)
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(apiInterface.rfxPostDeviceData(job1)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<JsonObject?>() {
                    override fun onSuccess(jsonObject: JsonObject) {
                        showToast("Successfully pushed data")
                    }

                    override fun onError(e: Throwable) {}
                }))
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
        if(bleDevice.deviceName.equals("SFBPBLE",true)){
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

        ActBpRetriveData.start(this, `object`)

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

        private val TAG = ActBpChooseDevice::class.java.simpleName
    }

}


