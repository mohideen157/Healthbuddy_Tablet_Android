package indg.com.cover2protect.presenter

import com.google.gson.JsonObject
import indg.com.cover2protect.model.Awsresponse.aws_response
import indg.com.cover2protect.model.MedicationHealthProfile.medicationhealthProfileResponse
import indg.com.cover2protect.model.alcoholmodel.AlcoholQuestionResponse
import indg.com.cover2protect.model.alcoholmodel.getalcoholdosage.GetAlcoholDosage
import indg.com.cover2protect.model.apiresponse.ApiResponse
import indg.com.cover2protect.model.arrhythmia.ArrhythmiaResponse
import indg.com.cover2protect.model.articles.ArticleResponse
import indg.com.cover2protect.model.bpmodel.bpdata
import indg.com.cover2protect.model.banddatadetail.updatebanddata.GetBandResponse
import indg.com.cover2protect.model.caloriesgraphresponse.Caloriesgraphresponse
import indg.com.cover2protect.model.caloriesgraphresponse.getcalories.GetCalories
import indg.com.cover2protect.model.cerealconsumption.CerealConsummption
import indg.com.cover2protect.model.diet.DietType
import indg.com.cover2protect.model.diseases.DiseaseDetailResponse.DiseaseDetailResponse
import indg.com.cover2protect.model.diseases.GetDiseases
import indg.com.cover2protect.model.drinkconsumption.DrinkConsumption
import indg.com.cover2protect.model.excerciseapi.excercise_get.ExcerciseGetResponse
import indg.com.cover2protect.model.excerciseapi.ExcercisePostResponse
import indg.com.cover2protect.model.fastfoodconsumption.FastFoodConsumption
import indg.com.cover2protect.model.frequencyresponse.FrequencyResponse
import indg.com.cover2protect.model.fruitconsumption.FruitConsumption
import indg.com.cover2protect.model.gettraveldata.GetTravelData
import indg.com.cover2protect.model.hhi.hhiresponse
import indg.com.cover2protect.model.hramodel.hradata
import indg.com.cover2protect.model.heriditary.GetHeriditary
import indg.com.cover2protect.model.imageresponse.ImageUrlResponse
import indg.com.cover2protect.model.login.LoginResponse
import indg.com.cover2protect.model.medicationmodel.deleteresponse.DeleteResponse
import indg.com.cover2protect.model.medicationmodel.medicationdetail.PostMedicationDetail
import indg.com.cover2protect.model.medicationmodel.medicationinfo.MedicationInfo
import indg.com.cover2protect.model.medicationmodel.medicationnameresponse.PostMedicationResponse
import indg.com.cover2protect.model.multiplequesmodel.MultipleQuesResponse
import indg.com.cover2protect.model.nutrition.updatenutritionresponse.PutNutritionResponse
import indg.com.cover2protect.model.occupationmodel.OccupationResposeData
import indg.com.cover2protect.model.organization.organisationmodel
import indg.com.cover2protect.model.profile.Profile
import indg.com.cover2protect.model.nutrition.nutritionhistory.GetNutritionHistory
import indg.com.cover2protect.model.pedometer.PedometerResponse
import indg.com.cover2protect.model.ResponseModel.Responsedata
import indg.com.cover2protect.model.arrhythmia.arrythmialist.arrythmialist_response
import indg.com.cover2protect.model.event.GetEvent.GetRecordResponse
import indg.com.cover2protect.model.event.PostEvent.PostEventResponse
import indg.com.cover2protect.model.getallergy_detail.getalergydetail
import indg.com.cover2protect.model.hhi.hhigraph.hhigraphresponse
import indg.com.cover2protect.model.smokingmodel.GetSmokingResponse
import indg.com.cover2protect.model.tiamodel.GETTIAData
import indg.com.cover2protect.model.tenant.Tenentdata
import indg.com.cover2protect.model.profile.UpdateProfile
import indg.com.cover2protect.model.profile_api.bloodgroupresponse.BloodgroupResponse
import indg.com.cover2protect.model.profile_api.heightresponse.HeightResponse
import indg.com.cover2protect.model.profile_api.occupation.OccupationResponse
import indg.com.cover2protect.model.profile_api.profileresponse.ProfileResponse
import indg.com.cover2protect.model.profile_api.weightresponse.WeightResponse
import indg.com.cover2protect.model.profile_api.dobprofile.dobprofileResponse
import indg.com.cover2protect.model.profilecompletion.ProfileCompletionResponse
import indg.com.cover2protect.model.registrationmodel.otp.RegisterOtpVerification
import indg.com.cover2protect.model.registrationmodel.RegistrationResponse
import indg.com.cover2protect.model.resetpassword.ResetPassword
import indg.com.cover2protect.model.responseformat.ResponseFormat
import indg.com.cover2protect.model.send_otp.SendOTPResponse
import indg.com.cover2protect.model.trends.afib.afibtrendresponse
import indg.com.cover2protect.model.trends.bp.bptrendresponse
import indg.com.cover2protect.model.trends.rpwvtrend.rPWVTrendresponse
import indg.com.cover2protect.model.uploadimage.ImageResponse
import indg.com.cover2protect.model.vegconsumption.VegConsumption
import indg.com.cover2protect.model.hr.hrresponse
import indg.com.cover2protect.model.medicalreport.medical_report_get.get_medical_response
import indg.com.cover2protect.model.medicalreport.medicalreportresponse
import indg.com.cover2protect.model.medicinedetail.MedicineDetailResponse
import indg.com.cover2protect.model.pedometer.pedometerresponse.PedometerGetResponse
import indg.com.cover2protect.model.pedometer.pedometerresponse.PedometerResponsedata
import indg.com.cover2protect.model.pre_existdetail.preexistdetail_response
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.POST
import retrofit2.http.Multipart
import retrofit2.http.HeaderMap


