package indg.com.cover2protect.navigator

import indg.com.cover2protect.model.excerciseapi.excercise_get.Data
import indg.com.cover2protect.model.nutrition.nutritionhistory.Date

interface NutrionHistoryNavigator {

    fun OnSuccess(data:List<Date>,sum:String)
    fun OnLunch(data:List<Date>,sum:String)
    fun OnDinner(data:List<Date>,sum:String)
    fun OnSnacks(data:List<Date>,sum:String)
    fun OnExcercise(data: List<Data>,sum:String)
    fun OnError(msg:String)
    fun OnCalTarget(msg:String)
    fun OnPedometerSuccess()

}