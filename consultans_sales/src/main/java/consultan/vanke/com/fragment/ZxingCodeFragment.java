package consultan.vanke.com.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.king.zxing.CaptureHelper;
import com.king.zxing.OnCaptureCallback;
import com.king.zxing.ViewfinderView;

import consultan.vanke.com.R;
import consultan.vanke.com.utils.ToastUtils;

public class ZxingCodeFragment extends BaseFragment implements OnCaptureCallback {
    private View view;

    private CaptureHelper mCaptureHelper;

    private SurfaceView surfaceView;

    private ViewfinderView viewfinderView;

    private View ivTorch;

    @Override
    protected void initViews() {
        initUI();
    }

    @Override
    protected void initDatas() {
        mSimpleToolbar.setMainTitle(getString(R.string.zxingcode));
    }

    @Override
    protected void initEvents() {
        mSimpleToolbar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });

        mSimpleToolbar.setRightTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setStatusBarColors(getResources().getColor(R.color.color_00BA91), false);
        view = inflater.inflate(R.layout.zxing_main, null);
        return view;
    }

    private void initUI() {
        mSimpleToolbar = view.findViewById(R.id.simple_toolbar);
        surfaceView = view.findViewById(R.id.surfaceView);
        viewfinderView = view.findViewById(R.id.viewfinderView);
        ivTorch = view.findViewById(R.id.ivFlash);
        ivTorch.setVisibility(View.INVISIBLE);


        mCaptureHelper = new CaptureHelper(this, surfaceView, viewfinderView, ivTorch);
        mCaptureHelper.setOnCaptureCallback(this);
        mCaptureHelper.onCreate();
        mCaptureHelper.vibrate(true)
                .fullScreenScan(true)//全屏扫码
                .supportVerticalCode(false)//支持扫垂直条码，建议有此需求时才使用。
                .continuousScan(false);

    }

    @Override
    public void onResume() {
        super.onResume();
        setStatusBarColors(getResources().getColor(R.color.color_00BA91), false);
        mCaptureHelper.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mCaptureHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCaptureHelper.onDestroy();
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        mCaptureHelper.onTouchEvent(event);
//        return super.onTouchEvent(event);
//    }

    @Override
    public boolean onResultCallback(String result) {
        ToastUtils.show(result);
        pop();
        return true;
    }

}
