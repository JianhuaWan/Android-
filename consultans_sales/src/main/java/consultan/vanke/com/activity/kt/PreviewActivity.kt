package consultan.vanke.com.activity.kt

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.RelativeLayout
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.umeng.analytics.MobclickAgent
import consultan.vanke.com.R
import consultan.vanke.com.activity.BaseActivity
import consultan.vanke.com.adapter.ImagePagerAdapter
import consultan.vanke.com.bean.Image
import consultan.vanke.com.utils.ImageSelector
import consultan.vanke.com.utils.VersionUtils
import kotlinx.android.synthetic.main.activity_preview.*
import java.util.*

class PreviewActivity : BaseActivity() {
    private var mImages: ArrayList<Image>? = null
    private var mSelectImages: ArrayList<Image>? = null
    private var isShowBar = true
    private var isConfirm = false
    private var isSingle = false
    private var mMaxCount = 0
    private var mSelectDrawable: BitmapDrawable? = null
    private var mUnSelectDrawable: BitmapDrawable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initContentView(bundle: Bundle?) {
        setContentView(R.layout.activity_preview)
        if (VersionUtils.isAndroidP()) { //设置页面全屏显示
            val lp = window.attributes
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            //设置页面延伸到刘海区显示
            window.attributes = lp
        }
        setStatusBarVisible(true)
        mImages = tempImages
        tempImages = null
        mSelectImages = tempSelectImages
        tempSelectImages = null
        val intent = intent
        mMaxCount = intent.getIntExtra(ImageSelector.MAX_SELECT_COUNT, 0)
        isSingle = intent.getBooleanExtra(ImageSelector.IS_SINGLE, false)
        val resources = resources
        val selectBitmap = BitmapFactory.decodeResource(resources, R.mipmap.icon_image_select)
        mSelectDrawable = BitmapDrawable(resources, selectBitmap)
        mSelectDrawable!!.setBounds(0, 0, selectBitmap.width, selectBitmap.height)
        val unSelectBitmap = BitmapFactory.decodeResource(resources, R.mipmap.icon_image_un_select)
        mUnSelectDrawable = BitmapDrawable(resources, unSelectBitmap)
        mUnSelectDrawable!!.setBounds(0, 0, unSelectBitmap.width, unSelectBitmap.height)
        setStatusBarColor()
        initView()
        initListener()
        initViewPager()
        tv_indicator!!.text = 1.toString() + "/" + mImages!!.size
        changeSelect(mImages!![0])
        vp_image!!.currentItem = intent.getIntExtra(ImageSelector.POSITION, 0)
    }

    override fun initElements() {}
    override fun initData() {}
    override fun initEvent() {}
    private fun initView() {
        val lp = rl_top_bar.getLayoutParams() as RelativeLayout.LayoutParams
        lp.topMargin = getStatusBarHeight(this)
        rl_top_bar.setLayoutParams(lp)
    }

    private fun initListener() {
        btn_back.setOnClickListener { finish() }
        btn_confirm!!.setOnClickListener {
            isConfirm = true
            finish()
        }
        tv_select!!.setOnClickListener { clickSelect() }
    }

