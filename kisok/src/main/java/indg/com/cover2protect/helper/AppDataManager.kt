package indg.com.cover2protect.helper

import indg.com.cover2protect.presenter.DataManager
import indg.com.cover2protect.presenter.PreferencesHelper
import javax.inject.Inject

class AppDataManager : indg.com.cover2protect.presenter.DataManager {


    override fun updateDeviceStatus(DeviceConnectionstatus: String) {
        this.DeviceConnectionstatus = DeviceConnectionstatus
    }

    override var DeviceConnectionstatus: String
        get() = mPreferencesHelper!!.DeviceConnectionstatus
        set(value) {
            mPreferencesHelper!!.DeviceConnectionstatus = value

        }

    private var mPreferencesHelper: indg.com.cover2protect.presenter.PreferencesHelper? = null

    @Inject
    constructor(preferencesHelper: indg.com.cover2protect.presenter.PreferencesHelper)
    {
        mPreferencesHelper = preferencesHelper
    }






    override fun updateUserInfo(AccessToken: String, userName: String, email: String, profilePicPath: String) {
        accessToken = AccessToken
        currentUserEmail = email
        currentUserName = userName
        currentUserProfilePicUrl = profilePicPath

    }

    override var accessToken: String
        get() = mPreferencesHelper!!.accessToken
        set(accesstoken) {

            mPreferencesHelper!!.accessToken = accesstoken

        }

    override var currentUserEmail: String
        get() = mPreferencesHelper!!.currentUserEmail
        set(email) {
            mPreferencesHelper!!.currentUserEmail = email
        }


    override var currentUserName: String
        get() = mPreferencesHelper!!.currentUserName
        set(value) {

            mPreferencesHelper!!.currentUserName = value
        }
    override var currentUserProfilePicUrl: String
        get() = mPreferencesHelper!!.currentUserProfilePicUrl
        set(value) {

            mPreferencesHelper!!.currentUserProfilePicUrl = value
        }
}