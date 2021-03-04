package consultan.vanke.com.activity.kt

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.provider.Settings
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.os.EnvironmentCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.umeng.analytics.MobclickAgent
import consultan.vanke.com.R
import consultan.vanke.com.activity.BaseActivity
import consultan.vanke.com.adapter.FolderAdapter
import consultan.vanke.com.adapter.ImageAdapter
import consultan.vanke.com.bean.Folder
import consultan.vanke.com.bean.Image
import consultan.vanke.com.bean.RequestConfig
import consultan.vanke.com.utils.*
import consultan.vanke.com.utils.SdcardHelper.Images
import consultan.vanke.com.utils.SdcardHelper.PATH
import kotlinx.android.synthetic.main.activity_image_select.*
import me.yokeyword.fragmentation.SupportActivity
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ImageSelectorActivity : BaseActivity() {
    private var mAdapter: ImageAdapter? = null
    private var mLayoutManager: GridLayoutManager? = null
    private var mFolders: ArrayList<Folder>? = null
    private var mFolder: Folder? = null
    private var applyLoadImage = false
    private var applyCamera = false
    private var mCameraUri: Uri? = null
    private var mCameraImagePath: String? = null
    private var isOpenFolder = false
    private var isShowTime = false
    private var isInitFolder = false
    private var isSingle = false
    private var canPreview = true
    private var mMaxCount = 0
    private var useCamera = true
    private var onlyTakePhoto = false
    private val mHideHandler = Handler()
    private val mHide = Runnable { hideTime() }
    //用于接收从外面传进来的已选择的图片列表。当用户原来已经有选择过图片，现在重新打开选择器，允许用
// 户把先前选过的图片传进来，并把这些图片默认为选中状态。
    private var mSelectedImages: ArrayList<String>? = null

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
    }

    private fun initListener() {
        btn_back.setOnClickListener { finish() }
        btn_preview!!.setOnClickListener {
            val images = ArrayList<Image>()
            images.addAll(mAdapter!!.selectImages)
            toPreviewActivity(images, 0)
        }
        btn_confirm!!.setOnClickListener { confirm() }
        btn_folder.setOnClickListener {
            if (isInitFolder) {
                if (isOpenFolder) {
                    closeFolder()
                } else {
                    openFolder()
                }
            }
        }
        masking!!.setOnClickListener { closeFolder() }
        rv_image!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                changeTime()
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                changeTime()
            }
        })
    }

    /**
     * 初始化图片列表
     */
    private fun initImageList() { // 判断屏幕方向
        val configuration = resources.configuration
        mLayoutManager = if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            GridLayoutManager(this, 3)
        } else {
            GridLayoutManager(this, 5)
        }
        rv_image!!.layoutManager = mLayoutManager
        mAdapter = ImageAdapter(this, mMaxCount, isSingle, canPreview)
        rv_image!!.adapter = mAdapter
        (rv_image!!.itemAnimator as SimpleItemAnimator?)!!.supportsChangeAnimations = false
        if (mFolders != null && !mFolders!!.isEmpty()) {
            setFolder(mFolders!![0])
        }
        mAdapter!!.setOnImageSelectListener { image, isSelect, selectCount -> setSelectImageCount(selectCount) }
        mAdapter!!.setOnItemClickListener(object : ImageAdapter.OnItemClickListener {
            override fun OnItemClick(image: Image, position: Int) {
                toPreviewActivity(mAdapter!!.data, position)
            }

            override fun OnCameraClick() {
                checkPermissionAndCamera()
            }
        })
    }

    /**
     * 初始化图片文件夹列表
     */
    private fun initFolderList() {
        if (mFolders != null && !mFolders!!.isEmpty()) {
            isInitFolder = true
            rv_folder!!.layoutManager = LinearLayoutManager(this@ImageSelectorActivity)
            val adapter = FolderAdapter(this@ImageSelectorActivity, mFolders)
            adapter.setOnFolderSelectListener { folder ->
                setFolder(folder)
                closeFolder()
            }
            rv_folder!!.adapter = adapter
        }
    }

    /**
     * 刚开始的时候文件夹列表默认是隐藏的
     */
    private fun hideFolderList() {
        rv_folder!!.post {
            rv_folder!!.translationY = rv_folder!!.height.toFloat()
            rv_folder!!.visibility = View.GONE
            rv_folder!!.setBackgroundColor(Color.WHITE)
        }
    }

    /**
     * 设置选中的文件夹，同时刷新图片列表
     *
     * @param folder
     */
    private fun setFolder(folder: Folder?) {
        if (folder != null && mAdapter != null && folder != mFolder) {
            mFolder = folder
            tv_folder_name!!.text = folder.name
            rv_image!!.scrollToPosition(0)
            mAdapter!!.refresh(folder.images, folder.isUseCamera)
        }
    }

    private fun setSelectImageCount(count: Int) {
        if (count == 0) {
            btn_confirm!!.isEnabled = false
            btn_preview!!.isEnabled = false
            tv_confirm!!.setText(R.string.selector_send)
            tv_preview!!.setText(R.string.selector_preview)
        } else {
            btn_confirm!!.isEnabled = true
            btn_preview!!.isEnabled = true
            tv_preview!!.text = getString(R.string.selector_preview) + "(" + count + ")"
            if (isSingle) {
                tv_confirm!!.setText(R.string.selector_send)
            } else if (mMaxCount > 0) {
                tv_confirm!!.text = getString(R.string.selector_send) + "(" + count + "/" + mMaxCount + ")"
            } else {
                tv_confirm!!.text = getString(R.string.selector_send) + "(" + count + ")"
            }
        }
    }

    /**
     * 弹出文件夹列表
     */
    @SuppressLint("ObjectAnimatorBinding")
    private fun openFolder() {
        if (!isOpenFolder) {
            masking!!.visibility = View.VISIBLE
            val animator = ObjectAnimator.ofFloat(rv_folder, "translationY",
                    rv_folder!!.height.toFloat(), 0f).setDuration(300)
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    super.onAnimationStart(animation)
                    rv_folder!!.visibility = View.VISIBLE
                }
            })
            animator.start()
            isOpenFolder = true
        }
    }

    /**
     * 收起文件夹列表
     */
    @SuppressLint("ObjectAnimatorBinding")
    private fun closeFolder() {
        if (isOpenFolder) {
            masking!!.visibility = View.GONE
            val animator = ObjectAnimator.ofFloat(rv_folder, "translationY", 0f, rv_folder!!.height.toFloat()).setDuration(300)
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    rv_folder!!.visibility = View.GONE
                }
            })
            animator.start()
            isOpenFolder = false
        }
    }

    /**
     * 隐藏时间条
     */
    @SuppressLint("ObjectAnimatorBinding")
    private fun hideTime() {
        if (isShowTime) {
            ObjectAnimator.ofFloat(tv_time, "alpha", 1f, 0f).setDuration(300).start()
            isShowTime = false
        }
    }

    /**
     * 显示时间条
     */
    @SuppressLint("ObjectAnimatorBinding")
    private fun showTime() {
        if (!isShowTime) {
            ObjectAnimator.ofFloat(tv_time, "alpha", 0f, 1f).setDuration(300).start()
            isShowTime = true
        }
    }

    /**
     * 改变时间条显示的时间（显示图片列表中的第一个可见图片的时间）
     */
    private fun changeTime() {
        val firstVisibleItem = firstVisibleItem
        val image = mAdapter!!.getFirstVisibleImage(firstVisibleItem)
        if (image != null) {
            val time = DateUtils.getImageTime(this, image.time)
            tv_time!!.text = time
            showTime()
            mHideHandler.removeCallbacks(mHide)
            mHideHandler.postDelayed(mHide, 1500)
        }
    }

    private val firstVisibleItem: Int
        private get() = mLayoutManager!!.findFirstVisibleItemPosition()

    private fun confirm() {
        if (mAdapter == null) {
            return
        }
        //因为图片的实体类是Image，而我们返回的是String数组，所以要进行转换。
        val selectImages = mAdapter!!.selectImages
        val images = ArrayList<String?>()
        for (image in selectImages) {
            images.add(image.path)
        }
        saveImageAndFinish(images, false)
    }

    private fun saveImageAndFinish(images: ArrayList<String?>, isCameraImage: Boolean) { //点击确定，把选中的图片通过Intent传给上一个Activity。
        setResult(images, isCameraImage)
        finish()
        //        if (!VersionUtils.isAndroidQ() || images.isEmpty()) {
//            //点击确定，把选中的图片通过Intent传给上一个Activity。
//            setResult(images, isCameraImage);
//            finish();
//        }
//
//        // 适配android 10，先把图片拷贝到app私有目录
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                final ArrayList<String> newImages = new ArrayList<>();
//                Context context = ImageSelectorActivity.this;
//                String dir = ImageUtil.getImageCacheDir(context);
//                for (String image : images) {
//                    String md5 = MD5Utils.md5(image);
//                    if (md5 != null) {
//                        Bitmap bitmap = ImageUtil.getBitmapFromUri(context, UriUtils.getImageContentUri(context, image), null);
//                        if (bitmap != null) {
//                            String newImage = ImageUtil.saveImage(bitmap, dir, md5);
//                            if (!StringUtils.isEmptyString(newImage)) {
//                                newImages.add(newImage);
//                            }
//                        }
//                    }
//                }
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        setResult(newImages, isCameraImage);
//                        finish();
//                    }
//                });
//            }
//        }).start();
    }

    private fun setResult(images: ArrayList<String?>, isCameraImage: Boolean) {
        val intent = Intent()
        intent.putStringArrayListExtra(ImageSelector.SELECT_RESULT, images)
        intent.putExtra(ImageSelector.IS_CAMERA_IMAGE, isCameraImage)
        setResult(Activity.RESULT_OK, intent)
    }

    private fun toPreviewActivity(images: ArrayList<Image>?, position: Int) {
        if (images != null && !images.isEmpty()) {
            PreviewActivity.openActivity(this, images,
                    mAdapter!!.selectImages, isSingle, mMaxCount, position)
        }
    }

    override fun onStart() {
        super.onStart()
        if (applyLoadImage) {
            applyLoadImage = false
            checkPermissionAndLoadImages()
        }
        if (applyCamera) {
            applyCamera = false
            checkPermissionAndCamera()
        }
    }

    override fun initContentView(bundle: Bundle?) {
        val intent = intent
        val config: RequestConfig = intent.getParcelableExtra(ImageSelector.KEY_CONFIG)
        mMaxCount = config.maxSelectCount
        isSingle = config.isSingle
        canPreview = config.canPreview
        useCamera = config.useCamera
        mSelectedImages = config.selected
        onlyTakePhoto = config.onlyTakePhoto
        if (onlyTakePhoto) { // 仅拍照
            checkPermissionAndCamera()
        } else {
            setContentView(R.layout.activity_image_select)
            setStatusBarColor()
            initView()
            initListener()
            initImageList()
            checkPermissionAndLoadImages()
            hideFolderList()
            setSelectImageCount(0)
        }
    }

    override fun initElements() {}
    override fun initData() {}
    override fun initEvent() {}
    /**
     * 处理图片预览页返回的结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ImageSelector.RESULT_CODE) {
            if (data != null && data.getBooleanExtra(ImageSelector.IS_CONFIRM, false)) { //如果用户在预览页点击了确定，就直接把用户选中的图片返回给用户。
                confirm()
            } else { //否则，就刷新当前页面。
                mAdapter!!.notifyDataSetChanged()
                setSelectImageCount(mAdapter!!.selectImages.size)
            }
        } else if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val images = ArrayList<String?>()
                if (VersionUtils.isAndroidQ()) {
                    sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, mCameraUri))
                    images.add(UriUtils.getPathForUri(this, mCameraUri))
                } else {
                    sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(File(mCameraImagePath))))
                    images.add(mCameraImagePath)
                }
                saveImageAndFinish(images, true)
            } else {
                if (onlyTakePhoto) {
                    finish()
                }
            }
        }
    }

    /**
     * 横竖屏切换处理
     *
     * @param newConfig
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (mLayoutManager != null && mAdapter != null) { //切换为竖屏
            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                mLayoutManager!!.spanCount = 3
            } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mLayoutManager!!.spanCount = 5
            }
            mAdapter!!.notifyDataSetChanged()
        }
    }

    /**
     * 检查权限并加载SD卡里的图片。
     */
    private fun checkPermissionAndLoadImages() {
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) { //            Toast.makeText(this, "没有图片", Toast.LENGTH_LONG).show();
            return
        }
        val hasWriteExternalPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (hasWriteExternalPermission == PackageManager.PERMISSION_GRANTED) { //有权限，加载图片。
            loadImageForSDCard()
        } else { //没有权限，申请权限。
            ActivityCompat.requestPermissions(this@ImageSelectorActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_WRITE_EXTERNAL_REQUEST_CODE)
        }
    }

    /**
     * 检查权限并拍照。
     */
    private fun checkPermissionAndCamera() {
        val hasCameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val hasWriteExternalPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (hasCameraPermission == PackageManager.PERMISSION_GRANTED
                && hasWriteExternalPermission == PackageManager.PERMISSION_GRANTED) { //有调起相机拍照。
            openCamera()
        } else { //没有权限，申请权限。
            ActivityCompat.requestPermissions(this@ImageSelectorActivity, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_CAMERA_REQUEST_CODE)
        }
    }

    /**
     * 处理权限申请的回调。
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_WRITE_EXTERNAL_REQUEST_CODE) {
            if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) { //允许权限，加载图片。
                loadImageForSDCard()
            } else { //拒绝权限，弹出提示框。
                showExceptionDialog(true)
            }
        } else if (requestCode == PERMISSION_CAMERA_REQUEST_CODE) {
            if (grantResults.size > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) { //允许权限，有调起相机拍照。
                openCamera()
            } else { //拒绝权限，弹出提示框。
                showExceptionDialog(false)
            }
        }
    }

    /**
     * 发生没有权限等异常时，显示一个提示dialog.
     */
    private fun showExceptionDialog(applyLoad: Boolean) {
        AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(R.string.selector_hint)
                .setMessage(R.string.selector_permissions_hint)
                .setNegativeButton(R.string.selector_cancel) { dialog, which ->
                    dialog.cancel()
                    finish()
                }.setPositiveButton(R.string.selector_confirm) { dialog, which ->
                    dialog.cancel()
                    startAppSettings()
                    if (applyLoad) {
                        applyLoadImage = true
                    } else {
                        applyCamera = true
                    }
                }.show()
    }

    /**
     * 从SDCard加载图片。
     */
    private fun loadImageForSDCard() {
        ImageModel.loadImageForSDCard(this) { folders ->
            mFolders = folders
            runOnUiThread {
                if (mFolders != null && !mFolders!!.isEmpty()) {
                    initFolderList()
                    mFolders!![0].isUseCamera = useCamera
                    setFolder(mFolders!![0])
                    if (mSelectedImages != null && mAdapter != null) {
                        mAdapter!!.setSelectedImages(mSelectedImages)
                        mSelectedImages = null
                        setSelectImageCount(mAdapter!!.selectImages.size)
                    }
                }
            }
        }
    }

    /**
     * 调起相机拍照
     */
    private fun openCamera() {
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (captureIntent.resolveActivity(packageManager) != null) {
            var photoFile: File? = null
            var photoUri: Uri? = null
            if (VersionUtils.isAndroidQ()) {
                photoUri = createImagePathUri()
            } else {
                try {
                    photoFile = createImageFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                if (photoFile != null) {
                    mCameraImagePath = photoFile.absolutePath
                    photoUri = if (VersionUtils.isAndroidN()) { //通过FileProvider创建一个content类型的Uri
                        FileProvider.getUriForFile(this, "$packageName.imageSelectorProvider", photoFile)
                    } else {
                        Uri.fromFile(photoFile)
                    }
                }
            }
            mCameraUri = photoUri
            if (photoUri != null) {
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                startActivityForResult(captureIntent, CAMERA_REQUEST_CODE)
            }
        }
    }

    /**
     * 创建一条图片地址uri,用于保存拍照后的照片
     *
     * @return 图片的uri
     */
    fun createImagePathUri(): Uri? {
        val status = Environment.getExternalStorageState()
        val timeFormatter = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val time = System.currentTimeMillis()
        val imageName = timeFormatter.format(Date(time))
        // ContentValues是我们希望这条记录被创建时包含的数据信息
        val values = ContentValues(2)
        values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        // 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
        return if (status == Environment.MEDIA_MOUNTED) {
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        } else {
            contentResolver.insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, values)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = String.format("JPEG_%s.jpg", timeStamp)
        val storageDir = File(Environment.getExternalStorageDirectory()
                .toString() + File.separator + PATH + File.separator + Images)
        if (!storageDir.exists()) {
            storageDir.mkdir()
        }
        val tempFile = File(storageDir, imageFileName)
        return if (Environment.MEDIA_MOUNTED != EnvironmentCompat.getStorageState(tempFile)) {
            null
        } else tempFile
    }

    /**
     * 启动应用的设置
     */
    private fun startAppSettings() {
        val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:$packageName")
        startActivity(intent)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN && isOpenFolder) {
            closeFolder()
            return true
        }
        return super.onKeyDown(keyCode, event)
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
        private const val PERMISSION_WRITE_EXTERNAL_REQUEST_CODE = 0x00000011
        private const val PERMISSION_CAMERA_REQUEST_CODE = 0x00000012
        private const val CAMERA_REQUEST_CODE = 0x00000010
        /**
         * 启动图片选择器
         *
         * @param activity
         * @param requestCode
         * @param config
         */
        fun openActivity(activity: Activity, requestCode: Int, config: RequestConfig?) {
            val intent = Intent(activity, ImageSelectorActivity::class.java)
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
            val intent = Intent(fragment.activity, ImageSelectorActivity::class.java)
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
            val intent = Intent(fragment, ImageSelectorActivity::class.java)
            intent.putExtra(ImageSelector.KEY_CONFIG, config)
            fragment.startActivityForResult(intent, requestCode)
        }
    }
}