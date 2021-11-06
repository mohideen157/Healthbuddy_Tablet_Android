package indg.com.cover2protect.data.database.model_db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "MaisenseDevice")
class MaisenseDevice {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "heartrate")
    var Heartrate: String? = null

    @ColumnInfo(name = "rpwv")
    var rPWV: String? = null


    @ColumnInfo(name = "hrvlevel")
    var HRVLevel: Int = 0

    @ColumnInfo(name = "afib")
    var AFIB: String = ""

    @ColumnInfo(name = "arrythmia")
    var Arrythmia: String = ""

    @ColumnInfo(name = "bp")
    var Bp: String = ""

    @ColumnInfo(name = "date")
    var date: String = ""

    @ColumnInfo(name = "synchedid")
    var synchedId: String = ""






}