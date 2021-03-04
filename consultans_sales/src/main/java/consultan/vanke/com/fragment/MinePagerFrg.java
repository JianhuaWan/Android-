package consultan.vanke.com.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import consultan.vanke.com.R;
import consultan.vanke.com.base.BaseApplication;
import consultan.vanke.com.bean.HomeJumpBean;
import consultan.vanke.com.constant.ConstantLibrary;
import consultan.vanke.com.event.MessageEvent;
import consultan.vanke.com.utils.TimeTools;

public class MinePagerFrg extends BaseFragment {

    @BindView(R.id.btn_chart)
    Button btnChart;
    @BindView(R.id.btn_time)
    Button btnTime;
    @BindView(R.id.tv_countTime)
    TextView tvCountTime;

    private CountDownTimer mCountDownTimer;
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    private static final long MAX_TIME = 62 * 1000;
    private long curTime = 0;

    @Override
    protected void initVariable() {
        super.initVariable();
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void initDatas() {
        //设置字体
        tvCountTime.setTypeface(BaseApplication.getInstance().tf);
        initCountDownTimer(MAX_TIME);
        mCountDownTimer.start();
    }

    public void initCountDownTimer(long millisInFuture) {
        mCountDownTimer = new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                curTime = millisUntilFinished;
                tvCountTime.setText(TimeTools.getCountTimeByLong(millisUntilFinished));
            }

            public void onFinish() {
                tvCountTime.setText(ConstantLibrary.TIMESTAR);
            }
        };
    }

    @Override
    protected void initEvents() {

    }


    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = View.inflate(_mActivity, R.layout.mine_fragment, null);
        return rootView;
    }


    @OnClick({R.id.btn_chart, R.id.btn_time, R.id.btn_start, R.id.btn_cancel})
    public void onViewClicked(View view) {
        MessageEvent<HomeJumpBean> messageEvent;
        switch (view.getId()) {
            case R.id.btn_chart:
                /**
                 * 以上两种方式都可以跳转到下一级页面
                 */
//                messageEvent = new MessageEvent<>();
//                messageEvent.setData(new HomeJumpBean("ChartFragment", ConstantLibrary.TYPE_TIME_OUT));
//                EventBus.getDefault().post(messageEvent);
                startBrotherFragment(new ChartFragment());
                break;
            case R.id.btn_time:
                messageEvent = new MessageEvent<>();
                messageEvent.setData(new HomeJumpBean(ConstantLibrary.COUNTTIMEFRAGMENT, ConstantLibrary.TYPE_TIME_OUT));
                EventBus.getDefault().post(messageEvent);
                break;
            case R.id.btn_start:
                //开始
                mCountDownTimer.start();
                break;
            case R.id.btn_cancel:
                mCountDownTimer.cancel();
                break;
        }
    }

    //退出时释放资源
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer != null)
            mCountDownTimer.cancel();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        Log.e("onSupportVisible", "MinePagerFrg");
    }
}
