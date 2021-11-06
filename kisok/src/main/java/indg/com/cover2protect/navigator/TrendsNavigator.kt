package indg.com.cover2protect.navigator

interface TrendsNavigator {

    fun OnSuccess(startdate:String,enddate:String)

    fun OnError(message:String)
}