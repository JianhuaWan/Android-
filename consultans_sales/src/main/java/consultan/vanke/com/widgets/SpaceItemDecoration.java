package consultan.vanke.com.widgets;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        outRect.left = space;
        outRect.bottom = space;
        //由于每行都只有4个，所以第一个都是4的倍数，把左边距设为0
        if (parent.getChildLayoutPosition(view) % 4 == 0) {
            outRect.left = 5;
        }
    }
}
