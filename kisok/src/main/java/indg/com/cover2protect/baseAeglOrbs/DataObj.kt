package indg.com.cover2protect.baseAeglOrbs

import java.io.Serializable

data class W3Obj(var WeigthKg : String, var BMI : String,var BodyFat : String,
                 var MuscleMass : String,var VisceralFat : String,var BodyWater : String,
                 var BoneMass : String,var BMR : String, var Target : String,
                 var ToLose : String, var DateOfReading : String, var UserId : String): Serializable;


data class BpObj(var SYS_mmHg : String, var DIA_mmHg : String,var PUL : String,
                 var IHB : String,var Date : String): Serializable;


data class ObjGlucose(var Date : String, var mmol : String,var High : String,
                      var Low : String,var Type : String): Serializable;

data class ObjQuestionnaire(var question : String, var responseString : String): Serializable;
