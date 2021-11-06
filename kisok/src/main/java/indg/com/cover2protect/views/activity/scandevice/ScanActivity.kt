package indg.com.cover2protect.views.activity.scandevice

import android.Manifest
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import indg.com.cover2protect.databinding.FragmentScanDeviceBinding
import com.ficat.easyble.BleManager
import com.ficat.easyble.BleDevice
import android.widget.Toast
import com.ficat.easypermissions.RequestSubscriber
import com.ficat.easypermissions.EasyPermissions
import com.ficat.easypermissions.Permission
import android.util.Log
import com.ficat.easyble.scan.BleScanCallback
import indg.com.cover2protect.R
import kotlinx.android.synthetic.main.fragment_scan_device.*
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import indg.com.cover2protect.adapter.device_list.devicelistadapter
import indg.com.cover2protect.events.device_select_listener
import indg.com.cover2protect.helper.DeviceHelper
import indg.com.cover2protect.views.activity.device2.HistoryActivity


class ScanActivity : AppCompatActivity(), View.OnClickListener, device_select_listener {
    override fun onSelect(name: String, address: String) {
        devicename = name
        macAddress = address
        if (devicename.startsWith("Y9")) {
            showAlertDialog(R.layout.dialog_device_connect)
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.scan -> {
                ScanDevices()
            }
        }
    }

    private var binding: FragmentScanDeviceBinding? = null
    private var manager: BleManager? = null
    private val deviceList = ArrayList<BleDevice>()
    private var adapter: devicelistadapter? = null
    private val TAG = "Cover2protect"
    private var devicename: String = ""
    private var macAddress: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_scan_device)
        initBleManager()
        showDevicesByRv()
        binding!!.scan.setOnClickListener(this)
    }


    private fun initBleManager() {
        //check if this android device supports ble
        if (!BleManager.supportBle(this)) {
            return
        }
        //open bluetooth without a request dialog
        BleManager.toggleBluetooth(true)

        val options = BleManager.Options()
        options.loggable = true
        options.scanPeriod = 10000
        options.connectTimeout = 10000
        manager = BleManager.getInstance(application)
        manager!!.option(options)
    }


    private fun ScanDevices() {
        if (!BleManager.isBluetoothOn()) {
            BleManager.toggleBluetooth(true)
        }
        val easyPermissions = EasyPermissions(this)
        easyPermissions.requestEach(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(object : RequestSubscriber<Permission> {
                    override fun onPermissionsRequestResult(permission: Permission) {
                        if (permission.granted) {
                            if (manager!!.isScanning) return
                            startScan()
                        } else {
                            if (permission.shouldShowRequestPermissionRationale) {
                                easyPermissions.requestEach(Manifest.permission.ACCESS_COARSE_LOCATION)
                                        .subscribe(this)
                            } else {
                                Toast.makeText(this@ScanActivity,
                                        getString(R.string.locationPermission),
                                        Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                })
    }


    private fun startScan() {
        manager!!.startScan(object : BleScanCallback {
            override fun onLeScan(device: BleDevice, rssi: Int, scanRecord: ByteArray) {
                for (d in deviceList) {
                    if (device.address == d.address) {
                        return
                    }
                }
                if (device.name.startsWith("Y9")) {
                    deviceList.add(device)
                }
                adapter!!.notifyDataSetChanged()
            }

            override fun onStart(startScanSuccess: Boolean, info: String) {
                Log.e("", "start scan = $startScanSuccess   info: $info")
                if (startScanSuccess) {
                    deviceList.clear()
                    adapter!!.notifyDataSetChanged()
                }
            }

            override fun onFinish() {
                Log.e("", "scan finish")
            }
        })
    }


    private fun showDevicesByRv() {
        adapter = devicelistadapter(this, deviceList)
        device_rv.adapter = adapter
        adapter!!.setListener(this)
    }

    private fun SetDeviceIntent(devicename: String?, macAddress: String?) {
        var intent = Intent(this, HistoryActivity::class.java)
        intent.putExtra("name", devicename)
        intent.putExtra("address", macAddress)
        DeviceHelper.Device_name = devicename.toString()
        DeviceHelper.Device_address = macAddress.toString()
        startActivity(intent)
    }


    private fun showAlertDialog(layout: Int) {
        var dialogBuilder = AlertDialog.Builder(this@ScanActivity)
        val layoutView = layoutInflater.inflate(layout, null)
        val dialogButton = layoutView.findViewById<View>(R.id.btnDialog)
        dialogBuilder.setView(layoutView)
        var alertDialog = dialogBuilder.create()
        alertDialog.window.attributes.windowAnimations = R.style.DialogAnimation
        alertDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
        dialogButton.setOnClickListener {
            SetDeviceIntent(devicename, macAddress)
            alertDialog.dismiss()
        }
    }

}