interface ApiService {

    // Login Api
    @FormUrlEncoded
    @POST("api/rest/V1/authenticate/login")
    fun authuser(@FieldMap data: Map<String, String>): Call<LoginResponse>


    //Register otp Api
    @FormUrlEncoded
    @POST("api/rest/V1/mobile/verification")
    fun RegisterOTPuser(@FieldMap data: Map<String, String>): Observable<RegisterOtpVerification>

    //Register Api
    @FormUrlEncoded
    @POST("api/rest/V1/authenticate/register")
    fun RegisterAuth(@FieldMap data: Map<String, String>): Call<RegistrationResponse>


    @GET("api/rest/V1/patient/profile")
    fun getProfile(@HeaderMap headerData: HashMap<String, String>): Observable<Profile>

    @FormUrlEncoded
    @POST("api/rest/V1/patient/profile")
    fun updateProfile(@HeaderMap header: Map<String, String>, @FieldMap data: Map<String, String>): Observable<OccupationResposeData>

    @POST("api/rest/V1/patient/profile")
    fun PostProfileData(@HeaderMap header: Map<String, String>, @Body data: UpdateProfile): Observable<OccupationResposeData>

    @POST("api/rest/V1/patient/healthProfile/travelFrequency")
    fun updateTravelFrequency(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("child_key") child_key: String, @Query("value") value: String, @Query("unit") unit: String): Call<FrequencyResponse>

    @GET("api/rest/V1/patient/profile")
    fun getProfiledata(@HeaderMap headerData: HashMap<String, String>): Observable<Profile>

    @POST("api/rest/V1/patient/healthProfile/diet/diet-type")
    fun updateDiet(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("value") value: String): Call<DietType>

    @POST("api/rest/V1/patient/healthProfile/diet/cup-of-vegetables")
    fun vegConsuption(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("value") value: String, @Query("unit") unit: String): Call<VegConsumption>

    @POST("api/rest/V1/patient/healthProfile/diet/fruits")
    fun updatefruitConsumption(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("value") value: String, @Query("unit") unit: String): Call<FruitConsumption>

    @POST("api/rest/V1/patient/healthProfile/diet/cereals-qty")
    fun UpdatecerealsConsumption(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("value") value: String, @Query("unit") unit: String): Call<CerealConsummption>

    @POST("api/rest/V1/patient/healthProfile/diet/fast-food")
    fun UpdatefastFoodConsumption(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("value") value: String, @Query("unit") unit: String): Call<FastFoodConsumption>

