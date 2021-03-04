package consultan.vanke.com.fragment.kt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.buildbui.net.ApiStateException
import com.buildbui.net.NetMaybeObservable
import com.bumptech.glide.Glide
import com.xiao.nicevideoplayer.NiceVideoPlayer
import com.xiao.nicevideoplayer.NiceVideoPlayerManager
import com.xiao.nicevideoplayer.TxVideoPlayerController
import consultan.vanke.com.R
import consultan.vanke.com.adapter.CommonAdapter
import consultan.vanke.com.adapter.ViewHolder
import consultan.vanke.com.bean.kt.Izuiyou
import consultan.vanke.com.fragment.BaseFragment
import consultan.vanke.com.listener.OnItemClickListener
import consultan.vanke.com.utils.ToastUtils
import consultan.vanke.com.viewmodel.kt.IzuiyouViewModel
import io.reactivex.MaybeObserver
import kotlinx.android.synthetic.main.izuiyou_fragment.*
import kotlinx.android.synthetic.main.top_title_back_bar.*
import pl.droidsonroids.gif.GifImageView
import java.util.*


class IzuiyouFragment : BaseFragment() {
    private var izuiyouViewModel: IzuiyouViewModel? = null
    private var adapter: CommonAdapter<Izuiyou>? = null
    var subjectsBeans: MutableList<Izuiyou> = ArrayList()
    override fun initViews() {
    }

    override fun initDatas() {
        gif_View!!.setImageResource(R.drawable.loading)
        simple_toolbar.setMainTitle(getString(R.string.kttile))
        recy_view!!.layoutManager = LinearLayoutManager(_mActivity)
        dopostNet()
    }

    private fun dopostNet() {
        izuiyouViewModel = ViewModelProviders.of(_mActivity).get(IzuiyouViewModel::class.java);
        adapter = object : CommonAdapter<Izuiyou>(_mActivity, subjectsBeans, R.layout.item_movie) {
            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                super.onBindViewHolder(holder, position)
            }

            override fun convert(holder: ViewHolder, subjectsBean: Izuiyou) {
                val name = holder.getView<TextView>(R.id.name)
                val tv_content = holder.getView<TextView>(R.id.tv_content)
                val imageView = holder.getView<ImageView>(R.id.myheadIcon)
                val img_url = holder.getView<ImageView>(R.id.img_url)
                //=======================================
                val niceVideoPlayer = holder.getView<View>(R.id.nice_video_player) as NiceVideoPlayer
                if (niceVideoPlayer === NiceVideoPlayerManager.instance().currentNiceVideoPlayer) {
                    NiceVideoPlayerManager.instance().releaseNiceVideoPlayer()
                }
                //=======================================
                name.text = subjectsBean.name
                tv_content.text = subjectsBean.content
                Glide.with(activity!!).load(subjectsBean.avatar_urls).into(imageView)
                if (subjectsBean.urls != null) {
                    img_url.visibility = View.GONE
                    niceVideoPlayer.visibility = View.VISIBLE
                    niceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK)
                    niceVideoPlayer.setUp(subjectsBean.urls, null)
                    val controller = TxVideoPlayerController(_mActivity)
                    controller.setTitle("")
                    if (!subjectsBean.urls!!.contains("sz/src")) {
                        Glide.with(activity!!)
                                .load(subjectsBean.urls) //                                .placeholder(R.mipmap.ic_launcher)
                                .into(controller.imageView())
                    }
                    niceVideoPlayer.setController(controller)
                } else {
                    if (!subjectsBean.imgs!!.contains("sz/src")) {
                        Glide.with(activity!!).load(subjectsBean.imgs).into(img_url)
                    }
                    img_url.visibility = View.VISIBLE
                    niceVideoPlayer.visibility = View.GONE
                }
            }
        }
        recy_view!!.adapter = adapter
        refreshlayout.autoRefresh()
        izuiyouViewModel!!.listData.subscribe(observer)
    }

    override fun initEvents() {
        simple_toolbar.setLeftTitleClickListener {
            pop()
        }
        refreshlayout.setOnRefreshListener {
            subjectsBeans.clear()
            izuiyouViewModel!!.listData.subscribe(observer)
        }
        refreshlayout.setOnLoadMoreListener { izuiyouViewModel!!.listData.subscribe(observer) }
        adapter!!.setOnItemClickListener(object : OnItemClickListener<Any?> {
            override fun onItemClick(parent: ViewGroup?, view: View?, t: Any?, position: Int) {
//                val bean: Izuiyou = adapter!!.getItem(position)
            }

            override fun onItemLongClick(parent: ViewGroup?, view: View?, t: Any?, position: Int): Boolean {
                return true
            }
        })
    }

    override fun initContentView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setStatusBarColors(resources.getColor(R.color.color_00BA91), false)
        return View.inflate(_mActivity, R.layout.izuiyou_fragment, null)
    }

    private val observer: MaybeObserver<List<Izuiyou>> = object : NetMaybeObservable<List<Izuiyou>>() {
        override fun onSuccess(dataBean: List<Izuiyou>) {
            if (subjectsBeans.size == 0) {
                adapter!!.setDatas(dataBean)
                if (refreshlayout != null) refreshlayout.finishRefresh()
            } else {
                adapter!!.addDatas(dataBean)
                if (refreshlayout != null) refreshlayout.finishLoadMore()
            }
        }

        override fun onError(e: Throwable) {
            if (e is ApiStateException) {
                ToastUtils.show(e.msg)
            } else {
                ToastUtils.show(e.message)
            }
            if (subjectsBeans.size == 0) {
                refreshlayout.finishRefresh()
            } else {
                refreshlayout.finishLoadMore()
            }
        }
    }
}