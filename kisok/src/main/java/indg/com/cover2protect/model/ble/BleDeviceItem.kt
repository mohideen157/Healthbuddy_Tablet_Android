package indg.com.cover2protect.model.ble

class BleDeviceItem {
    var bleDeviceName: String? = null
    var bleDeviceAddress: String? = null
    var nickname: String? = null
    var bindedDate: String? = null
    var rssi: Int = 0
    var type: String? = null

    constructor() {}

    constructor(deviceName: String, deviceAddress: String, nickname: String, bindedDate: String, rssi: Int, type: String) {
        bleDeviceName = deviceName
        bleDeviceAddress = deviceAddress
        this.nickname = nickname
        this.bindedDate = bindedDate
        this.rssi = rssi
        this.type = type
    }

}