    @POST("api/rest/V1/patient/healthProfile/diet/drinks")
    fun UpdatedrinkConsumption(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("value") value: String, @Query("unit") unit: String): Call<DrinkConsumption>


    @POST("api/rest/V1/patient/healthProfile/alcohol")
    fun UpsertAlcohol(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("value") value: String): Observable<AlcoholQuestionResponse>

    @POST("api/rest/V1/patient/healthProfile/smoking")
    fun UpsertSmoke(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("value") value: String): Call<Responsedata>

    @POST("api/rest/V1/patient/healthProfile/vigorus-physical-activity")
    fun UpsertVigorous(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("value") value: String, @Query("unit") unit: String): Call<Responsedata>


    @POST("api/rest/V1/patient/healthProfile/moderate-physical-activity")
    fun UpsertModerate(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("value") value: String, @Query("unit") unit: String): Call<Responsedata>

    @POST("api/rest/V1/patient/healthProfile/light-physical-activity")
    fun UpsertLight(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("value") value: String, @Query("unit") unit: String): Call<Responsedata>


    @POST("api/rest/V1/patient/healthProfile/diebetic")
    fun UpsertDiabetic(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("value") value: String): Call<Responsedata>


    @POST("api/rest/V1/patient/healthProfile/diebetic/medicine")
    fun UpsertDiabeticIntake(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("child_key") child_key: String, @Query("value") value: String): Call<Responsedata>


    @POST("api/rest/V1/patient/healthProfile/blood-cholestrol")
    fun UpsertBloodCholestrol(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("value") value: String): Call<Responsedata>

    @POST("api/rest/V1/patient/healthProfile/blood-pressure/systolic")
    fun UpsertSystolic(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("child_key") child_key: String, @Query("value") value: String): Call<Responsedata>

    @POST("api/rest/V1/patient/healthProfile/blood-pressure/diastolic")
    fun UpsertDiastolic(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("child_key") child_key: String, @Query("value") value: String): Call<Responsedata>


    @POST("api/rest/V1/patient/healthProfile/cardiovascular-or-stroke")
    fun UpsertCardiovascular(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("value") value: String): Call<Responsedata>

    @POST("api/rest/V1/patient/healthProfile/tia")
    fun UpsertTia(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("value") value: String): Call<Responsedata>

    @POST("api/rest/V1/patient/healthProfile/tia/regular-treatment")
    fun UpsertTiaTreatment(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("child_key") child_key: String, @Query("value") value: String): Call<Responsedata>

    @POST("api/rest/V1/patient/healthProfile/disease")
    fun UpsertPre_ExistDisease(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("value") value: String): Call<Responsedata>

    @POST("api/rest/V1/patient/healthProfile/allergy")
    fun UpsertAllergy(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("value") value: String): Call<Responsedata>

    @POST("api/rest/V1/patient/healthProfile/medication")
    fun UpsertMedication(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("value") value: String): Call<Responsedata>

    @POST("api/rest/V1/patient/healthProfile/stroke")
    fun UpsertStroke(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("value") value: String): Call<Responsedata>


    @POST("api/rest/V1/patient/healthProfile/stroke/reason")
    fun UpsertStrokeReason(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("child_key") child_key: String, @Query("value") value: String): Call<Responsedata>


    @POST("api/rest/V1/patient/healthProfile/alcohol/alcohol-dosage/small")
    fun UpdateAlcohol_Small(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("child_key") child_key: String, @Query("value") value: Int, @Query("extra_info") unit: String): Call<Responsedata>

    @POST("api/rest/V1/patient/healthProfile/alcohol/alcohol-dosage/medium")
    fun UpdateAlcohol_Medium(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("child_key") child_key: String, @Query("value") value: Int, @Query("extra_info") unit: String): Call<Responsedata>

    @POST("api/rest/V1/patient/healthProfile/alcohol/alcohol-dosage/large")
    fun UpdateAlcohol_Large(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("child_key") child_key: String, @Query("value") value: Int, @Query("extra_info") unit: String): Call<Responsedata>


    //Post and Delete Disease Detail

    @POST("api/rest/V1/patient/healthProfile/disease/disease-details")
    fun UpdateDiseaseDetail(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("child_key") child_key: String, @Query("value") value: String): Call<DiseaseDetailResponse>

