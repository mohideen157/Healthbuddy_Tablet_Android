package indg.com.cover2protect.presenter

import indg.com.cover2protect.model.profile.Profile

interface Profile_Data_Response {

    fun onResponse(data: Profile)
    fun onError(msg:String)

}