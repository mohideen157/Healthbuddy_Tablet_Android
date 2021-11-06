package indg.com.cover2protect.views.activity.device2

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.RemoteException
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sxr.sdk.ble.keepfit.aidl.IRemoteService
import com.sxr.sdk.ble.keepfit.aidl.IServiceCallback
import indg.com.cover2protect.R
import indg.com.cover2protect.SampleBleService
import indg.com.cover2protect.repository.SysUtils
import kotlinx.android.synthetic.main.activity_device_scan_result.*
import kotlinx.android.synthetic.main.toolbar.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DeviceScanResultActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.unbind_btn -> {
                if (mIsBound) {
                    if(mService!=null){
                        try {
                            mService!!.unregisterCallback(mServiceCallback)
                        } catch (e: RemoteException) {
                            e.printStackTrace()
                        }
                        unbindService(mServiceConnection)
                        mIsBound = false
                    }
                }

            }
            R.id.devicesearch ->{
                callSet_vir()
            }

        }
    }

    private var mService: IRemoteService? = null
    private var mIsBound = false
    private val countStep = 0
    private val data = ""
    private var llConnect: LinearLayout? = null
    private var deviceName: String = ""
    private var macAddress: String = ""
    private val pathLog = "/jyClient/log/"
    private val bSave = true

    private var sleepcount = 0
    private var bStart = false

    private var updateConnectStateHandler = Handler(Handler.Callback { msg ->
        val data = msg.data
        val state = data.getInt("state")
        if (state == 2) {

        } else {

        }
        true
    })

    private var bColor = false
    private val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())


    protected var curMac: String? = null

    private var bOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_scan_result)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp)
        toolbar.setNavigationOnClickListener { arrow -> onBackPressed() }
        val gattServiceIntent = Intent(this,
                SampleBleService::class.java)
        gattServiceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startService(gattServiceIntent)
        getIntentData()
        initView()

        bloodpressure_btn.setOnClickListener { callRemoteOpenBlood(true) }
        bloodpressureoff_btn.setOnClickListener { callRemoteOpenBlood(false) }

    }

    private val messageHandler = Handler(Handler.Callback { msg ->
        val data = msg.data
        val title = data.getString("title")
        val content = data.getString("content")

        Log.i(TAG, "$title: $content")
        when (title) {
            else -> {
                var text = "[" + sdf.format(Date()) + "] " + title + "\n" + content
                Toast.makeText(this, text, Toast.LENGTH_LONG).show()

            }
        }
        true
    })

    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Toast.makeText(this@DeviceScanResultActivity, "Service connected", Toast.LENGTH_SHORT).show()

            mService = IRemoteService.Stub.asInterface(service)
            try {
                mService!!.registerCallback(mServiceCallback)
                mService!!.openSDKLog(bSave, pathLog, "blue.log")

                val isConnected = callRemoteIsConnected()

                if (!isConnected) {

                } else {
                    val authrize = callRemoteIsAuthrize()
                    if (authrize == 200) {
                        val curMac = callRemoteGetConnectedDevice()

                    }
                }

            } catch (e: RemoteException) {
                e.printStackTrace()
            }

        }

        override fun onServiceDisconnected(name: ComponentName) {
            Toast.makeText(this@DeviceScanResultActivity, "Service disconnected", Toast.LENGTH_SHORT).show()

            mService = null
        }
    }

    private val mServiceCallback = object : IServiceCallback.Stub() {
        @Throws(RemoteException::class)
        override fun onConnectStateChanged(state: Int) {
            showToast("onConnectStateChanged", "$curMac state $state")
              updateConnectState(state)
        }


        @Throws(RemoteException::class)
        override fun onScanCallback(deviceName: String, deviceMacAddress: String, rssi: Int) {
            Log.i(TAG, String.format("onScanCallback <%1\$s>[%2\$s](%3\$d)", deviceName, deviceMacAddress, rssi))

        }


        @Throws(RemoteException::class)
        override fun onSetNotify(result: Int) {
            showToast("onSetNotify", result.toString())
        }

        @Throws(RemoteException::class)
        override fun onSetUserInfo(result: Int) {
            showToast("onSetUserInfo", "" + result)
        }

        @Throws(RemoteException::class)
        override fun onAuthSdkResult(errorCode: Int) {
            if (errorCode == 200) {
                callRemoteConnect(deviceName,macAddress)
                name.text = deviceName
                mac.text = "MAC:" + macAddress
               // callRemoteGetDeviceInfo()
            }
            showToast("onAuthSdkResult", errorCode.toString() + "")
        }

        @Throws(RemoteException::class)
        override fun onGetDeviceTime(result: Int, time: String) {
            showToast("onGetDeviceTime", time)
        }

        @Throws(RemoteException::class)
        override fun onSetDeviceTime(arg0: Int) {
            showToast("onSetDeviceTime", arg0.toString() + "")
        }

        @Throws(RemoteException::class)
        override fun onSetDeviceInfo(arg0: Int) {
            showToast("onSetDeviceInfo", arg0.toString() + "")
        }


        @Throws(RemoteException::class)
        override fun onAuthDeviceResult(arg0: Int) {
            showToast("onAuthDeviceResult", arg0.toString() + "")
        }


        @Throws(RemoteException::class)
        override fun onSetAlarm(arg0: Int) {
            showToast("onSetAlarm", arg0.toString() + "")
        }

        @Throws(RemoteException::class)
        override fun onSendVibrationSignal(arg0: Int) {
            showToast("onSendVibrationSignal", "result:$arg0")
        }

        @Throws(RemoteException::class)
        override fun onGetDeviceBatery(arg0: Int, arg1: Int) {
            showToast("onGetDeviceBatery", "batery:$arg0, statu $arg1")
        }


        @Throws(RemoteException::class)
        override fun onSetDeviceMode(arg0: Int) {
            showToast("onSetDeviceMode", "result:$arg0")
        }

        @Throws(RemoteException::class)
        override fun onSetHourFormat(arg0: Int) {
            showToast("onSetHourFormat ", "result:$arg0")

        }

        @Throws(RemoteException::class)
        override fun setAutoHeartMode(arg0: Int) {
            showToast("setAutoHeartMode ", "result:$arg0")
        }


        @Throws(RemoteException::class)
        override fun onGetCurSportData(type: Int, timestamp: Long, step: Int, distance: Int,
                                       cal: Int, cursleeptime: Int, totalrunningtime: Int, steptime: Int) {
            val date = Date(timestamp * 1000)
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val time = sdf.format(date)
            showToast("onGetCurSportData", "type : $type , time :$time , step: $step, distance :$distance, cal :$cal, cursleeptime :$cursleeptime, totalrunningtime:$totalrunningtime")


        }

        @Throws(RemoteException::class)
        override fun onGetSenserData(result: Int, timestamp: Long, heartrate: Int, sleepstatu: Int) {
            val date = Date(timestamp * 1000)
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val time = sdf.format(date)
            showToast("onGetSenserData", "result: $result,time:$time,heartrate:$heartrate,sleepstatu:$sleepstatu")

        }


        @Throws(RemoteException::class)
        override fun onGetDataByDay(type: Int, timestamp: Long, step: Int, heartrate: Int) {
            val date = Date(timestamp * 1000)
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val recorddate = sdf.format(date)
            showToast("onGetDataByDay", "type:$type,time::$recorddate,step:$step,heartrate:$heartrate")
            if (type == 2) {
                sleepcount++
            }
        }

        @Throws(RemoteException::class)
        override fun onGetDataByDayEnd(type: Int, timestamp: Long) {
            val date = Date(timestamp * 1000)
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val recorddate = sdf.format(date)
            showToast("onGetDataByDayEnd", "time:$recorddate,sleepcount:$sleepcount")
            sleepcount = 0
        }


        @Throws(RemoteException::class)
        override fun onSetPhontMode(arg0: Int) {
            showToast("onSetPhontMode", "result:$arg0")
        }


        @Throws(RemoteException::class)
        override fun onSetSleepTime(arg0: Int) {
            showToast("onSetSleepTime", "result:$arg0")
        }


        @Throws(RemoteException::class)
        override fun onSetIdleTime(arg0: Int) {
            showToast("onSetIdleTime", "result:$arg0")
        }


        @Throws(RemoteException::class)
        override fun onGetDeviceInfo(version: Int, macaddress: String, vendorCode: String,
                                     productCode: String, result: Int) {
            showToast("onGetDeviceInfo", "version :$version,macaddress : $macaddress,vendorCode : $vendorCode,productCode :$productCode , CRCresult :$result")

        }

        @Throws(RemoteException::class)
        override fun onGetDeviceAction(type: Int) {
            showToast("onGetDeviceAction", "type:$type")
        }


        @Throws(RemoteException::class)
        override fun onGetBandFunction(result: Int, results: BooleanArray) {
            showToast("onGetBandFunction", "result : " + result + ", results :" + results.size)

        }

        @Throws(RemoteException::class)
        override fun onSetLanguage(arg0: Int) {
            showToast("onSetLanguage", "result:$arg0")
        }


        @Throws(RemoteException::class)
        override fun onSendWeather(arg0: Int) {
            showToast("onSendWeather", "result:$arg0")
        }


        @Throws(RemoteException::class)
        override fun onSetAntiLost(arg0: Int) {
            showToast("onSetAntiLost", "result:$arg0")

        }


        @Throws(RemoteException::class)
        override fun onReceiveSensorData(arg0: Int, arg1: Int, arg2: Int, arg3: Int,
                                         arg4: Int) {
            showToast("onReceiveSensorData", "result:$arg0 , $arg1 , $arg2 , $arg3 , $arg4")
        }


        @Throws(RemoteException::class)
        override fun onSetBloodPressureMode(arg0: Int) {
            showToast("onSetBloodPressureMode", "result:$arg0")
        }


        @Throws(RemoteException::class)
        override fun onGetMultipleSportData(flag: Int, recorddate: String, mode: Int, value: Int) {
            //            Date date = new Date(timestamp * 1000);
            //            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            //            String recorddate = sdf.format(date);
            showToast("onGetMultipleSportData", "flag:$flag , mode :$mode recorddate:$recorddate , value :$value")
        }


        @Throws(RemoteException::class)
        override fun onSetGoalStep(result: Int) {
            showToast("onSetGoalStep", "result:$result")
        }


        @Throws(RemoteException::class)
        override fun onSetDeviceHeartRateArea(result: Int) {
            showToast("onSetDeviceHeartRateArea", "result:$result")
        }


        @Throws(RemoteException::class)
        override fun onSensorStateChange(type: Int, state: Int) {

            showToast("onSensorStateChange", "type:$type , state : $state")
        }

        @Throws(RemoteException::class)
        override fun onReadCurrentSportData(mode: Int, time: String, step: Int,
                                            cal: Int) {

            showToast("onReadCurrentSportData", "mode:$mode , time : $time , step : $step cal :$cal")
        }

        @Throws(RemoteException::class)
        override fun onGetOtaInfo(isUpdate: Boolean, version: String, path: String) {
            showToast("onGetOtaInfo", "isUpdate $isUpdate version $version path $path")
        }

        @Throws(RemoteException::class)
        override fun onGetOtaUpdate(step: Int, progress: Int) {
            showToast("onGetOtaUpdate", "step $step progress $progress")
        }

        @Throws(RemoteException::class)
        override fun onSetDeviceCode(result: Int) {
            showToast("onSetDeviceCode", "result $result")
        }

        @Throws(RemoteException::class)
        override fun onGetDeviceCode(bytes: ByteArray) {
            showToast("onGetDeviceCode", "bytes " + SysUtils.printHexString(bytes))
        }

        @Throws(RemoteException::class)
        override fun onCharacteristicChanged(uuid: String, bytes: ByteArray) {
            showToast("onCharacteristicChanged", uuid + " " + SysUtils.printHexString(bytes))
        }

        @Throws(RemoteException::class)
        override fun onCharacteristicWrite(uuid: String, bytes: ByteArray, status: Int) {
            showToast("onCharacteristicWrite", status.toString() + " " + uuid + " " + SysUtils.printHexString(bytes))
        }
    }

    private fun getIntentData() {
        if (!intent.getStringExtra("name").isNullOrEmpty()) {
            deviceName = intent.getStringExtra("name")
        }

        if (!intent.getStringExtra("address").isNullOrEmpty()) {
            macAddress = intent.getStringExtra("address")
        }
    }

    private fun initView() {
        unbind_btn.setOnClickListener(this)
        devicesearch.setOnClickListener(this)
        val intent = Intent(IRemoteService::class.java!!.getName())
        intent.setClassName("com.sxr.sdk.ble.keepfit.client", "com.sxr.sdk.ble.keepfit.client.SampleBleService");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
        mIsBound = true




    }

    protected fun updateConnectState(state: Int) {
        val msg = Message()
        val data = Bundle()
        data.putInt("state", state)
        msg.data = data
        updateConnectStateHandler.sendMessage(msg)
    }

    protected fun showToast(title: String, content: String) {
        val file = "demo.log"
        if (bSave)
            SysUtils.writeTxtToFile("$title -> $content", pathLog, file)
        val msg = Message()
        val data = Bundle()
        data.putString("title", title)
        data.putString("content", content)
        msg.data = data
        messageHandler.sendMessage(msg)
    }

    private fun callRemoteIsConnected(): Boolean {
        var isConnected = false
        if (mService != null) {
            try {
                isConnected = mService!!.isConnectBt
            } catch (e: RemoteException) {
                e.printStackTrace()
                Toast.makeText(this, "Remote call error!", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(this, "Service is not available yet!", Toast.LENGTH_SHORT).show()
        }

        return isConnected
    }

    private fun callRemoteGetConnectedDevice(): String {
        var deviceMac = ""
        if (mService != null) {
            try {
                deviceMac = mService!!.connectedDevice
            } catch (e: RemoteException) {
                e.printStackTrace()
                Toast.makeText(this, "Remote call error!", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(this, "Service is not available yet!", Toast.LENGTH_SHORT).show()
        }

        return deviceMac
    }

    private fun callRemoteConnect(name: String?, mac: String?) {
        if (mac == null || mac.length == 0) {
            Toast.makeText(this, "ble device mac address is not correctly!", Toast.LENGTH_SHORT).show()
            return
        }

        if (mService != null) {
            try {
                mService!!.connectBt(name, mac)
            } catch (e: RemoteException) {
                e.printStackTrace()
                Toast.makeText(this, "Remote call error!", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(this, "Service is not available yet!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun callRemoteIsAuthrize(): Int {
        var isAuthrize = 0
        if (mService != null) {
            try {
                isAuthrize = mService!!.isAuthrize
            } catch (e: RemoteException) {
                e.printStackTrace()
                Toast.makeText(this, "Remote call error!", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(this, "Service is not available yet!", Toast.LENGTH_SHORT).show()
        }

        return isAuthrize
    }

    private fun callRemoteGetDeviceInfo() {
        val result: Int
        if (mService != null) {
            try {
                result = mService!!.getDeviceInfo()
            } catch (e: RemoteException) {
                e.printStackTrace()
                Toast.makeText(this, "Remote call error!", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(this, "Service is not available yet!", Toast.LENGTH_SHORT).show()
        }
    }


    private fun callRemoteOpenBlood(enable: Boolean) {
        Log.i(TAG, "callRemoteGetMutipleData")
        val result: Int
        if (mService != null) {
            try {
                result = mService!!.setBloodPressureMode(enable)
            } catch (e: RemoteException) {
                e.printStackTrace()
                Toast.makeText(this, "Remote call error!", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(this, "Service is not available yet!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun callSet_vir() {
        val result: Int
        if (mService != null) {
            try {
                result = mService!!.sendVibrationSignal(5)
                Log.d("","")
            } catch (e: RemoteException) {
                e.printStackTrace()
                Toast.makeText(this, "Remote call error!", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(this, "Service is not available yet!", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {

        private val TAG = DeviceScanResultActivity::class.java!!.getSimpleName()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

    }

}