    @DELETE("api/rest/V1/patient/healthProfile/disease/disease-details/delete")
    fun DeleteDisease(@HeaderMap headerData: HashMap<String, String>, @Query("value") value: String): Call<DeleteResponse>


    //Post and Delete Allergy Detail

    @POST("api/rest/V1/patient/healthProfile/allergy/allergy-details")
    fun UpdateAllergyDetail(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("child_key") child_key: String, @Query("value") value: String): Call<DiseaseDetailResponse>

    @DELETE("api/rest/V1/patient/healthProfile/allergy/allergy-details/delete")
    fun DeleteAllergy(@HeaderMap headerData: HashMap<String, String>, @Query("value") value: String): Call<DeleteResponse>

    //Post and Delete Medication Detail

    @POST("api/rest/V1/patient/healthProfile/medication/medication-details")
    fun UpdateMedicationDetail(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("child_key") child_key: String, @Query("value") value: String): Call<PostMedicationResponse>

    @DELETE("api/rest/V1/patient/healthProfile/medication/medication-details/delete")
    fun DeleteMedication(@HeaderMap headerData: HashMap<String, String>, @Query("value") value: String): Call<DeleteResponse>

    @POST("api/rest/V1/patient/healthProfile/medication/medication-details/{id}/update")
    fun UpdateMedicationInfo(@Path("id") groupId: Int, @HeaderMap headerData: HashMap<String, String>, @Query("dosage") dosage: String, @Query("type") type: String, @Query("per_day") perday: String): Call<PostMedicationResponse>

    //Smoking Post Api
    @POST("api/rest/V1/patient/healthProfile/smoking/smoking-interval")
    fun UpdateSmokingTime(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parent_key: String, @Query("child_key") child_key: String, @Query("value") value: String): Call<Responsedata>

    @POST("api/rest/V1/patient/healthProfile/smoking/start-age")
    fun UpdateSmoking_StartAge(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parent_key: String, @Query("child_key") child_key: String, @Query("value") value: String): Call<Responsedata>

    @POST("api/rest/V1/patient/healthProfile/smoking/end-age")
    fun UpdateSmoking_EndAge(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parent_key: String, @Query("child_key") child_key: String, @Query("value") value: String): Call<Responsedata>

    @POST("api/rest/V1/patient/healthProfile/smoking/dosage")
    fun UpdateSmoking_Dosage(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parent_key: String, @Query("child_key") child_key: String, @Query("value") value: String): Call<Responsedata>

    @POST("api/rest/V1/patient/healthProfile/smoking/tobacco")
    fun UpdateSmoking_Tobacco(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parent_key: String, @Query("child_key") child_key: String, @Query("value") value: String, @Query("extra_info") extra_info: String): Call<Responsedata>

    @GET("api/rest/V1/patient/healthProfile/all-smoking-data")
    fun GetSmokingData(@HeaderMap headerData: HashMap<String, String>): Call<GetSmokingResponse>

    //Multiple Questions

    @POST("api/rest/V1/patient/healthProfile/cardiovascular-or-stroke/coronary-heart-ischemic-heart")
    fun UpsertCardiovascularCoronary(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("child_key") child_key: String, @Query("value") value: String): Call<Responsedata>

    @POST("api/rest/V1/patient/healthProfile/cardiovascular-or-stroke/angina-pain")
    fun UpsertCardiovascularAnginaPain(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("child_key") child_key: String, @Query("value") value: String): Call<Responsedata>

    @POST("api/rest/V1/patient/healthProfile/cardiovascular-or-stroke/regular-medication")
    fun UpsertCardiovascularRegularMedication(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("child_key") child_key: String, @Query("value") value: String): Call<Responsedata>

    @POST("api/rest/V1/patient/healthProfile/cardiovascular-or-stroke/heart-attack")
    fun UpsertCardivascHeartAttack(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("child_key") child_key: String, @Query("value") value: String): Call<Responsedata>

    @POST("api/rest/V1/patient/healthProfile/cardiovascular-or-stroke/ecg")
    fun UpsertCardiovascEcg(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("child_key") child_key: String, @Query("value") value: String): Call<Responsedata>

