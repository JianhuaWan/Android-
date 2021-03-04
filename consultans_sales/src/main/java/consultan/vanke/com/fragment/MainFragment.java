package consultan.vanke.com.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import consultan.vanke.com.R;
import consultan.vanke.com.activity.MainActivity;
import consultan.vanke.com.bean.HomeJumpBean;
import consultan.vanke.com.constant.ConstantLibrary;
import consultan.vanke.com.event.MessageEvent;
import consultan.vanke.com.utils.AppMainStatus;
import consultan.vanke.com.utils.MainUtils;
import consultan.vanke.com.widgets.TabLayoutManager;
import me.yokeyword.fragmentation.SupportFragment;

public class MainFragment extends BaseFragment {
    private TabLayout mTabs;
    private SupportFragment[] mFragments;
    private RelativeLayout rl_tab;
    private ViewStub viewstub_loading;
    private SupportFragment currFrg;

    @Override
    protected void initViews() {
        mTabs = (TabLayout) fragmentView.findViewById(R.id.bottom_tab_layout);
        rl_tab = (RelativeLayout) fragmentView.findViewById(R.id.rl_tab);
        viewstub_loading = (ViewStub) fragmentView.findViewById(R.id.vb);

    }

    @Override
    protected void handleEvent(MessageEvent event) {
        super.handleEvent(event);
        if (null != event) {
            showTab();
        }
    }

    private void showTab() {
        if (null != rl_tab) {
            rl_tab.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void initDatas() {
        currFrg = mFragments[0];

    }

    @Override
    protected void initEvents() {
        for (int i = 0; i < mFragments.length; i++) {
            mTabs.addTab(mTabs.newTab().setCustomView(TabLayoutManager.getTabView(_mActivity, i, 0)));
        }
        mTabs.post(new Runnable() {
            @Override
            public void run() {
                MainUtils.setTabIconWidth(mTabs);
                mTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        showHideFragment(mFragments[tab.getPosition()], currFrg);
                        currFrg = mFragments[tab.getPosition()];
                        for (int i = 0; i < mTabs.getTabCount(); i++) {
                            View view = mTabs.getTabAt(i).getCustomView();
                            ImageView icon = (ImageView) view.findViewById(R.id.tv_tab);
                            icon.setTag(icon.getWidth());
                            TextView text = (TextView) view.findViewById(R.id.tv_text);
                            if (i == tab.getPosition()) {
                                MainUtils.startTabAnimal(icon, AppMainStatus.TABICONWIDTH);
                                icon.setImageResource(TabLayoutManager.mTabRespressed[i]);
                                text.setTextColor(getResources().getColor(R.color.color_00BA91));
                            } else {
                                MainUtils.startTabAnimalend(icon, AppMainStatus.TABICONWIDTH);
                                icon.setImageResource(TabLayoutManager.mTabRes[i]);
                                text.setTextColor(getResources().getColor(R.color.color_b4b4b4));
                            }
                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
            }
        });
    }


    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment_layout, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFragments = new SupportFragment[3];
        MainUtils.initMainFragment(this, mFragments);
    }


    //定义接收方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toAnotherFragment(MessageEvent<HomeJumpBean> tofragment) {
        if (!(tofragment.getData() instanceof HomeJumpBean)) {
            return;
        }
        HomeJumpBean bean = (HomeJumpBean) tofragment.getData();
        switch (bean.tag) {
            case ConstantLibrary.ONEFRAGMENT:
                start(new HomePagerFrg());
                break;
            case ConstantLibrary.LOGINFRAGMENT:
                start(LoginFragment.getInstance(getString(R.string.send_data)));
                break;
            case ConstantLibrary.RECYCLEFRAGMENT:
                start(new RecycleFragment());
                break;
            case ConstantLibrary.CHARTFRAGMENT:
                start(new ChartFragment());
                break;
            case ConstantLibrary.FLUTTERACTY:
                //跳转到flutter
//                startActivity(new Intent(_mActivity, FlutterActy.class));
                break;
            case ConstantLibrary.CHANGEURLFRAGMENT:
                start(new ChangeBaseUrlFragment());
                break;
            case ConstantLibrary.COUNTTIMEFRAGMENT:
                start(new CountTimeFragment());
                break;
            case ConstantLibrary.WEBVIEWFRAGMENT:
                start(new WebViewFragment());
                break;
            case ConstantLibrary.ZXINGCODEFRAGMENT:
                start(new ZxingCodeFragment());
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        setStatusBarColors(getResources().getColor(R.color.white_color), true);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setStatusBarColors(getResources().getColor(R.color.white_color), true);
        }
    }

    /**
     * 解决fragment返回问题
     * 2020年1月6日 17:05:21
     *
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        if (_mActivity.getTopFragment().getClass().getSimpleName().equalsIgnoreCase(ConstantLibrary.MAINFRAGMENT)) {
            ((MainActivity) getActivity()).onKeyDown();
        }
        return true;
    }
}
