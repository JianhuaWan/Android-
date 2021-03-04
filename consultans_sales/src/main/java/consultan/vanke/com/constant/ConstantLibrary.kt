package consultan.vanke.com.constant

import android.Manifest
import consultan.vanke.com.BuildConfig

/**
 * library下的常量存放
 */
object ConstantLibrary {
    @JvmField
    var HOST = BuildConfig.HOST
    const val VIEWPAGERLIMIT = 3
    const val TYPE_TIME_OUT = 6
    const val ERR_CODE_TOKEN = 101
    const val ERR_CODE_RELOGIN = 102
    const val RC_BASEPREM = 123
    @JvmField
    val BASEPREM = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CALL_LOG, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_SMS)
    const val IMAGESELECTOR_CROP = 1001
    const val IMAGESELECTOR_ONLYTAKE = 1002
    const val IMAGESELECTOR_FILE = 1003
    const val IMAGESELECTOR_FILE_ONE = 1004
    const val MAINACTIVITY = "MainActivity"
    const val MAINFRAGMENT = "MainFragment"
    const val ONEFRAGMENT = "oneFragment"
    const val LOGINFRAGMENT = "Loginfragment"
    const val RECYCLEFRAGMENT = "Recyclefragment"
    const val CHARTFRAGMENT = "ChartFragment"
    const val FLUTTERCONTAINERACTY = "FlutterContainerActy"
    const val FLUTTERACTY = "FlutterActy"
    const val CHANGEURLFRAGMENT = "ChangeUrlfragment"
    const val HOTFIXFRAGMENT = "HotFixFragment"
    const val COUNTTIMEFRAGMENT = "CountTimeFragment"
    const val WEBVIEWFRAGMENT = "webViewfragment"
    const val ZXINGCODEFRAGMENT = "ZxingCodefragment"
    const val LOGANPWD = "0123456789012345"
    const val ROOMDBNAME = "room-consultan-db"
    const val ROOMPWD = "vanketest"
    const val HTTSHEAD = "http://"
    const val LABLE = "LABLE"
    const val TIMESTAR = "00:00:00"
    const val TIMEOVER = ":结束"
    const val GUIDE_TIPS = "AAAA"
    const val GUIDE_TIPS_CONTENT = "BBBB"
    const val SPLITE = "-"
    const val TESTROOM = "TESTROOM"
    const val LOGANFILE = "crash.log"
    const val LOGANFILE_FILTTER = ".txt"
    const val LOGANFILE_FILTLOG = ".log"
    const val LOGANPATH = "mPathPath"
    const val DATAFORMAT = "yyyy-MM-dd"
    const val WEBVIEWURL = "https://lv.lemont.cn/Wx/Laworder/index.html"
    const val DOMAIN = "xsj-consultant-android"
    const val LOGINERROR = "登录异常"
    const val NETERROR = "网络错误"
    const val VALUE = "value"
    const val USERNAME_KEY = "username"
}