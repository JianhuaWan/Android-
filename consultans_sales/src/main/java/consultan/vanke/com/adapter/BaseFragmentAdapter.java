package consultan.vanke.com.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import consultan.vanke.com.fragment.BaseFragment;

import java.util.List;

public class BaseFragmentAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> mfragments;
    private List<String> titles;

    public BaseFragmentAdapter(FragmentManager fm, List<BaseFragment> mfragments, List<String> titles) {
        super(fm);
        this.mfragments = mfragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mfragments.get(position);
    }

    @Override
    public int getCount() {
        return mfragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
