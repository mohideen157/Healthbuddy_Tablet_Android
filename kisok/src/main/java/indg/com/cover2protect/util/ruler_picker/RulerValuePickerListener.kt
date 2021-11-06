package indg.com.cover2protect.util.ruler_picker

interface RulerValuePickerListener {

    fun onValueChange(selectedValue: Int)

    fun onIntermediateValueChange(selectedValue: Int)
}