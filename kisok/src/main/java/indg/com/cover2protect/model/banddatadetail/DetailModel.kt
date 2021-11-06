package indg.com.cover2protect.model.banddatadetail


import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import indg.com.cover2protect.BR

class DetailModel : BaseObservable() {
     var hr:String?=null
     var bp:String?=null
     var rpmv:String?=null
     var afib:String?=null
     var arrhythmia:String?=null
     var HrvLevel:String?=null

    @Bindable
    fun getHR(): String? {
        return hr
    }

    fun setHR(hr: String) {
        this.hr = hr
        notifyPropertyChanged(BR.hR)
    }

    @Bindable
    fun getBP(): String? {
        return bp
    }

    fun setBP(bp: String) {
        this.bp = bp
        notifyPropertyChanged(BR.bP)
    }

    @Bindable
    fun getrPMV(): String? {
        return rpmv
    }

    fun setrPMV(rpmv: String) {
        this.rpmv = rpmv
        notifyPropertyChanged(BR.rPMV)
    }

    @Bindable
    fun getAFIB(): String? {
        return afib
    }

    fun setAFIB(afib: String) {
        this.afib = afib
        notifyPropertyChanged(BR.aFIB)
    }

    @Bindable
    fun getHrvLeve(): String? {
        return HrvLevel
    }

    fun setHrvLeve(HrvLevel: String) {
        this.HrvLevel = HrvLevel
        notifyPropertyChanged(BR.hrvLeve)
    }

    @Bindable
    fun getArrhythmi(): String? {
        return arrhythmia
    }

    fun setArrhythmi(arrhythmia: String) {
        this.arrhythmia = arrhythmia
        notifyPropertyChanged(BR.arrhythmi)
    }

}