    /**
     * 初始化ViewPager
     */
    private fun initViewPager() {
        val adapter = ImagePagerAdapter(this, mImages)
        vp_image!!.adapter = adapter
        adapter.setOnItemClickListener { position, image ->
            if (isShowBar) {
                hideBar()
            } else {
                showBar()
            }
        }
        vp_image!!.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                tv_indicator.setText("" + position + 1 + "/" + mImages!!.size)
                changeSelect(mImages!![position])
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

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

    /**
     * 显示和隐藏状态栏
     *
     * @param show
     */
    private fun setStatusBarVisible(show: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (show) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            } else {
                window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_FULLSCREEN)
            }
        }
    }

    /**
     * 显示头部和尾部栏
     */
    private fun showBar() {
        isShowBar = true
        setStatusBarVisible(true)
        //添加延时，保证StatusBar完全显示后再进行动画。
        rl_top_bar!!.postDelayed({
            if (rl_top_bar != null) {
                val animator = ObjectAnimator.ofFloat(rl_top_bar, "translationY",
                        rl_top_bar!!.translationY, 0f).setDuration(300)
                animator.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        super.onAnimationStart(animation)
                        if (rl_top_bar != null) {
                            rl_top_bar!!.visibility = View.VISIBLE
                        }
                    }
                })
                animator.start()
                ObjectAnimator.ofFloat(rl_bottom_bar, "translationY", rl_bottom_bar!!.translationY, 0f)
                        .setDuration(300).start()
            }
        }, 100)
    }

    /**
     * 隐藏头部和尾部栏
     */
    private fun hideBar() {
        isShowBar = false
        val animator = ObjectAnimator.ofFloat(rl_top_bar, "translationY", 0f, -rl_top_bar!!.height.toFloat()).setDuration(300)
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                if (rl_top_bar != null) {
                    rl_top_bar!!.visibility = View.GONE
                    //添加延时，保证rl_top_bar完全隐藏后再隐藏StatusBar。
                    rl_top_bar!!.postDelayed({ setStatusBarVisible(false) }, 5)
                }
            }
        })
        animator.start()
        ObjectAnimator.ofFloat(rl_bottom_bar, "translationY", 0f, rl_bottom_bar!!.height.toFloat())
                .setDuration(300).start()
    }

    private fun clickSelect() {
        val position = vp_image!!.currentItem
        if (mImages != null && mImages!!.size > position) {
            val image = mImages!![position]
            if (mSelectImages!!.contains(image)) {
                mSelectImages!!.remove(image)
            } else if (isSingle) {
                mSelectImages!!.clear()
                mSelectImages!!.add(image)
            } else if (mMaxCount <= 0 || mSelectImages!!.size < mMaxCount) {
                mSelectImages!!.add(image)
            }
            changeSelect(image)
        }
    }

    private fun changeSelect(image: Image) {
        tv_select!!.setCompoundDrawables(if (mSelectImages!!.contains(image)) mSelectDrawable else mUnSelectDrawable, null, null, null)
        setSelectImageCount(mSelectImages!!.size)
    }

    private fun setSelectImageCount(count: Int) {
        if (count == 0) {
            btn_confirm!!.isEnabled = false
            tv_confirm!!.setText(R.string.selector_send)
        } else {
            btn_confirm!!.isEnabled = true
            if (isSingle) {
                tv_confirm!!.setText(R.string.selector_send)
            } else if (mMaxCount > 0) {
                tv_confirm!!.text = getString(R.string.selector_send) + "(" + count + "/" + mMaxCount + ")"
            } else {
                tv_confirm!!.text = getString(R.string.selector_send) + "(" + count + ")"
            }
        }
    }

    override fun finish() { //Activity关闭时，通过Intent把用户的操作(确定/返回)传给ImageSelectActivity。
        val intent = Intent()
        intent.putExtra(ImageSelector.IS_CONFIRM, isConfirm)
        setResult(ImageSelector.RESULT_CODE, intent)
        super.finish()
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
        //tempImages和tempSelectImages用于图片列表数据的页面传输。
//之所以不要Intent传输这两个图片列表，因为要保证两位页面操作的是同一个列表数据，同时可以避免数据量大时，
// 用Intent传输发生的错误问题。
        private var tempImages: ArrayList<Image>? = null
        private var tempSelectImages: ArrayList<Image>? = null
        fun openActivity(activity: Activity, images: ArrayList<Image>?,
                         selectImages: ArrayList<Image>?, isSingle: Boolean,
                         maxSelectCount: Int, position: Int) {
            tempImages = images
            tempSelectImages = selectImages
            val intent = Intent(activity, PreviewActivity::class.java)
            intent.putExtra(ImageSelector.MAX_SELECT_COUNT, maxSelectCount)
            intent.putExtra(ImageSelector.IS_SINGLE, isSingle)
            intent.putExtra(ImageSelector.POSITION, position)
            activity.startActivityForResult(intent, ImageSelector.RESULT_CODE)
        }

        /**
         * 获取状态栏高度
         *
         * @param context
         * @return
         */
        fun getStatusBarHeight(context: Context): Int {
            var result = 0
            val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = context.resources.getDimensionPixelSize(resourceId)
            }
            return result
        }
    }
}