    @POST("api/rest/V1/patient/healthProfile/cardiovascular-or-stroke/coronary-angiography")
    fun UpsertCadiovascCoronaryAngiography(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("child_key") child_key: String, @Query("value") value: String): Call<Responsedata>

    @POST("api/rest/V1/patient/healthProfile/cardiovascular-or-stroke/bypass-surgery")
    fun UpsertCardiovascBypassSurgery(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("child_key") child_key: String, @Query("value") value: String): Call<Responsedata>

    @POST("api/rest/V1/patient/healthProfile/cardiovascular-or-stroke/stent-placement")
    fun UpsertCardiovascStentPlacement(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("child_key") child_key: String, @Query("value") value: String): Call<Responsedata>

    @POST("api/rest/V1/patient/healthProfile/cardiovascular-or-stroke/valve-surgery")
    fun UpsertCardiovascValveSurgery(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parentkey: String, @Query("child_key") child_key: String, @Query("value") value: String): Call<Responsedata>

    @GET("api/rest/V1/disease")
    fun diseases(@HeaderMap headerData: HashMap<String, String>): Observable<GetDiseases>

    @GET("api/rest/V1/allergy")
    fun getallergy(@HeaderMap headerData: HashMap<String, String>): Observable<GetDiseases>


    @GET("api/rest/V1/patient/healthProfile/allergy/allergy-details")
    fun GetAllergyDetail(@HeaderMap headerData: HashMap<String, String>): Observable<getalergydetail>

    @GET("api/rest/V1/patient/healthProfile/disease/disease-details")
    fun GetdiseaseDetail(@HeaderMap headerData: HashMap<String, String>): Observable<preexistdetail_response>

    @GET("api/rest/V1/patient/healthProfile/medication/medication-details")
    fun GetmedicineDetail(@HeaderMap headerData: HashMap<String, String>): Observable<MedicineDetailResponse>



    @GET("api/rest/V1/medication")
    fun getMedication(@HeaderMap headerData: HashMap<String, String>): Observable<GetDiseases>

    @GET("api/rest/V1/patient/healthProfile/travelFrequency/travel-national")
    fun GetTravelNational(@HeaderMap headerData: HashMap<String, String>): Call<GetTravelData>

    @GET("api/rest/V1/patient/healthProfile/travelFrequency/travel-international")
    fun GetTravelInterNational(@HeaderMap headerData: HashMap<String, String>): Call<GetTravelData>

    @GET("api/rest/V1/patient/healthProfile/diet/diet-type")
    fun GetDietType(@HeaderMap headerData: HashMap<String, String>): Call<GetTravelData>


    @GET("api/rest/V1/patient/healthProfile/diet/cup-of-vegetables")
    fun GetCupsofVeg(@HeaderMap headerData: HashMap<String, String>): Call<GetTravelData>


    @GET("api/rest/V1/patient/healthProfile/diet/fruits")
    fun GetFruits(@HeaderMap headerData: HashMap<String, String>): Call<GetTravelData>

    @GET("api/rest/V1/patient/healthProfile/diet/cereals-qty")
    fun GETCereals(@HeaderMap headerData: HashMap<String, String>): Call<GetTravelData>

    @GET("api/rest/V1/patient/healthProfile/diet/fast-food")
    fun GetFastFoodData(@HeaderMap headerData: HashMap<String, String>): Call<GetTravelData>

    @GET("api/rest/V1/patient/healthProfile/diet/drinks")
    fun GetDrinks(@HeaderMap headerData: HashMap<String, String>): Call<GetTravelData>

    @GET("api/rest/V1/patient/healthProfile/alcohol")
    fun GetAlcohol(@HeaderMap headerData: HashMap<String, String>): Call<GetTravelData>

    @GET("api/rest/V1/patient/healthProfile/smoking")
    fun GetSmoking(@HeaderMap headerData: HashMap<String, String>): Call<GetTravelData>

    @GET("api/rest/V1/patient/healthProfile/vigorus-physical-activity")
    fun GetVigorous(@HeaderMap headerData: HashMap<String, String>): Call<GetTravelData>

    @GET("api/rest/V1/patient/healthProfile/moderate-physical-activity")
    fun GetModerate(@HeaderMap headerData: HashMap<String, String>): Call<GetTravelData>

