package indg.com.cover2protect.navigator

import indg.com.cover2protect.model.MedicalReportResponse

interface MedicalReportNavigator {

    fun OnSuccess(data:MedicalReportResponse)

    fun OnError(message:String)

    fun OnUpdate(`data`:Any)
}