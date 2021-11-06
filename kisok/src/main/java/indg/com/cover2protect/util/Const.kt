package indg.com.cover2protect.util

import indg.com.cover2protect.data.database.datasource.DeviceRepository
import indg.com.cover2protect.data.database.local.DeviceDatabase

const val VIEW_SCALE_FACTOR = .6f
const val DURATION_500: Long = 500
const val BASE_URL: String = "http://admin.myaibud.com/"
//const val BASE_URL: String = "http://192.168.1.63:90/"
const val Medical_BASE_URL: String = "http://c2phealthcareapi.com/"
//const val BASE_URL: String = "http://192.168.1.128/cover-2-protect/public/"
const val NutrionURL: String = "https://trackapi.nutritionix.com"
const val MaisenseURL: String = "https://us-central1-freeview-d955b.cloudfunctions.net"
val PREF_NAME = "Cover2Protect"
const val LOGGED_IN_PREF = "logged_in_status"
const val DEVICE_STATUS = "device_status"
const val DEVICE2_STATUS = "device2_status"
const val FIRSTTIME = "device_status"
const val NODEVICE = "nodevice_status"
const val MOBILE = "mobile"
const val USER_ID = "user_id"
const val AWS_BUCKET = "cp2pdfupload2read"
lateinit var deviceDatabase:DeviceDatabase
lateinit var deviceRepository:DeviceRepository
const val RC_FILE_PICKER_PERM = 321
const val DEVICE_DATA = "api/device-json"
