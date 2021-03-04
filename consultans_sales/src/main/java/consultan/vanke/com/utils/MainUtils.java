package consultan.vanke.com.utils;

import android.animation.ValueAnimator;
import com.google.android.material.tabs.TabLayout;
import android.view.View;
import android.widget.ImageView;
import consultan.vanke.com.R;
import consultan.vanke.com.fragment.HomePagerFrg;
import consultan.vanke.com.fragment.MinePagerFrg;
import consultan.vanke.com.fragment.ContentPagerFrg;
import me.yokeyword.fragmentation.SupportFragment;

public class MainUtils {
    public static void initMainFragment(SupportFragment supportFragment, SupportFragment[] mFragments) {
        SupportFragment firstFragment = supportFragment.findChildFragment(HomePagerFrg.class);
        if (firstFragment == null) {
            mFragments[0] = new HomePagerFrg();
            mFragments[1] = new ContentPagerFrg();
            mFragments[2] = new MinePagerFrg();
            supportFragment.loadMultipleRootFragment(R.id.fl_tab_container, 0, mFragments[0], mFragments[1], mFragments[2]);
        }
    }

    public static void setTabIconWidth(TabLayout tabLayout) {
        View view = tabLayout.getTabAt(0).getCustomView();
        ImageView icon = (ImageView) view.findViewById(R.id.tv_tab);
        AppMainStatus.TABICONWIDTH = icon.getWidth();
    }

    /**
     * 适当放大
     */
    public static void startTabAnimal(final View view, final int width) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1.0f, 1.3f, 1.0f).setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.getLayoutParams().width = (int) (width * (float) valueAnimator.getAnimatedValue());
                view.getLayoutParams().height = (int) (width * (float) valueAnimator.getAnimatedValue());
                view.requestLayout();
            }
        });
        valueAnimator.start();
    }

    /**
     * 还原
     *
     * @param icon
     * @param tabiconwidth
     */
    public static void startTabAnimalend(final ImageView icon, final int tabiconwidth) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1.0f, 1.0f).setDuration(100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                icon.getLayoutParams().width = (int) (tabiconwidth * (float) valueAnimator.getAnimatedValue());
                icon.getLayoutParams().height = (int) (tabiconwidth * (float) valueAnimator.getAnimatedValue());
                icon.requestLayout();
            }
        });
        valueAnimator.start();
    }
}