    @GET("api/rest/V1/patient/healthProfile/light-physical-activity")
    fun GetLight(@HeaderMap headerData: HashMap<String, String>): Call<GetTravelData>

    @GET("api/rest/V1/patient/healthProfile/diebetic")
    fun GetDiabetic(@HeaderMap headerData: HashMap<String, String>): Call<GetTravelData>

    @GET("api/rest/V1/patient/healthProfile/diebetic/medicine")
    fun GetDiabeticInfo(@HeaderMap headerData: HashMap<String, String>): Call<GetTravelData>

    @GET("api/rest/V1/patient/healthProfile/blood-cholestrol")
    fun GetBloodcholestrol(@HeaderMap headerData: HashMap<String, String>): Call<GetTravelData>

    @GET("api/rest/V1/patient/healthProfile/get-blood-pressure-all-data")
    fun GetBPData(@HeaderMap headerData: HashMap<String, String>): Call<bpdata>

    @GET("api/rest/V1/patient/healthProfile/cardiovascular-or-stroke")
    fun GEtHeartAilment(@HeaderMap headerData: HashMap<String, String>): Call<GetTravelData>

    @GET("api/rest/V1/patient/healthProfile/tia")
    fun GETTIAData(@HeaderMap headerData: HashMap<String, String>): Call<GETTIAData>

    @GET("api/rest/V1/patient/healthProfile/tia/regular-treatment")
    fun GETTIAIntake(@HeaderMap headerData: HashMap<String, String>): Call<GETTIAData>

    @GET("api/rest/V1/patient/healthProfile/disease")
    fun GETPreDisease(@HeaderMap headerData: HashMap<String, String>): Call<GETTIAData>

    @GET("api/rest/V1/patient/healthProfile/allergy")
    fun GETAllergydata(@HeaderMap headerData: HashMap<String, String>): Call<GETTIAData>

    @GET("api/rest/V1/patient/healthProfile/medication")
    fun GETMedicationdata(@HeaderMap headerData: HashMap<String, String>): Call<medicationhealthProfileResponse>

    @GET("api/rest/V1/patient/healthProfile/stroke")
    fun GETStrokedata(@HeaderMap headerData: HashMap<String, String>): Call<GETTIAData>

    @GET("api/rest/V1/patient/healthProfile/stroke/reason")
    fun GETSTrokeReasondata(@HeaderMap headerData: HashMap<String, String>): Call<GETTIAData>


    @GET("api/rest/V1/patient/healthProfile/get-cardiovascular-or-stroke-all-data")
    fun GETMultipleQuesResponse(@HeaderMap headerData: HashMap<String, String>): Call<MultipleQuesResponse>

    @GET("api/rest/V1/patient/healthProfile/medication/medication-details")
    fun GETMedicationList(@HeaderMap headerData: HashMap<String, String>): Call<MedicationInfo>

    @DELETE("api/rest/V1/patient/healthProfile/medication/medication-details/delete")
    fun DeleteMedicationList(@HeaderMap headerData: HashMap<String, String>, @Query("value") value: String): Call<DeleteResponse>

    @POST("api/rest/V1/patient/healthProfile/medication/medication-details/{Id}/update")
    fun UpdateMedDetail(@Path("Id") id: String, @HeaderMap headerData: HashMap<String, String>, @Query("dosage") value: String, @Query("type") type: String, @Query("per_day") per_day: String): Call<PostMedicationDetail>


    @GET("api/rest/V1/patient/healthProfile/nutrition/delete/{Id}")
    fun DeleteNutrition(@Path("Id") id: String, @HeaderMap headerData: HashMap<String, String>): Call<ResponseFormat>

    @GET("api/rest/V1/patient/healthProfile/get-hra-score")
    fun GetHRA(@HeaderMap headerData: HashMap<String, String>): Call<hradata>

    @GET("api/rest/V1/tenant")
    fun GetTenant(): Call<Tenentdata>

    @GET("api/rest/V1/patient/healthProfile/all-alcohol-data")
    fun GetAlcoholDosageData(@HeaderMap headerData: HashMap<String, String>): Call<GetAlcoholDosage>

    @GET("api/rest/V1/tenant_organisation/{Id}")
    fun GEtOrganisationlist(@Path("Id") id: String): Observable<organisationmodel>

