
@file:Suppress("DEPRECATION")
package indg.com.cover2protect.DB


import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase


open class DBHelper2(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
       db.execSQL("CREATE TABLE $TABLE_NAME_BMI (ID INTEGER PRIMARY KEY AUTOINCREMENT, WeigthKg TEXT, BMI TEXT, BodyFat TEXT, MuscleMass TEXT, VisceralFat TEXT, BodyWater TEXT, BoneMass TEXT, BMR TEXT, Target TEXT, ToLose TEXT, DateOfReading TEXT, UserId TEXT)")
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_BMI")
        onCreate(db)
    }



     fun insertBMIDataS(
            WeigthKg: String,
            BMI: String,
            BodyFat: String,
            MuscleMass: String,
            VisceralFat: String,
            BodyWater: String,
            BoneMass: String,
            BMR: String,
            Target: String,
            ToLose: String,
            DateOfReading: String,
            UserId: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_BMI_1, WeigthKg)
        contentValues.put(COL_BMI_2, BMI)
        contentValues.put(COL_BMI_3, BodyFat)
        contentValues.put(COL_BMI_4, MuscleMass)
        contentValues.put(COL_BMI_5, VisceralFat)
        contentValues.put(COL_BMI_6, BodyWater)
        contentValues.put(COL_BMI_7, BoneMass)
        contentValues.put(COL_BMI_8, BMR)
        contentValues.put(COL_BMI_9, Target)
        contentValues.put(COL_BMI_10, ToLose)
        contentValues.put(COL_BMI_11, DateOfReading)
        contentValues.put(COL_BMI_12,UserId)
        db.insert(TABLE_NAME_BMI, null, contentValues)
    }
    /**
     * Let's create  a method to update a row with new field values.
     */

    /**
     * Let's create a function to delete a given row based on the id.
     */

    fun deleteBMIData(UserId: String): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME_BMI, "UserId = ?", arrayOf(UserId))
    }
    /**
     * The below getter property will return a Cursor containing our dataset.
     */

    val allBMIData: Cursor
        get() {
            val db = this.writableDatabase
            val res = db.rawQuery("SELECT * FROM $TABLE_NAME_BMI order by UserId DESC limit 50", null)
            return res
        }

    /**
     * Let's create a companion object to hold our static fields.
     * A Companion object is an object that is common to all instances of a given
     * class.
     */
    companion object {
        val DATABASE_NAME = "c2phealth.db"
        val TABLE_NAME_BMI = "medcheck_BMI"

        val COL_BMI_1 = "WeigthKg"
        val COL_BMI_2 = "BMI"
        val COL_BMI_3 = "BodyFat"
        val COL_BMI_4 = "MuscleMass"
        val COL_BMI_5 = "VisceralFat"
        val COL_BMI_6 = "BodyWater"
        val COL_BMI_7 = "BoneMass"
        val COL_BMI_8 = "BMR"
        val COL_BMI_9 = "Target"
        val COL_BMI_10 = "ToLose"
        val COL_BMI_11 = "DateOfReading"
        val COL_BMI_12 = "UserId"
    }

}
