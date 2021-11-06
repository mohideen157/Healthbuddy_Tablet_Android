package indg.com.cover2protect.baseAeglOrbs.networking;


import com.google.gson.JsonObject;

import indg.com.cover2protect.views.activity.deviceConnection.fitBitDashBoard.fitbitResp.RespFitbit;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by kural mughil selvam on 08-10-2017.
 */

public interface ApiInterface {

    @GET("user/-/profile.json")
    Single<RespFitbit> rfxFitBitResp(@Header("Authorization") String token);

    @POST("api/device-json")
    Single<JsonObject> rfxPostDeviceData(@Body JsonObject DeviceJsonData);



}