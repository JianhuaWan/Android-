package consultan.vanke.com.adapter;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class GuideViewPageAdapter extends PagerAdapter {
    private List<View> viewList;

    public GuideViewPageAdapter(List<View> views) {
        this.viewList = views;
    }

    @Override
    public int getCount() {
        if (viewList != null) {
            return viewList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(viewList.get(position));
    }
}
