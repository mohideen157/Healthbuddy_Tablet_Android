package indg.com.cover2protect.model.googlefit

data class GetPedometerRes(
        val foods: List<PedometerResponse>,
        var startdate:String,
        var enddate:String

)