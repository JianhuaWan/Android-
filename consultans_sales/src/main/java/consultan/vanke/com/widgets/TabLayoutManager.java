package consultan.vanke.com.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import consultan.vanke.com.R;

public class TabLayoutManager {
    public static final int[] mTabRes = new int[]{R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round};
    public static final int[] mTabRespressed = new int[]{R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    public static final int[] mTabTitle = new int[]{R.string.home, R.string.content, R.string.mine};

    public static View getTabView(Context context, int position, int defaultposition) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_tab_item, null);
        ImageView tabIcon = (ImageView) view.findViewById(R.id.tv_tab);
        TextView tabText = (TextView) view.findViewById(R.id.tv_text);
        tabText.setText(mTabTitle[position]);
        if (position == defaultposition) {
            tabIcon.setImageResource(TabLayoutManager.mTabRespressed[position]);
            tabText.setTextColor(context.getResources().getColor(R.color.color_00BA91));
        } else {
            tabIcon.setImageResource(TabLayoutManager.mTabRes[position]);
            tabText.setTextColor(context.getResources().getColor(R.color.color_b4b4b4));
        }
        return view;
    }
}
