package indg.com.cover2protect.repository

import android.app.Activity
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.os.Build
import android.os.Environment
import android.util.Log


import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.RandomAccessFile
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Arrays
import java.util.Calendar
import java.util.Date
import kotlin.experimental.and

object SysUtils {

    fun writeTxtToFile(strcontent: String, filePath: String, fileName: String) {
        var fileName = fileName
        val sdcardDir = Environment.getExternalStorageDirectory()
        val path = sdcardDir.path + filePath
        val today = Calendar.getInstance()
        fileName = today.get(Calendar.YEAR).toString() + "-" + (today.get(Calendar.MONTH) + 1) + "-" + today.get(Calendar.DAY_OF_MONTH) + "-" + fileName
        makeFilePath(path, fileName)

        val strFilePath = path + fileName
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val now = sdf.format(Date())
        val strContent = "$now:$strcontent\r\n"
        try {
            val file = File(strFilePath)
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:$strFilePath")
                file.parentFile.mkdirs()
                file.createNewFile()
            }
            val raf = RandomAccessFile(file, "rwd")
            raf.seek(file.length())
            raf.write(strContent.toByteArray())
            raf.close()
        } catch (e: Exception) {
            Log.e("TestFile", "Error on write File:$e")
        }

    }

    fun makeFilePath(filePath: String, fileName: String): File? {
        var file: File? = null
        makeRootDirectory(filePath)
        try {
            file = File(filePath + fileName)
            if (!file.exists()) {
                file.createNewFile()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return file
    }

    fun makeRootDirectory(filePath: String) {
        var file: File? = null
        try {
            file = File(filePath)
            if (!file.exists()) {
                file.mkdir()
            }
        } catch (e: Exception) {
            Log.i("error:", e.toString() + "")
        }

    }

    fun printHexString(b: ByteArray?): String {
        if (b == null) {
            return ""
        }

        var hexString = ""
        for (i in b.indices) {
            var hex = Integer.toHexString((b[i] and 0xFF.toByte()).toInt())
            if (hex.length == 1) {
                hex = "0$hex"
            }
            hexString += hex.toUpperCase() + " "
        }

        return hexString
    }


}