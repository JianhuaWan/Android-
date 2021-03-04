package consultan.vanke.com.dialog;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import android.view.*;
import consultan.vanke.com.R;

public abstract class BaseDialog extends Dialog {
    protected Context mContext;
    private int offY;
    private int offX;
    private int mLocation = Gravity.CENTER;

    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        View contentView = View.inflate(context, getLayoutResID(), null);
        this.mContext = context;
        setCanceledOnTouchOutside(true);
        initView(contentView);
        setContentView(contentView);
    }

    public BaseDialog(Context context, int themeResId, int mLocation, boolean TouchOutsideistrue) {
        super(context, themeResId);
        View contentView = View.inflate(context, getLayoutResID(), null);
        this.mContext = context;
        this.mLocation = mLocation;
        setCanceledOnTouchOutside(TouchOutsideistrue);
        setContentView(contentView);
        initView(contentView);
    }

    public abstract int getLayoutResID();

    public void initView(View contentView) {
    }

    protected int getDialogLocation() {
        return mLocation;
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = getDialogLocation();
        Window window = getWindow();
        window.setBackgroundDrawableResource(setDialogBackgroundColor());
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams attr = window.getAttributes();
        if (attr != null) {
            attr.height = setDialogHeight();
            attr.width = setDialogWidth();
            attr.alpha = 1;
            window.setAttributes(attr);
        }
    }

    public void showButton() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = getDialogLocation();
        Window window = getWindow();
        window.setBackgroundDrawableResource(setDialogBackgroundColor());
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams attr = window.getAttributes();
        if (attr != null) {
            attr.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            attr.width = setDialogWidth();
            attr.alpha = 1;
            window.setAttributes(attr);
            window.setWindowAnimations(R.style.button_dialog_style);
        }
    }

    public void showCenter() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = getDialogLocation();
        Window window = getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams attr = window.getAttributes();
        if (attr != null) {
            WindowManager windowManager = getWindow().getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            attr.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            attr.width = (int) (display.getWidth() * 0.8);
            attr.alpha = 1;
            window.setAttributes(attr);
        }
    }

    protected Context getmContext() {
        return mContext;
    }

    protected void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    protected int getOffY() {
        return offY;
    }

    protected void setOffY(int offY) {
        this.offY = offY;
    }

    protected int getOffX() {
        return offX;
    }

    protected void setOffX(int offX) {
        this.offX = offX;
    }

    protected int getmLocation() {
        return mLocation;
    }

    protected void setmLocation(int mLocation) {
        this.mLocation = mLocation;
    }

    protected int setDialogWidth() {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }

    protected int setDialogHeight() {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }

    protected float setDialogApl() {
        return 1;
    }

    protected int setDialogBackgroundColor() {
        return android.R.color.transparent;
    }

    protected int setDialogGrayBackgoundColor() {
        return R.color.white_color;

    }

}
