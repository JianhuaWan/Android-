package consultan.vanke.com.activity.kt

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.os.Bundle
import android.text.format.DateFormat
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.umeng.analytics.MobclickAgent
import consultan.vanke.com.R
import consultan.vanke.com.activity.BaseActivity
import consultan.vanke.com.bean.RequestConfig
import consultan.vanke.com.utils.ImageSelector
import consultan.vanke.com.utils.ImageUtil
import consultan.vanke.com.utils.StringUtils
import consultan.vanke.com.utils.VersionUtils
import kotlinx.android.synthetic.main.activity_clip_image.*
import me.yokeyword.fragmentation.SupportActivity
import java.util.*

class ClipImageActivity : BaseActivity() {
    private var mRequestCode = 0
    private var isCameraImage = false
    private var cropRatio = 0f
    override fun initContentView(bundle: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_clip_image)
        val intent = intent
        val config: RequestConfig = intent.getParcelableExtra(ImageSelector.KEY_CONFIG)
        mRequestCode = config.requestCode
        config.isSingle = true
        config.maxSelectCount = 0
        cropRatio = config.cropRatio
        setStatusBarColor()
        ImageSelectorActivity.openActivity(this, mRequestCode, config)
        initView()
    }

    override fun initElements() {}
    override fun initData() {}
    override fun initEvent() {}
    /**
     * 修改状态栏颜色
     */
    private fun setStatusBarColor() {
        if (VersionUtils.isAndroidL()) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.color_373c3d)
        }
    }

    private fun initView() {
        btn_confirm.setOnClickListener {
            if (process_img.getDrawable() != null) {
                btn_confirm.setEnabled(false)
                confirm(process_img.clipImage())
            }
        }
        btn_back.setOnClickListener { finish() }
        process_img.setRatio(cropRatio)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && requestCode == mRequestCode) {
            val images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT)
            isCameraImage = data.getBooleanExtra(ImageSelector.IS_CAMERA_IMAGE, false)
            val bitmap = ImageUtil.decodeSampledBitmapFromFile(this, images[0], WIDTH, HEIGHT)
            if (bitmap != null) {
                process_img!!.setBitmapData(bitmap)
            } else {
                finish()
            }
        } else {
            finish()
        }
    }

    private fun confirm(bitmap: Bitmap) {
        var bitmap: Bitmap? = bitmap
        var imagePath: String? = null
        if (bitmap != null) {
            val name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.getDefault())).toString()
            val path = ImageUtil.getImageCacheDir(this)
            imagePath = ImageUtil.saveImage(bitmap, path, name)
            bitmap.recycle()
            bitmap = null
        }
        if (StringUtils.isNotEmptyString(imagePath)) {
            val selectImages = ArrayList<String?>()
            selectImages.add(imagePath)
            val intent = Intent()
            intent.putStringArrayListExtra(ImageSelector.SELECT_RESULT, selectImages)
            intent.putExtra(ImageSelector.IS_CAMERA_IMAGE, isCameraImage)
            setResult(Activity.RESULT_OK, intent)
        }
        finish()
    }

    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }

    companion object {
        private const val WIDTH = 720
        private const val HEIGHT = 1080
        /**
         * 启动图片选择器
         *
         * @param activity
         * @param requestCode
         * @param config
         */
        fun openActivity(activity: Activity, requestCode: Int, config: RequestConfig?) {
            val intent = Intent(activity, ClipImageActivity::class.java)
            intent.putExtra(ImageSelector.KEY_CONFIG, config)
            activity.startActivityForResult(intent, requestCode)
        }

        /**
         * 启动图片选择器
         *
         * @param fragment
         * @param requestCode
         * @param config
         */
        fun openActivity(fragment: Fragment, requestCode: Int, config: RequestConfig?) {
            val intent = Intent(fragment.activity, ClipImageActivity::class.java)
            intent.putExtra(ImageSelector.KEY_CONFIG, config)
            fragment.startActivityForResult(intent, requestCode)
        }

        /**
         * 启动图片选择器
         *
         * @param fragment
         * @param requestCode
         * @param config
         */
        @JvmStatic
        fun openActivity(fragment: SupportActivity, requestCode: Int, config: RequestConfig?) {
            val intent = Intent(fragment, ClipImageActivity::class.java)
            intent.putExtra(ImageSelector.KEY_CONFIG, config)
            fragment.startActivityForResult(intent, requestCode)
        }
    }
}