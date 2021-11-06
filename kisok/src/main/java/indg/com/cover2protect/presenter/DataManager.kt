package indg.com.cover2protect.presenter

interface DataManager : indg.com.cover2protect.presenter.PreferencesHelper {

    fun updateUserInfo(
            accessToken: String,
            userName: String,
            email: String,
            profilePicPath: String)


    fun updateDeviceStatus(DeviceConnectionstatus:String)

}