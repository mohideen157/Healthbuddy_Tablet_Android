package indg.com.cover2protect.data.database.local


import indg.com.cover2protect.data.database.datasource.IDeviceDataSource
import indg.com.cover2protect.data.database.model_db.MaisenseDevice
import io.reactivex.Flowable



class DeviceDataSource(private val cartDAO: DeviceDAO) : IDeviceDataSource {

    override fun findDeviceBySynchedId(synchedId: String): MaisenseDevice {
        return cartDAO.findDeviceBySynchedId(synchedId)
    }

    override fun getCartItems(): Flowable<List<MaisenseDevice>> {
        return cartDAO.getCartItems()
    }

    override fun getCartItemById(cartItemId: Int): Flowable<List<MaisenseDevice>> {
        return cartDAO.getCartItemById(cartItemId)
    }

    override fun countCartItems(): Int {
        return cartDAO.countCartItem()
    }

    override fun emptyCart() {
        cartDAO.emptyCart()
    }

    override fun insertToCart(vararg carts: MaisenseDevice) {
        cartDAO.insertToCart(*carts)
    }

    override fun updateCart(vararg carts: MaisenseDevice) {
        cartDAO.updateCart(*carts)
    }

    override fun deleteCartItem(cart: MaisenseDevice) {
        cartDAO.deleteCartItem(cart)
    }

    companion object {
        private var instance: DeviceDataSource? = null

        fun getInstance(cartDAO: DeviceDAO): DeviceDataSource {

            if (instance == null)
                instance = DeviceDataSource(cartDAO)

            return instance as DeviceDataSource
        }
    }
}