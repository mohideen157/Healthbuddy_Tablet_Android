package indg.com.cover2protect.presenter

import indg.com.cover2protect.model.medicalreport.medical_report_get.Data

interface reportlistener {
    fun onSelect(listener: Data)
}