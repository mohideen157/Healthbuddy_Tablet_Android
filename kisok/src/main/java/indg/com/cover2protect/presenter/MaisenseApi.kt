package indg.com.cover2protect.presenter

import indg.com.cover2protect.model.deviceresponse.DeviceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MaisenseApi {

    @GET("/record")
    fun GetDeviceResponse(@Query("email") email: String,@Query("synched_id") synched_id: String): Call<DeviceResponse>

}