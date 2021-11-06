package indg.com.cover2protect.presenter

interface Medicationlistener {

    fun OnClick(name:String)

    fun OnSubmit(type:String,count:String,dosage:String,id:String)
}