package indg.com.cover2protect.util

import dagger.Module
import dagger.Provides

@Module
class HeaderData {
    val header : HashMap<String,String> = HashMap()

    fun setHeader(token : String){
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer "+token
    }

    fun clearHeader(){
        header["Accept"] = ""
        header["Content-Type"] = ""
        header["Authorization"] = ""
    }

    @Provides
    fun getHeaderData(): HashMap<String, String> {
        return header
    }


}