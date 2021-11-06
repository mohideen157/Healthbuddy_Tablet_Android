package indg.com.cover2protect.data.database.local

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Database
import android.content.Context
import indg.com.cover2protect.data.database.model_db.MaisenseDevice


@Database(entities = [MaisenseDevice::class], version = 1 ,exportSchema = false)
abstract class DeviceDatabase : RoomDatabase() {
    abstract fun cartDAO(): DeviceDAO




    companion object {
        private var instance: DeviceDatabase? = null




        fun getInstance(context: Context): DeviceDatabase? {

            if (instance == null)
                instance = Room.databaseBuilder(context, DeviceDatabase::class.java, "Cover2Protect")
                        .allowMainThreadQueries()
                        .build()

            return instance
        }
    }
}