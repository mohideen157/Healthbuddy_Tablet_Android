package indg.com.cover2protect.model.login


import java.io.Serializable

class LoginResponse() : Serializable {

    private var success: Boolean? = null
    private var message: String? = null
    private var data: Datas? = null

    fun getStatus(): Boolean? {
        return success
    }

    fun getMessage():String?{
        return  message
    }

    fun getData(): Datas?{
        return  data
    }


    class Datas {

        private var token: String? = null
        private var user: User? = null
        private var showProfile:Boolean?=null

        fun getToken():String?{
            return token
        }

        fun getUser(): User?{
            return user
        }

        fun getShowProfile():Boolean?{
            return  showProfile
        }
    }

    class User {

        private var name:String?=null
        private var email:String?=null
        private var mobile_no:String?=null

        fun getName():String?{
            return  name
        }

        fun getEmail():String?{
            return  email
        }

        fun getMobileNumber():String?{
            return  mobile_no
        }
    }


}