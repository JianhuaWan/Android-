package consultan.vanke.com.widgets;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import consultan.vanke.com.R;

public class CommonLinearLayoutItem extends LinearLayout {
    public CommonLinearLayoutItem(Context context) {
        super(context);
    }

    public CommonLinearLayoutItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater mInflater = LayoutInflater.from(context);
        View myView = mInflater.inflate(R.layout.common_linearlayout_item, null);
        addView(myView);
    }
}
