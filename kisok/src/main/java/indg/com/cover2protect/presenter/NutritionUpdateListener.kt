package indg.com.cover2protect.presenter

import indg.com.cover2protect.model.nutrition.nutritionhistory.Date

interface NutritionUpdateListener {

    fun OnUpdate(id:Date)

    fun OnDelete(id:String)
}