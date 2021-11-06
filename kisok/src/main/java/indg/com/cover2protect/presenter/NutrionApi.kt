package indg.com.cover2protect.presenter

import indg.com.cover2protect.model.excercisemodel.ExcerciseModelResponse
import indg.com.cover2protect.model.MedicalReportResponse
import indg.com.cover2protect.model.nutrition.nutitionmodel.NutritionModelResponse
import indg.com.cover2protect.model.nutrition.nutritiongetresponse.NutritionGetResponse
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface NutrionApi {



    @GET("/v2/search/instant")
    fun GetNutrionInstant(@HeaderMap headerData: HashMap<String, String>,@Query("query") query:String): Observable<NutritionModelResponse>



    @FormUrlEncoded
    @POST("/v2/natural/nutrients")
    fun GetNutrionResponse(@HeaderMap headerData: HashMap<String, String>,@Field("query") query:String): Call<NutritionGetResponse>


    @FormUrlEncoded
    @POST("/v2/natural/exercise")
    fun GetNutrionExcerciseResponse(@HeaderMap headerData: HashMap<String, String>,@Field("query") query:String): Call<ExcerciseModelResponse>


    //MedicalReport
    @POST("medreportreader")
    fun MedicalReport(@Body file_name:RequestBody): Call<MedicalReportResponse>




}