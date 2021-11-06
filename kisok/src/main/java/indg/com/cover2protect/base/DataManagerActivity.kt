package indg.com.cover2protect.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import indg.com.cover2protect.presenter.DataManager

abstract class DataManagerActivity : AppCompatActivity{

    private var mDataManager: indg.com.cover2protect.presenter.DataManager?=null

    constructor(dataManager: indg.com.cover2protect.presenter.DataManager){
        mDataManager = dataManager
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    fun getDataManager(): indg.com.cover2protect.presenter.DataManager? {
        return mDataManager
    }
}