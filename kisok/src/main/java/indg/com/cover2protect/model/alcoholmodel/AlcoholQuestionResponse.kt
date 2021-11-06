package indg.com.cover2protect.model.alcoholmodel

import indg.com.cover2protect.model.fruitconsumption.Data
import java.io.Serializable

class AlcoholQuestionResponse : Serializable {

        var data: Data?=null
        var success: Boolean?=null
        var message:String?=null
}