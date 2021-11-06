package indg.com.cover2protect.data.database.datasource

import indg.com.cover2protect.data.database.model_db.MaisenseDevice
import io.reactivex.Flowable


class DeviceRepository(private val iCartDataSource: IDeviceDataSource) : IDeviceDataSource {
    override fun findDeviceBySynchedId(synchedId: String): MaisenseDevice {
        return iCartDataSource.findDeviceBySynchedId(synchedId)
    }

    override fun getCartItems(): Flowable<List<MaisenseDevice>> {
        return iCartDataSource.getCartItems()
    }

    override fun getCartItemById(cartItemId: Int): Flowable<List<MaisenseDevice>> {
        return iCartDataSource.getCartItemById(cartItemId)
    }

    override fun countCartItems(): Int {
        return iCartDataSource.countCartItems()
    }

    override fun emptyCart() {
        iCartDataSource.emptyCart()
    }

    override fun insertToCart(vararg carts: MaisenseDevice) {
        iCartDataSource.insertToCart(*carts)
    }

    override fun updateCart(vararg carts: MaisenseDevice) {
        iCartDataSource.updateCart(*carts)
    }

    override fun deleteCartItem(cart: MaisenseDevice) {
        iCartDataSource.deleteCartItem(cart)
    }

    companion object {

        private var instance: DeviceRepository? = null

        fun getInstance(iCartDataSource: IDeviceDataSource): DeviceRepository {

            if (instance == null)
                instance = DeviceRepository(iCartDataSource)

            return instance as DeviceRepository
        }
    }
}