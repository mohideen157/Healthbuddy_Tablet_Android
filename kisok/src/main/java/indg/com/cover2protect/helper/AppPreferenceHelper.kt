package indg.com.cover2protect.helper

import android.content.Context
import android.content.SharedPreferences
import indg.com.cover2protect.dagger.PreferenceInfo
import indg.com.cover2protect.presenter.PreferencesHelper
import javax.inject.Inject

class AppPreferenceHelper : indg.com.cover2protect.presenter.PreferencesHelper {

    override var DeviceConnectionstatus: String
        get() = mPrefs!!.getString(PREF_KEY_CURRENT_DEVICE_CONNECTION_STATUS, null)
        set(value) {
            mPrefs!!.edit().putString(PREF_KEY_CURRENT_DEVICE_CONNECTION_STATUS, value).apply()
        }


    private val PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN"

    private val PREF_KEY_CURRENT_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL"


    private val PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME"

    private val PREF_KEY_CURRENT_USER_PROFILE_PIC_URL = "PREF_KEY_CURRENT_USER_PROFILE_PIC_URL"
    private val PREF_KEY_CURRENT_DEVICE_CONNECTION_STATUS = "PREF_KEY_CURRENT_DEVICE_CONNECTION_STATUS"
    private var mPrefs: SharedPreferences? = null

    @Inject
    constructor(context: Context, @PreferenceInfo prefFileName: String){
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)
    }



    override var accessToken: String
        get() = mPrefs!!.getString(PREF_KEY_ACCESS_TOKEN, null)
        set(value) {
            mPrefs!!.edit().putString(PREF_KEY_ACCESS_TOKEN, value).apply()
        }

    override var currentUserEmail: String
        get() = mPrefs!!.getString(PREF_KEY_CURRENT_USER_EMAIL, null)
        set(value) {
            mPrefs!!.edit().putString(PREF_KEY_CURRENT_USER_EMAIL, value).apply()
        }


    override var currentUserName: String
        get() = mPrefs!!.getString(PREF_KEY_CURRENT_USER_NAME, null);
        set(value) {
            mPrefs!!.edit().putString(PREF_KEY_CURRENT_USER_NAME, value).apply()
        }

    override var currentUserProfilePicUrl: String
        get() = mPrefs!!.getString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, null);
        set(value) {

            mPrefs!!.edit().putString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, value).apply()
        }
}