package indg.com.cover2protect.presenter

import indg.com.cover2protect.model.nutrition.nutitionmodel.NutritionModelResponse
import indg.com.cover2protect.model.nutrition.nutritiongetresponse.Food

interface NutritionModelNavigator {

    fun OnSuccess(message:String)

    fun OnError(msg:String)

    fun OnFood(food: List<Food>)

    fun OnType(food:List<Food>)

    fun onData(data:NutritionModelResponse)
}