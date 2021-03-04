package consultan.vanke.com.utils

import android.os.Environment
import java.io.File

object SdcardHelper {
    /**
     * 项目根目录名称
     */
    const val PATH = "XSJ"
    const val CrashLog = "txt";
    const val Images = "image";
    var f: File? = null
    var f_txt: File? = null
    var f_image: File? = null
    var f_apk: File? = null
    var f_temp: File? = null
    @JvmStatic
    fun creatFils(): Boolean {
        return if (IsSDCardExist()) {
            f = File(Environment.getExternalStorageDirectory()
                    .toString() + File.separator + PATH)
            if (!f!!.exists()) {
                f!!.mkdirs()
            }
            f_txt = File(f, "txt")
            if (!f_txt!!.exists()) {
                f_txt!!.mkdirs()
            }
            f_image = File(f, "image")
            if (!f_image!!.exists()) {
                f_image!!.mkdirs()
            }
            f_apk = File(f, "apk")
            if (!f_apk!!.exists()) {
                f_apk!!.mkdirs()
            }
            f_temp = File(f, "temp")
            if (!f_temp!!.exists()) {
                f_temp!!.mkdirs()
            }
            true
        } else {
            false
        }
    }

    private fun IsSDCardExist(): Boolean {
        val status = Environment.getExternalStorageState()
        val isSDCardExist = status == Environment.MEDIA_MOUNTED
        // 如果存在则获取SDCard目录
        return if (isSDCardExist) {
            true
        } else false
    }
}