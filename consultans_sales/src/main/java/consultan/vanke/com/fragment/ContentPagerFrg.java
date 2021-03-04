package consultan.vanke.com.fragment;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;

import com.androidkun.xtablayout.XTabLayout;

import consultan.vanke.com.R;
import consultan.vanke.com.adapter.BaseFragmentAdapter;
import consultan.vanke.com.constant.ConstantLibrary;

import java.util.ArrayList;

public class ContentPagerFrg extends BaseFragment {
    @BindView(R.id.tablayout)
    XTabLayout tablayout;
    @BindView(R.id.vp_view)
    ViewPager vpView;
    private View inflate;
    private ArrayList<String> titles;
    private ArrayList<BaseFragment> fragments;
    private BlockOneFragment blockOneFragment;
    private BlockTwoFragment blockTwoFragment;
    private BlockThreeFragment blockThreeFragment;

    @Override
    protected void initViews() {
        titles = new ArrayList<>();
        titles.add(getString(R.string.modelleft));
        titles.add(getString(R.string.modelcenter));
        titles.add(getString(R.string.modelright));
        blockOneFragment = BlockOneFragment.getInstance();
        blockTwoFragment = BlockTwoFragment.getInstance();
        blockThreeFragment = BlockThreeFragment.getInstance();
        fragments = new ArrayList<>();
        fragments.add(blockOneFragment);
        fragments.add(blockTwoFragment);
        fragments.add(blockThreeFragment);
        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getChildFragmentManager(), fragments, titles);
        vpView.setAdapter(adapter);
        vpView.setOffscreenPageLimit(ConstantLibrary.VIEWPAGERLIMIT);
        tablayout.setupWithViewPager(vpView);
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initEvents() {
        tablayout.addOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(XTabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(XTabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(XTabLayout.Tab tab) {
            }
        });
        vpView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflate = View.inflate(_mActivity, R.layout.content_fragment, null);
        return inflate;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        Log.e("onSupportVisible", "ContentPagerFrg");
    }

}
