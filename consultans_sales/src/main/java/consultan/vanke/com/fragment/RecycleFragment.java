package consultan.vanke.com.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.buildbui.net.ApiStateException;
import com.buildbui.net.NetMaybeObservable;
import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import consultan.vanke.com.R;
import consultan.vanke.com.adapter.CommonAdapter;
import consultan.vanke.com.adapter.ViewHolder;
import consultan.vanke.com.bean.Izuiyou;
import consultan.vanke.com.listener.OnItemClickListener;
import consultan.vanke.com.utils.ToastUtils;
import consultan.vanke.com.viewmodel.IzuiyouViewModel;
import consultan.vanke.com.widgets.SimpleToolbar;
import io.reactivex.MaybeObserver;
import pl.droidsonroids.gif.GifImageView;

public class RecycleFragment extends BaseFragment {
    @BindView(R.id.recy_view)
    RecyclerView recyView;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    @BindView(R.id.gif_View)
    GifImageView gifView;
    private View view;
    IzuiyouViewModel izuiyouViewModel;
    private String start;
    List<Izuiyou> subjectsBeans = new ArrayList<>();
    private CommonAdapter<Izuiyou> adapter;
    public SimpleToolbar mSimpleToolbar;

    @Override
    protected void initViews() {
        gifView.setImageResource(R.drawable.loading);
        izuiyouViewModel = ViewModelProviders.of(_mActivity).get(IzuiyouViewModel.class);
        mSimpleToolbar = view.findViewById(R.id.simple_toolbar);
    }

    @Override
    protected void initDatas() {
        mSimpleToolbar.setMainTitle(getString(R.string.app_name));
        recyView.setLayoutManager(new LinearLayoutManager(_mActivity));
        adapter = new CommonAdapter<Izuiyou>(_mActivity, subjectsBeans, R.layout.item_movie) {
            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
            }

            @Override
            public void convert(ViewHolder holder, Izuiyou subjectsBean) {
                TextView name = holder.getView(R.id.name);
                TextView tv_content = holder.getView(R.id.tv_content);
                ImageView imageView = holder.getView(R.id.myheadIcon);
                ImageView img_url = holder.getView(R.id.img_url);
                //=======================================
                NiceVideoPlayer niceVideoPlayer = (NiceVideoPlayer) holder.getView(R.id.nice_video_player);
                if (niceVideoPlayer == NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer()) {
                    NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                }
                //=======================================
                name.setText(subjectsBean.getName());
                tv_content.setText(subjectsBean.getContent());
                Glide.with(getActivity()).load(subjectsBean.getAvatar_urls()).into(imageView);
                if (subjectsBean.getUrls() != null) {
                    img_url.setVisibility(View.GONE);
                    niceVideoPlayer.setVisibility(View.VISIBLE);
                    niceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK);
                    niceVideoPlayer.setUp(subjectsBean.getUrls(), null);
                    TxVideoPlayerController controller = new TxVideoPlayerController(_mActivity);
                    controller.setTitle("");
                    if (!subjectsBean.getUrls().contains("sz/src")) {
                        Glide.with(getActivity())
                                .load(subjectsBean.getUrls())
//                                .placeholder(R.mipmap.ic_launcher)
                                .into(controller.imageView());
                    }
                    niceVideoPlayer.setController(controller);
                } else {
                    if (!subjectsBean.getImgs().contains("sz/src")) {
                        Glide.with(getActivity()).load(subjectsBean.getImgs()).into(img_url);
                    }
                    img_url.setVisibility(View.VISIBLE);
                    niceVideoPlayer.setVisibility(View.GONE);
                }
            }

        };
        recyView.setAdapter(adapter);
        refreshlayout.autoRefresh();
        izuiyouViewModel.getListData().subscribe(observer);
    }

    @Override
    public void onStop() {
        super.onStop();
        // 在onStop时释放掉播放器
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    public boolean onBackPressedSupport() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) {
            return true;
        }
        return super.onBackPressedSupport();
    }

    @Override
    public void onResume() {
        super.onResume();
        setStatusBarColors(getResources().getColor(R.color.color_00BA91), false);
    }

    @Override
    protected void initEvents() {
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                subjectsBeans.clear();
                izuiyouViewModel.getListData().subscribe(observer);
            }
        });
        refreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                izuiyouViewModel.getListData().subscribe(observer);
            }
        });
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                Izuiyou bean = adapter.getItem(position);
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
        mSimpleToolbar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });

        mSimpleToolbar.setRightTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setStatusBarColors(getResources().getColor(R.color.color_00BA91), false);
        view = inflater.inflate(R.layout.recycle_fragment, null);
        return view;
    }

    private MaybeObserver<List<Izuiyou>> observer =
            new NetMaybeObservable<List<Izuiyou>>() {
                @Override
                public void onSuccess(List<Izuiyou> dataBean) {
                    if (subjectsBeans.size() == 0) {
                        adapter.setDatas(dataBean);
                        if (refreshlayout != null)
                            refreshlayout.finishRefresh();
                    } else {
                        adapter.addDatas(dataBean);
                        if (refreshlayout != null)
                            refreshlayout.finishLoadMore();
                    }

                }

                @Override
                public void onError(Throwable e) {
                    if (e instanceof ApiStateException) {
                        ApiStateException apiStateException = (ApiStateException) e;
                        ToastUtils.show(apiStateException.getMsg());
                    } else {
                        ToastUtils.show(e.getMessage());
                    }
                    if (subjectsBeans.size() == 0) {
                        refreshlayout.finishRefresh();
                    } else {
                        refreshlayout.finishLoadMore();
                    }

                }
            };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (gifView != null) {
            gifView = null;
        }
    }
}
