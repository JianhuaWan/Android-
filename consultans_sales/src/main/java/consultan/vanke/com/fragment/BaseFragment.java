package consultan.vanke.com.fragment;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.githang.statusbar.StatusBarCompat;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import consultan.vanke.com.BuildConfig;
import consultan.vanke.com.R;
import consultan.vanke.com.dialog.CommonDialog;
import consultan.vanke.com.event.MessageEvent;
import consultan.vanke.com.utils.DateUtils;
import consultan.vanke.com.utils.EventBusUtils;
import consultan.vanke.com.utils.TimeTools;
import consultan.vanke.com.utils.ToastUtils;
import consultan.vanke.com.widgets.SimpleToolbar;
import me.yokeyword.fragmentation.SupportFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static java.lang.System.currentTimeMillis;

public abstract class BaseFragment extends SupportFragment {
    protected View fragmentView;

    protected String simpleName;
    private BaseFragment mParentFragment;
    Unbinder unbinder;
    public SimpleToolbar mSimpleToolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simpleName = this.getClass().getSimpleName();
        EventBusUtils.register(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = initContentView(inflater, container, savedInstanceState);
        if (null == fragmentView) {
            throw new RuntimeException(getString(R.string.fragment_error));
        }
        unbinder = ButterKnife.bind(this, fragmentView);
        mParentFragment = (BaseFragment) getParentFragment();
        if (isNowInflagterView()) {
            initVariable();
            initViews();
            initBeforOnEnterAnimationEndData();
            if (BuildConfig.DEBUG) {
                showFragmentBgWater();
            }

        }
        return fragmentView;
    }

    public void initBeforOnEnterAnimationEndData() {

    }

    public boolean isNowInflagterView() {
        return true;
    }

    public void showFragmentBgWater() {
        Bitmap bitmap = Bitmap.createBitmap(480, 600, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        Paint paintData = new Paint();
        paint.setColor(Color.GRAY);
        paint.setAlpha(80);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(80);
        Path path = new Path();
        path.moveTo(30, 350);
        path.lineTo(600, 30);
        paintData.setColor(Color.GRAY);
        paintData.setAlpha(80);
        paintData.setAntiAlias(true);
        paintData.setTextAlign(Paint.Align.LEFT);
        paintData.setTextSize(80);
        Path pathData = new Path();
        pathData.moveTo(30, 420);
        pathData.lineTo(600, 100);
        canvas.drawTextOnPath(getActivity().getString(R.string.app_name), path, 0, 0, paint);
        canvas.drawTextOnPath(BuildConfig.BUILD_TYPE, pathData, 0, 0, paintData);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
        bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        bitmapDrawable.setDither(true);
        fragmentView.setBackgroundDrawable(bitmapDrawable);
    }

    protected abstract void initViews();

    protected void initVariable() {

    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        if (!isNowInflagterView()) {
            initVariable();
            initViews();
        }
        initDatas();
        initEvents();
    }


    protected abstract void initDatas();

    protected abstract void initEvents();


    protected abstract View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public boolean isRegisterEventBus() {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtils.unRegister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(MessageEvent e) {
        if (e != null) {
            handleEvent(e);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    protected void handleEvent(MessageEvent event) {

    }

    public void startBrotherFragment(SupportFragment isupportFragment) {
        ((BaseFragment) getParentFragment()).start(isupportFragment);
    }

    //设置状态栏颜色
    public void setStatusBarColors(int color, boolean isTopTextBlack) {
        StatusBarCompat.setStatusBarColor(_mActivity, color, isTopTextBlack);
    }

    public void showCommonDialog() {
        CommonDialog commonDialog = new CommonDialog(_mActivity, true);
        commonDialog.setTitle(getString(R.string.tips));
        commonDialog.setMessage(getString(R.string.is_exit));
        commonDialog.setYesOnclickListener(getString(R.string.sure), new CommonDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                ToastUtils.show(getString(R.string.sure));
                commonDialog.dismiss();
            }
        });
        commonDialog.setNoOnclickListener(getString(R.string.cancle), new CommonDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                ToastUtils.show(getString(R.string.cancle));
                commonDialog.dismiss();
            }
        });
        commonDialog.show();
    }
}
