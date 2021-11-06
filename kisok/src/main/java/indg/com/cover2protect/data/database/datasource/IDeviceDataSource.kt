package indg.com.cover2protect.data.database.datasource

import indg.com.cover2protect.data.database.model_db.MaisenseDevice
import io.reactivex.Flowable



interface IDeviceDataSource {

    fun getCartItems(): Flowable<List<MaisenseDevice>>
    fun getCartItemById(cartItemId: Int): Flowable<List<MaisenseDevice>>
    fun countCartItems(): Int
    fun findDeviceBySynchedId(synchedId: String): MaisenseDevice
    fun emptyCart()
    fun insertToCart(vararg carts: MaisenseDevice)
    fun updateCart(vararg carts: MaisenseDevice)
    fun deleteCartItem(cart: MaisenseDevice)
}