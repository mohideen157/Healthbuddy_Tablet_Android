package indg.com.cover2protect.navigator

import indg.com.cover2protect.model.nutrition.nutritiongetresponse.Food

interface SampleNavigator {

    fun OnSuccess(msg:String)
    fun OnError(msg:String)
    fun OnFood(food:List<Food>)
    fun OnType(food:List<Food>)
}