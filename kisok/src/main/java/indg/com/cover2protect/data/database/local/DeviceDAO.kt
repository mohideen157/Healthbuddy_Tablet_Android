package indg.com.cover2protect.data.database.local

import androidx.room.*
import indg.com.cover2protect.data.database.model_db.MaisenseDevice
import io.reactivex.Flowable


@Dao
interface DeviceDAO {

    @Query("SELECT * FROM MaisenseDevice")
    fun getCartItems(): Flowable<List<MaisenseDevice>>

    @Query("SELECT * FROM MaisenseDevice WHERE id=:cartItemId")
    fun getCartItemById(cartItemId: Int): Flowable<List<MaisenseDevice>>


    @Query("SELECT * FROM MaisenseDevice WHERE synchedid = :synchedid")
    fun findDeviceBySynchedId(synchedid: String): MaisenseDevice

    @Query("SELECT COUNT(*) from MaisenseDevice")
    fun countCartItem(): Int

    @Query("DELETE FROM MaisenseDevice")
    fun emptyCart()

    @Insert
    fun insertToCart(vararg carts: MaisenseDevice)

    @Update
    fun updateCart(vararg carts: MaisenseDevice)

    @Delete
    fun deleteCartItem(cart: MaisenseDevice)
}