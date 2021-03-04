package consultan.vanke.com.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import consultan.vanke.com.R;
import consultan.vanke.com.activity.MainActivity;
import consultan.vanke.com.adapter.GuideViewPageAdapter;
import consultan.vanke.com.base.BaseApplication;
import consultan.vanke.com.constant.ConstantLibrary;
import consultan.vanke.com.utils.DensityUtil;
import consultan.vanke.com.utils.LazyOnClickListener;

import java.util.ArrayList;
import java.util.List;

public class GuideFragment extends BaseFragment {

    private int[] imageArray = new int[]
            {R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round};
    private String[] tipsOneList = new String[]{ConstantLibrary.GUIDE_TIPS, ConstantLibrary.GUIDE_TIPS, ConstantLibrary.GUIDE_TIPS};
    private String[] tipsTwoList = new String[]{ConstantLibrary.GUIDE_TIPS_CONTENT, ConstantLibrary.GUIDE_TIPS_CONTENT, ConstantLibrary.GUIDE_TIPS_CONTENT};
    private List<View> viewList = new ArrayList<>();
    private ViewPager vp_guide;
    private FrameLayout fl_guide_next;
    private LinearLayout ll_guide_dots;
    private ImageView iv_dots_one;
    private ImageView iv_dots_two;
    private ImageView iv_dots_three;
    private ImageView[] dotsView;

    @Override
    protected void initViews() {
        vp_guide = fragmentView.findViewById(R.id.vp_guide);
        fl_guide_next = fragmentView.findViewById(R.id.fl_guide_next);
        ll_guide_dots = fragmentView.findViewById(R.id.ll_guide_dots);
        iv_dots_one = fragmentView.findViewById(R.id.iv_dots_one);
        iv_dots_two = fragmentView.findViewById(R.id.iv_dots_two);
        iv_dots_three = fragmentView.findViewById(R.id.iv_dots_three);
        dotsView = new ImageView[]{iv_dots_one, iv_dots_two, iv_dots_three};
        InitViewPage();
    }

    private void InitViewPage() {
        for (int i = 0; i < imageArray.length; i++) {
            View view = View.inflate(_mActivity, R.layout.viewpager_guide, null);
            ImageView iv_guide = view.findViewById(R.id.iv_guide);
            TextView tv_guide_tips_one = view.findViewById(R.id.tv_guide_tips_one);
            TextView tv_guide_tips_two = view.findViewById(R.id.tv_guide_tips_two);
            iv_guide.setImageResource(imageArray[i]);
            tv_guide_tips_one.setText(tipsOneList[i]);
            tv_guide_tips_two.setText(tipsTwoList[i]);
            viewList.add(view);
        }
        vp_guide.setAdapter(new GuideViewPageAdapter(viewList));
        vp_guide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == imageArray.length - 1) {
                    fl_guide_next.setVisibility(View.VISIBLE);
                } else {
                    fl_guide_next.setVisibility(View.INVISIBLE);
                }
                ll_guide_dots.setVisibility(View.VISIBLE);
                for (int i = 0; i < dotsView.length; i++) {
                    ImageView imageView = dotsView[i];
                    ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                    if (position == i) {
                        imageView.setBackgroundResource(R.drawable.shape_guide_dots_select);
                        layoutParams.width = DensityUtil.dip2px(28);
                    } else {
                        imageView.setBackgroundResource(R.drawable.shape_guide_dots_unselect);
                        layoutParams.width = DensityUtil.dip2px(7);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initEvents() {
        LazyOnClickListener listener = new LazyOnClickListener() {
            @Override
            public void onLazyClick(View v) {
                if (v == fl_guide_next) {
                    BaseApplication.getInstance().settings.ISFIRSTLOGIN.setValue(false);
                    _mActivity.startActivity(new Intent(_mActivity, MainActivity.class));
                    _mActivity.finish();
                }
            }
        };
        fl_guide_next.setOnClickListener(listener);
    }


    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guide, null);
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
    }
}