    //Forgot Password
    @POST("api/rest/V1/send/otp")
    fun UpdateForgotPass(@Query("mobile_no") username: String): Call<SendOTPResponse>


    @POST("api/rest/V1/authenticate/reset-password")
    fun ResetPassword(@Query("mobile_no") username: String, @Query("password") password: String): Call<ResetPassword>


    @POST("api/rest/V1/patient/healthProfile/nutrition")
    fun UpdateNutrition(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parent_key: String, @Query("child_key") child_key: String, @Query("extra_info") extra_info: String): Call<PutNutritionResponse>

    @POST("api/rest/V1/patient/healthProfile/nutrition")
    fun UpdateNutritionData(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parent_key: String, @Query("child_key") child_key: String, @Query("extra_info") extra_info: String, @Query("id") id: String): Call<PutNutritionResponse>

    @POST("api/rest/V1/patient/healthProfile/hra-band-data/device-2")
    fun UpdateDevice2(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parent_key: String, @Query("child_key") child_key: String, @Query("extra_info") extra_info: String): Call<GetBandResponse>

    @Multipart
    @POST("api/rest/V1/patient/profile/image")
    fun uploadImage(@HeaderMap header: Map<String, String>, @Part file: MultipartBody.Part): Call<ImageResponse>


    @GET("api/rest/V1/patient/profile/image")
    fun GETImage(@HeaderMap header: Map<String, String>): Call<ImageUrlResponse>


    @POST("api/rest/V1/patient/healthProfile/hereditary")
    fun UpdateHeriditary(@HeaderMap header: Map<String, String>, @Query("parent_key") parent_key: String, @Query("child_key") child_key: String, @Query("value") value: String): Call<ApiResponse>

    @GET("api/rest/V1/patient/healthProfile/hereditary")
    fun GetHeriditary(@HeaderMap header: Map<String, String>, @Query("parent_key") parent_key: String): Call<GetHeriditary>


    @GET("api/rest/V1/patient/healthProfile/nutrition")
    fun GetNutritionHistory(@HeaderMap header: Map<String, String>, @Query("date") date: String, @Query("type") type: String): Call<GetNutritionHistory>


    @GET("api/rest/V1/patient/healthProfile/patient-profile-completion")
    fun GetProfileCompletion(@HeaderMap header: Map<String, String>): Call<ProfileCompletionResponse>


    @FormUrlEncoded
    @POST("api/rest/V1/patient/history")
    fun PostDeviceList(@HeaderMap header: Map<String, String>, @FieldMap history: Map<String, String>): Call<ApiResponse>


    @GET("api/rest/V1/patient/get-bp")
    fun GetTrendBP(@HeaderMap headerData: HashMap<String, String>): Observable<bptrendresponse>

    @GET("api/rest/V1/patient/get-rpwv")
    fun GetrPWVTrend(@HeaderMap headerData: HashMap<String, String>): Observable<rPWVTrendresponse>


    @GET("api/rest/V1/patient/get-afib")
    fun GetaFIBTrend(@HeaderMap headerData: HashMap<String, String>): Observable<afibtrendresponse>

    @FormUrlEncoded
    @POST("api/rest/V1/patient/healthProfile/padometer")
    fun PostPedometer(@HeaderMap headerData: HashMap<String, String>, @FieldMap pedometer: HashMap<String, String>): Call<PedometerResponse>

    @GET("api/rest/V1/patient/get-arrhythmia")
    fun GetArrhythmia(@HeaderMap headerData: HashMap<String, String>): Observable<arrythmialist_response>

    @GET("api/rest/V1/patient/profile/info")
    fun GetProfileAPIDob(@HeaderMap headerData: HashMap<String, String>): Observable<dobprofileResponse>


    @GET("api/rest/V1/patient/profile/info")
    fun GetProfileAPIHeight(@HeaderMap headerData: HashMap<String, String>, @Query("key") key: String): Observable<HeightResponse>

    @GET("api/rest/V1/patient/profile/info")
    fun GetProfileAPIWeight(@HeaderMap headerData: HashMap<String, String>, @Query("key") key: String): Observable<WeightResponse>

