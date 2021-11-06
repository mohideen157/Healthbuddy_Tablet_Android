
@file:Suppress("DEPRECATION")
package indg.com.cover2protect.DB


import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.getmedcheck.lib.MedCheckActivity


open class DBhelper(context: MedCheckActivity) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_NAME_BP (ID INTEGER PRIMARY KEY AUTOINCREMENT, SYS_mmHg TEXT,DIA_mmHg TEXT,PUL TEXT,IHB TEXT,Date TEXT,UserId TEXT,SystemDate TEXT)")
        db.execSQL("CREATE TABLE $TABLE_NAME_BGM (ID INTEGER PRIMARY KEY AUTOINCREMENT, Date TEXT,Mmol TEXT, High TEXT,Low TEXT,Type TEXT,UserId TEXT,SystemDate TEXT)")
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(" DROP TABLE IF EXISTS $TABLE_NAME_BP")
        db.execSQL(" DROP TABLE IF EXISTS $TABLE_NAME_BGM")
        onCreate(db)

    }

    fun insertBPData(
            SYS_mmHg: String,
            DIA_mmHg: String,
            PUL: String,
            IHB: String,
            Date: String,
            User_id: String,
            SystemDate: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_BP_2, SYS_mmHg)
        contentValues.put(COL_BP_3, DIA_mmHg)
        contentValues.put(COL_BP_4, PUL)
        contentValues.put(COL_BP_5,IHB)
        contentValues.put(COL_BP_6,Date)
        contentValues.put(COL_USER_ID,User_id)
        contentValues.put(COL_SYSTEM_DATE, SystemDate)
        db.insert(TABLE_NAME_BP, null, contentValues)
    }



    fun insertBGMData(
            Date: String,
            Mmol: String,
            High: String,
            Low: String,
            Type: String,
            User_id: String,
            SystemDate: String) {
        val db1 = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(COL_BGM_1, Date)
        contentValues.put(COL_BGM_2, Mmol)
        contentValues.put(COL_BGM_3, High)
        contentValues.put(COL_BGM_4, Low)
        contentValues.put(COL_BGM_5, Type)
        contentValues.put(COL_USER_ID,User_id)
        contentValues.put(COL_SYSTEM_DATE, SystemDate)
        db1.insert(TABLE_NAME_BGM, null, contentValues)
    }

    /**
     * Let's create  a method to update a row with new field values.
     */
    fun updateData(id: String, SYS_mmHg: String,DIA_mmHg: String, PUL: String, IHB: String, Date: String ):
            Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_BP_1, id)
        contentValues.put(COL_BP_2, SYS_mmHg)
        contentValues.put(COL_BP_3, DIA_mmHg)
        contentValues.put(COL_BP_4, PUL)
        contentValues.put(COL_BP_5, IHB)
        contentValues.put(COL_BP_6, Date)
        db.update(TABLE_NAME_BP, contentValues, "ID = ?", arrayOf(id))
        return true
    }
    /**
     * Let's create a function to delete a given row based on the id.
     */
    fun deleteData(id : String) : Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME_BP,"ID = ?", arrayOf(id))
    }
    /**
     * The below getter property will return a Cursor containing our dataset.
     */
    val allData : Cursor
        get() {
            val db = this.writableDatabase
            val res = db.rawQuery("SELECT * FROM " + TABLE_NAME_BP +" order by id DESC limit 20", null)
            return res
        }
    val allBGMData : Cursor
        get() {
            val db = this.writableDatabase
            val res = db.rawQuery("SELECT * FROM " + TABLE_NAME_BGM +" order by id DESC limit 20", null)
            return res
        }
    /**
     * Let's create a companion object to hold our static fields.
     * A Companion object is an object that is common to all instances of a given
     * class.
     */
    companion object {
        val DATABASE_NAME = "c2phealthfit.db"
        val TABLE_NAME_BP = "medcheck_BP"
        val TABLE_NAME_BGM = "medcheck_BGM"

        val COL_BP_1 = "ID"
        val COL_BP_2 = "SYS_mmHg"
        val COL_BP_3 = "DIA_mmHg"
        val COL_BP_4 = "PUL"
        val COL_BP_5 = "IHB"
        val COL_BP_6 = "Date"

        val COL_BGM_1 = "Date"
        val COL_BGM_2 = "Mmol"
        val COL_BGM_3 = "High"
        val COL_BGM_4 = "Low"
        val COL_BGM_5 = "Type"

        val COL_USER_ID = "UserId"
        val COL_SYSTEM_DATE = "SystemDate"


    }

}
