package indg.com.cover2protect.adapter.device_list

import android.os.Build

object DeviceUtils {
     val isLeX6: Boolean
        get() = Build.DEVICE.endsWith("le_x6")

     val isDevice2: Boolean
        get() = Build.DEVICE.startsWith("Y9A")

     val isSharpMS1: Boolean
        get() = Build.DEVICE.startsWith("MS1")

     val isHtcM8: Boolean
        get() = Build.DEVICE.startsWith("htc_m8")
}
