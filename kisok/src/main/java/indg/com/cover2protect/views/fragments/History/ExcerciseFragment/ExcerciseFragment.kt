package indg.com.cover2protect.views.fragments.History.ExcerciseFragment


import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.sxr.sdk.ble.keepfit.aidl.IRemoteService
import com.sxr.sdk.ble.keepfit.aidl.IServiceCallback
import indg.com.cover2protect.BR

import indg.com.cover2protect.R
import indg.com.cover2protect.SampleBleService
import indg.com.cover2protect.base.BaseFragmentBinding
import indg.com.cover2protect.databinding.FragmentExcerciseBinding
import indg.com.cover2protect.helper.DeviceHelper
import indg.com.cover2protect.repository.SysUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class ExcerciseFragment : BaseFragmentBinding<FragmentExcerciseBinding>(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.start_btn ->{
                callRemoteOpenBlood(true)
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


    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.fragment_excercise


    private var binding: FragmentExcerciseBinding? = null

    companion object {
        fun newInstance(): Fragment {
            return ExcerciseFragment()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding
        activity!!.title = "Blood Pressure"
        val gattServiceIntent = Intent(activity!!,
                SampleBleService::class.java)
        gattServiceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activity!!.startService(gattServiceIntent)
        getIntentData()
        initView()
        binding!!.startBtn.setOnClickListener(this)

    }

    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Toast.makeText(activity!!, "Service connected", Toast.LENGTH_SHORT).show()

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
            Toast.makeText(activity!!, "Service disconnected", Toast.LENGTH_SHORT).show()

            mService = null
        }
    }

    private val mServiceCallback = object : IServiceCallback.Stub() {
        @Throws(RemoteException::class)
        override fun onConnectStateChanged(state: Int) {
            updateConnectState(state)
            callgetCurSportData()
        }


        @Throws(RemoteException::class)
        override fun onScanCallback(deviceName: String, deviceMacAddress: String, rssi: Int) {

        }


        @Throws(RemoteException::class)
        override fun onSetNotify(result: Int) {
        }

        @Throws(RemoteException::class)
        override fun onSetUserInfo(result: Int) {
        }

        @Throws(RemoteException::class)
        override fun onAuthSdkResult(errorCode: Int) {
            if (errorCode == 200) {
                callRemoteConnect(deviceName, macAddress)
            }
        }

        @Throws(RemoteException::class)
        override fun onGetDeviceTime(result: Int, time: String) {
        }

        @Throws(RemoteException::class)
        override fun onSetDeviceTime(arg0: Int) {
        }

        @Throws(RemoteException::class)
        override fun onSetDeviceInfo(arg0: Int) {
        }


        @Throws(RemoteException::class)
        override fun onAuthDeviceResult(arg0: Int) {
        }


        @Throws(RemoteException::class)
        override fun onSetAlarm(arg0: Int) {
        }

        @Throws(RemoteException::class)
        override fun onSendVibrationSignal(arg0: Int) {
        }

        @Throws(RemoteException::class)
        override fun onGetDeviceBatery(arg0: Int, arg1: Int) {
        }


        @Throws(RemoteException::class)
        override fun onSetDeviceMode(arg0: Int) {
        }

        @Throws(RemoteException::class)
        override fun onSetHourFormat(arg0: Int) {

        }

        @Throws(RemoteException::class)
        override fun setAutoHeartMode(arg0: Int) {
        }


        @Throws(RemoteException::class)
        override fun onGetCurSportData(type: Int, timestamp: Long, step: Int, distance: Int,
                                       cal: Int, cursleeptime: Int, totalrunningtime: Int, steptime: Int) {
            val date = Date(timestamp * 1000)
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val time = sdf.format(date)
        }

        @Throws(RemoteException::class)
        override fun onGetSenserData(result: Int, timestamp: Long, heartrate: Int, sleepstatu: Int) {
            val date = Date(timestamp * 1000)
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val time = sdf.format(date)
            binding!!.heartrate.setStepCountText(heartrate.toString())

        }


        @Throws(RemoteException::class)
        override fun onGetDataByDay(type: Int, timestamp: Long, step: Int, heartrate: Int) {
            val date = Date(timestamp * 1000)
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val recorddate = sdf.format(date)

            if (type == 2) {
                sleepcount++
            }
        }

        @Throws(RemoteException::class)
        override fun onGetDataByDayEnd(type: Int, timestamp: Long) {
            val date = Date(timestamp * 1000)
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val recorddate = sdf.format(date)
            sleepcount = 0
        }


        @Throws(RemoteException::class)
        override fun onSetPhontMode(arg0: Int) {
        }


        @Throws(RemoteException::class)
        override fun onSetSleepTime(arg0: Int) {
        }


        @Throws(RemoteException::class)
        override fun onSetIdleTime(arg0: Int) {
        }


        @Throws(RemoteException::class)
        override fun onGetDeviceInfo(version: Int, macaddress: String, vendorCode: String,
                                     productCode: String, result: Int) {

        }

        @Throws(RemoteException::class)
        override fun onGetDeviceAction(type: Int) {
        }


        @Throws(RemoteException::class)
        override fun onGetBandFunction(result: Int, results: BooleanArray) {

        }

        @Throws(RemoteException::class)
        override fun onSetLanguage(arg0: Int) {
            callgetCurSportData()
        }


        @Throws(RemoteException::class)
        override fun onSendWeather(arg0: Int) {
        }


        @Throws(RemoteException::class)
        override fun onSetAntiLost(arg0: Int) {

        }


        @Throws(RemoteException::class)
        override fun onReceiveSensorData(arg0: Int, arg1: Int, arg2: Int, arg3: Int,
                                         arg4: Int) {
            binding!!.heartrate.setStepCountText("$arg1/$arg2")
            binding!!.bloodpressure.text = "$arg1/$arg2 mmHg"
            binding!!.bloodoxygen.text = "$arg3 %"
            binding!!.degree.text = "$arg4"
        }


        @Throws(RemoteException::class)
        override fun onSetBloodPressureMode(arg0: Int) {
        }


        @Throws(RemoteException::class)
        override fun onGetMultipleSportData(flag: Int, recorddate: String, mode: Int, value: Int) {
        }


        @Throws(RemoteException::class)
        override fun onSetGoalStep(result: Int) {
            showToast("Target", "Target Set")

        }


        @Throws(RemoteException::class)
        override fun onSetDeviceHeartRateArea(result: Int) {
        }


        @Throws(RemoteException::class)
        override fun onSensorStateChange(type: Int, state: Int) {

        }

        @Throws(RemoteException::class)
        override fun onReadCurrentSportData(mode: Int, time: String, step: Int,
                                            cal: Int) {

        }

        @Throws(RemoteException::class)
        override fun onGetOtaInfo(isUpdate: Boolean, version: String, path: String) {
        }

        @Throws(RemoteException::class)
        override fun onGetOtaUpdate(step: Int, progress: Int) {
        }

        @Throws(RemoteException::class)
        override fun onSetDeviceCode(result: Int) {
        }

        @Throws(RemoteException::class)
        override fun onGetDeviceCode(bytes: ByteArray) {
        }

        @Throws(RemoteException::class)
        override fun onCharacteristicChanged(uuid: String, bytes: ByteArray) {
        }

        @Throws(RemoteException::class)
        override fun onCharacteristicWrite(uuid: String, bytes: ByteArray, status: Int) {
        }
    }


    private fun initView() {
        val intent = Intent(IRemoteService::class.java!!.getName())
        intent.setClassName("com.sxr.sdk.ble.keepfit.client", "com.sxr.sdk.ble.keepfit.client.SampleBleService");
        activity!!.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
        mIsBound = true
    }

    private fun getIntentData() {
        deviceName = DeviceHelper.Device_name
        macAddress = DeviceHelper.Device_address
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

    private val messageHandler = Handler(Handler.Callback { msg ->
        val data = msg.data
        val title = data.getString("title")
        val content = data.getString("content")

        Log.i("", "$title: $content")
        when (title) {
            else -> {
                var text = "[" + sdf.format(Date()) + "] " + title + "\n" + content
                Toast.makeText(activity!!, text, Toast.LENGTH_LONG).show()

            }
        }
        true
    })

    private fun callRemoteIsConnected(): Boolean {
        var isConnected = false
        if (mService != null) {
            try {
                isConnected = mService!!.isConnectBt
            } catch (e: RemoteException) {
                e.printStackTrace()
                Toast.makeText(activity!!, "Remote call error!", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(activity!!, "Service is not available yet!", Toast.LENGTH_SHORT).show()
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
                Toast.makeText(activity!!, "Remote call error!", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(activity!!, "Service is not available yet!", Toast.LENGTH_SHORT).show()
        }

        return deviceMac
    }

    private fun callRemoteConnect(name: String?, mac: String?) {
        if (mac == null || mac.length == 0) {
            Toast.makeText(activity!!, "ble device mac address is not correctly!", Toast.LENGTH_SHORT).show()
            return
        }

        if (mService != null) {
            try {
                mService!!.connectBt(name, mac)
            } catch (e: RemoteException) {
                e.printStackTrace()
                Toast.makeText(activity!!, "Remote call error!", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(activity!!, "Service is not available yet!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun callRemoteIsAuthrize(): Int {
        var isAuthrize = 0
        if (mService != null) {
            try {
                isAuthrize = mService!!.isAuthrize
            } catch (e: RemoteException) {
                e.printStackTrace()
                Toast.makeText(activity!!, "Remote call error!", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(activity!!, "Service is not available yet!", Toast.LENGTH_SHORT).show()
        }

        return isAuthrize
    }


    private fun callRemoteOpenBlood(enable: Boolean) {
        Log.i("", "callRemoteGetMutipleData")
        val result: Int
        if (mService != null) {
            try {
                result = mService!!.setBloodPressureMode(enable)
            } catch (e: RemoteException) {
                e.printStackTrace()
                Toast.makeText(activity!!, "Remote call error!", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(activity!!, "Service is not available yet!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun callRemoteSetHeartRateMode(enable: Boolean) {
        val result: Int
        if (mService != null) {
            try {
                result = mService!!.setHeartRateMode(enable, 60)
            } catch (e: RemoteException) {
                e.printStackTrace()
                Toast.makeText(activity!!, "Remote call error!", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(activity!!, "Service is not available yet!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun callgetCurSportData() {
        val result: Int
        if (mService != null) {
            try {
                result = mService!!.getCurSportData()
            } catch (e: RemoteException) {
                e.printStackTrace()
                Toast.makeText(activity!!, "Remote call error!", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(activity!!, "Service is not available yet!", Toast.LENGTH_SHORT).show()
        }
    }


}
