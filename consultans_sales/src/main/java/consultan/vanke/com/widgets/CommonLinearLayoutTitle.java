package consultan.vanke.com.widgets;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import consultan.vanke.com.R;

public class CommonLinearLayoutTitle extends LinearLayout {
    public CommonLinearLayoutTitle(Context context) {
        super(context);
    }

    public CommonLinearLayoutTitle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater mInflater = LayoutInflater.from(context);
        View myView = mInflater.inflate(R.layout.common_linearlayout_title, null);
        addView(myView);
        ViewGroup.LayoutParams lp;
        lp = myView.getLayoutParams();
        lp.width = LayoutParams.MATCH_PARENT;
        lp.height = 100;
        setLayoutParams(lp);
    }

}
