package consultan.vanke.com.utils;

import android.view.View;

public abstract class LazyOnClickListener implements View.OnClickListener {

    private long lastClickTime = -1;
    private long mInterval = 700;

    @Override
    public void onClick(View view) {
        if (checkPermission() && moreCheck()) {
            onLazyClick(view);
        }
    }

    private boolean checkPermission() {
        if (-1 != lastClickTime) {
            long tmp = System.currentTimeMillis();
            if (mInterval > tmp - lastClickTime) {
                lastClickTime = tmp;
                return false;
            } else {
                lastClickTime = tmp;
                return true;
            }
        } else {
            lastClickTime = System.currentTimeMillis();
            return true;
        }
    }

    protected boolean moreCheck() {
        return true;
    }

    public void setInterval(long interval) {
        mInterval = interval;
    }

    public abstract void onLazyClick(View v);
}