    @GET("api/rest/V1/patient/profile/info")
    fun GetProfileAPIBlood(@HeaderMap headerData: HashMap<String, String>, @Query("key") key: String): Observable<BloodgroupResponse>

    @GET("api/rest/V1/patient/profile/info")
    fun GetProfileAPIOccupation(@HeaderMap headerData: HashMap<String, String>, @Query("key") key: String): Observable<OccupationResponse>

    @FormUrlEncoded
    @POST("api/rest/V1/patient/profile")
    fun PostProfileAPI(@HeaderMap headerData: HashMap<String, String>, @FieldMap map: HashMap<String, String>): Observable<ProfileResponse>


    @POST("api/rest/V1/patient/healthProfile/set-medical-report")
    fun UpdateHealthReport(@HeaderMap headerData: HashMap<String, String>, @Body body: RequestBody): Call<medicalreportresponse>


    @FormUrlEncoded
    @POST("api/rest/V1/patient/healthProfile/set-padometer-steps")
    fun UploadPedometerStep(@HeaderMap headerData: HashMap<String, String>,@FieldMap map: HashMap<String, String>):Call<PedometerResponsedata>


    @GET("api/rest/V1/patient/healthProfile/padometer-steps")
    fun GetPedometerData(@HeaderMap headerData: HashMap<String, String>,@Query("date")date:String):Call<PedometerGetResponse>


    @GET("api/rest/V1/patient/get-articles")
    fun GetArticleResponse(@HeaderMap headerData: HashMap<String, String>): Call<ArticleResponse>

    @GET("api/rest/V1/patient/get-articles")
    fun GetArticlePaginationResponse(@HeaderMap headerData: HashMap<String, String>, @Query("page") page: String): Call<ArticleResponse>

    @GET("api/rest/V1/patient/hhi")
    fun GetHHI(@HeaderMap headerData: HashMap<String, String>): Call<hhiresponse>

    @POST("api/rest/V1/patient/healthProfile/excercise")
    fun PostExcercise(@HeaderMap headerData: HashMap<String, String>, @Query("parent_key") parent_key: String, @Query("extra_info") extra_info: String): Call<ExcercisePostResponse>

    @GET("api/rest/V1/patient/healthProfile/excercise")
    fun GetExcercise(@HeaderMap headerData: HashMap<String, String>, @Query("date") date: String): Call<ExcerciseGetResponse>


    @GET("api/rest/V1/patient/healthProfile/excercise/delete/{Id}")
    fun DeleteExcercise(@HeaderMap headerData: HashMap<String, String>, @Path("Id") Id: String): Call<ExcercisePostResponse>

    @GET("api/rest/V1/patient/get-hr")
    fun GethrList(@HeaderMap headerData: HashMap<String, String>): Observable<hrresponse>

    @GET("api/rest/V1/patient/get-calories")
    fun GetCaloriesGraph(@HeaderMap headerData: HashMap<String, String>): Observable<Caloriesgraphresponse>


    @GET("api/rest/V1/patient/profile/hhi")
    fun GetHHIGraph(@HeaderMap headerData: HashMap<String, String>):Observable<hhigraphresponse>


    @GET("api/aws-setting")
    fun GetAWS():Observable<aws_response>



    @FormUrlEncoded
    @POST("api/rest/V1/patient/healthProfile/set-calories-target")
    fun SetCaloriesTarget(@HeaderMap headerData: HashMap<String, String>, @FieldMap fielddata: HashMap<String, String>): Observable<ResponseFormat>


    @GET("api/rest/V1/patient/healthProfile/get-calories-target")
    fun GetCaloriesTarget(@HeaderMap headerData: HashMap<String, String>,@Query("date")date:String): Observable<GetCalories>


    @GET("api/rest/V1/patient/healthProfile/get-medical-report")
    fun GetReport(@HeaderMap headerData: HashMap<String, String>): Call<get_medical_response>


    @FormUrlEncoded
    @POST("/api/rest/V1/patient/event")
    fun UpdateEvent(@HeaderMap headerData: HashMap<String, String>, @FieldMap map:HashMap<String,String>):Call<PostEventResponse>


    @GET("/api/rest/V1/patient/event")
    fun GetEvent(@HeaderMap headerData: HashMap<String, String>, @Query("synched")synched:String):Call<GetRecordResponse>



}