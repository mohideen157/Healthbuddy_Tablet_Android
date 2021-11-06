package indg.com.cover2protect.navigator

import indg.com.cover2protect.model.deviceresponse.DeviceResponse

interface DeviceResponseNavigator {

    fun OnError(message:String)
    fun OnSuccess(message:String)
    fun OnData(data:DeviceResponse)
}