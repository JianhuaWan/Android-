package consultan.vanke.com.utils

import android.content.Context
import consultan.vanke.com.activity.kt.ClipImageActivity
import consultan.vanke.com.activity.kt.ImageSelectorActivity
import consultan.vanke.com.bean.RequestConfig
import me.yokeyword.fragmentation.SupportActivity
import java.util.*

object ImageSelector {
    /**
     * 图片选择的结果
     */
    const val SELECT_RESULT = "select_result"
    /**
     * 是否是来自于相机拍照的图片，
     * 只有本次调用相机拍出来的照片，返回时才为true。
     * 当为true时，图片返回当结果有且只有一张图片。
     */
    const val IS_CAMERA_IMAGE = "is_camera_image"
    const val KEY_CONFIG = "key_config"
    //最大的图片选择数
    const val MAX_SELECT_COUNT = "max_select_count"
    //是否单选
    const val IS_SINGLE = "is_single"
    //初始位置
    const val POSITION = "position"
    const val IS_CONFIRM = "is_confirm"
    const val RESULT_CODE = 0x00000012
    /**
     * 预加载图片
     *
     * @param context
     */
    fun preload(context: Context?) {
        ImageModel.preloadAndRegisterContentObserver(context)
    }

    /**
     * 清空缓存
     */
    fun clearCache(context: Context?) {
        ImageModel.clearCache(context)
    }

    @JvmStatic
    fun builder(): ImageSelectorBuilder {
        return ImageSelectorBuilder()
    }

    class ImageSelectorBuilder() {
        private val config: RequestConfig
        /**
         * 是否使用图片剪切功能。默认false。如果使用了图片剪切功能，相册只能单选。
         *
         * @param isCrop
         * @return
         */
        fun setCrop(isCrop: Boolean): ImageSelectorBuilder {
            config.isCrop = isCrop
            return this
        }

        /**
         * 图片剪切的宽高比，宽固定为手机屏幕的宽。
         *
         * @param ratio
         * @return
         */
        fun setCropRatio(ratio: Float): ImageSelectorBuilder {
            config.cropRatio = ratio
            return this
        }

        /**
         * 是否单选
         *
         * @param isSingle
         * @return
         */
        fun setSingle(isSingle: Boolean): ImageSelectorBuilder {
            config.isSingle = isSingle
            return this
        }

        /**
         * 是否可以点击放大图片查看，默认为true
         *
         * @param isViewImage
         * @return
         */
        @Deprecated("请使用canPreview(boolean canPreview);")
        fun setViewImage(isViewImage: Boolean): ImageSelectorBuilder {
            config.canPreview = isViewImage
            return this
        }

        /**
         * 是否可以点击预览，默认为true
         *
         * @param canPreview
         * @return
         */
        fun canPreview(canPreview: Boolean): ImageSelectorBuilder {
            config.canPreview = canPreview
            return this
        }

        /**
         * 是否使用拍照功能。
         *
         * @param useCamera 默认为true
         * @return
         */
        fun useCamera(useCamera: Boolean): ImageSelectorBuilder {
            config.useCamera = useCamera
            return this
        }

        fun onlyTakePhoto(onlyTakePhoto: Boolean): ImageSelectorBuilder {
            config.onlyTakePhoto = onlyTakePhoto
            return this
        }

        /**
         * 图片的最大选择数量，小于等于0时，不限数量，isSingle为false时才有用。
         *
         * @param maxSelectCount
         * @return
         */
        fun setMaxSelectCount(maxSelectCount: Int): ImageSelectorBuilder {
            config.maxSelectCount = maxSelectCount
            return this
        }

        /**
         * 接收从外面传进来的已选择的图片列表。当用户原来已经有选择过图片，现在重新打开
         * 选择器，允许用户把先前选过的图片传进来，并把这些图片默认为选中状态。
         *
         * @param selected
         * @return
         */
        fun setSelected(selected: ArrayList<String?>?): ImageSelectorBuilder {
            config.selected = selected
            return this
        }

        /**
         * 打开相册
         *
         * @param fragment
         * @param requestCode
         */
        fun start(fragment: SupportActivity?, requestCode: Int) {
            config.requestCode = requestCode
            // 仅拍照，useCamera必须为true
            if (config.onlyTakePhoto) {
                config.useCamera = true
            }
            if (config.isCrop) {
                ClipImageActivity.openActivity(fragment!!, requestCode, config)
            } else {
                ImageSelectorActivity.openActivity(fragment!!, requestCode, config)
            }
        }

        init {
            config = RequestConfig()
        }
    }
}