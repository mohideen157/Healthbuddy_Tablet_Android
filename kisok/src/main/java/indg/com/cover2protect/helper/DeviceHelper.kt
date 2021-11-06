package indg.com.cover2protect.helper

import android.content.Context
import android.content.SharedPreferences


object DeviceHelper {
    private const val NAME = "Cover2Protect"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences
    private val DeviceConnectionStatus = Pair("status", false)
    private val Rememberme = Pair("status", false)

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }


    var Device_name: String
        get() = preferences.getString("device_name", "")
        set(value) = preferences.edit {
            it.putString("device_name", value)
        }

    var Device_address: String
        get() = preferences.getString("device_address", "")
        set(value) = preferences.edit {
            it.putString("device_address", value)
        }


    var DeviceStatus: Boolean
        get() = preferences.getBoolean(DeviceConnectionStatus.first, DeviceConnectionStatus.second)
        set(value) = preferences.edit {
            it.putBoolean(DeviceConnectionStatus.first, value)
        }

    var Rememberstatus: Boolean
        get() = preferences.getBoolean(Rememberme.first, Rememberme.second)
        set(value) = preferences.edit {
            it.putBoolean(Rememberme.first, value)
        }

    var Calories: String
        get() = preferences.getString("cal", "")
        set(value) = preferences.edit {
            it.putString("cal", value)
        }

    var calburned: String
        get() = preferences.getString("calburned", "")
        set(value) = preferences.edit {
            it.putString("calburned", value)
        }

    var deviceuser: String?
        get() = preferences.getString("userdata", "")
        set(value) = preferences.edit {
            it.putString("userdata", value)
        }
    var token: String?
        get() = preferences.getString("token", "")
        set(value) = preferences.edit {
            it.putString("token", value)
        }

    var gender: String?
        get() = preferences.getString("gender", "")
        set(value) = preferences.edit {
            it.putString("gender", value)
        }

    var steps: String?
        get() = preferences.getString("steps", "")
        set(value) = preferences.edit {
            it.putString("steps", value)
        }

    var date: String?
        get() = preferences.getString("date", "")
        set(value) = preferences.edit {
            it.putString("date", value)
        }

    var pastdate: String?
        get() = preferences.getString("pastdate", "")
        set(value) = preferences.edit {
            it.putString("pastdate", value)
        }

    fun Clear(){
        val editor = preferences.edit()
        editor.clear()
        editor.commit()
    }
}