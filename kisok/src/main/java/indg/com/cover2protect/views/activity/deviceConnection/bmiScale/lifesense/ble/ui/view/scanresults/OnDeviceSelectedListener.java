/**
 * 
 */
package indg.com.cover2protect.views.activity.deviceConnection.bmiScale.lifesense.ble.ui.view.scanresults;

import android.bluetooth.BluetoothDevice;

import com.lifesense.ble.bean.LsDeviceInfo;

/**
 * @author CaiChiXiang
 *
 */

public interface OnDeviceSelectedListener {
	/**
	 * Fired when user selected the device.
	 * 
	 * @param device
	 *            the device to connect to
	 * @param name
	 *            the device name. Unfortunately on some devices {@link BluetoothDevice#getName()} always returns <code>null</code>, f.e. Sony Xperia Z1 (C6903) with Android 4.3. The name has to
	 *            be parsed manually form the Advertisement packet.
	 */
	 void onDeviceSelected(LsDeviceInfo device);

	/**
	 * Fired when scanner dialog has been cancelled without selecting a device.
	 */
	 void onDialogCanceled();
	 
	 void onStopSearch();
}
