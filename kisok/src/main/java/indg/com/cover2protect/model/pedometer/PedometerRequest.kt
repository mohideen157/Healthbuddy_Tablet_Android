package indg.com.cover2protect.model.pedometer

import java.io.Serializable

class PedometerRequest : Serializable {
    var calories: String = ""
    var distance: String = ""
    var steps: String = ""
}