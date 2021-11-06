package indg.com.cover2protect.model.login

import java.io.Serializable

class Register_Response():Serializable {


    /*{
        "success": true,
         "error": "validation_error",
        "message": "Registration Completed Successfully."
    }*/


    private var success: Boolean? = null
    private var message: String? = null
    private var error: String? = null

    fun getSuccess():Boolean?{
        return  success
    }

    fun getMessage():String?{

        return  message
    }

    fun getError():String?{

        return  error
    }






}