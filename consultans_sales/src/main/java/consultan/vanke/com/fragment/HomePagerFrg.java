package consultan.vanke.com.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

import butterknife.BindView;
import consultan.vanke.com.R;
import consultan.vanke.com.bean.HomeJumpBean;
import consultan.vanke.com.constant.ConstantLibrary;
import consultan.vanke.com.event.MessageEvent;
import consultan.vanke.com.fragment.kt.DivLinearLayoutFragment;
import consultan.vanke.com.utils.LazyOnClickListener;
import consultan.vanke.com.utils.ToastUtils;

public class HomePagerFrg extends BaseFragment {
    @BindView(R.id.btn_recycle)
    Button btnRecycle;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_changeUrl)
    Button btnChangeUrl;
    @BindView(R.id.btn_zxingCode)
    Button btnZxingCode;
    @BindView(R.id.btn_timepick)
    Button btnTimepick;
    @BindView(R.id.btn_dialog)
    Button btnDialog;
    @BindView(R.id.btn_div_linear)
    Button btnDivLinear;
    private View inflate;

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
    }

    @Override
    protected void initEvents() {
        LazyOnClickListener lazyOnClickListener = new LazyOnClickListener() {
            @Override
            public void onLazyClick(View v) {
                if (v == btnLogin) {
                    MessageEvent<HomeJumpBean> messageEvent = new MessageEvent<>();
                    messageEvent.setData(new HomeJumpBean(ConstantLibrary.LOGINFRAGMENT, ConstantLibrary.TYPE_TIME_OUT));
                    EventBus.getDefault().post(messageEvent);
//                    start(LoginFragment.getInstance("可以传递数据"));
                } else if (v == btnRecycle) {
                    MessageEvent<HomeJumpBean> messageEvent = new MessageEvent<>();
                    messageEvent.setData(new HomeJumpBean(ConstantLibrary.RECYCLEFRAGMENT, ConstantLibrary.TYPE_TIME_OUT));
                    EventBus.getDefault().post(messageEvent);
                } else if (v == btnChangeUrl) {
                    MessageEvent<HomeJumpBean> messageEvent = new MessageEvent<>();
                    messageEvent.setData(new HomeJumpBean(ConstantLibrary.CHANGEURLFRAGMENT, ConstantLibrary.TYPE_TIME_OUT));
                    EventBus.getDefault().post(messageEvent);
                } else if (v == btnZxingCode) {
                    MessageEvent<HomeJumpBean> messageEvent = new MessageEvent<>();
                    messageEvent.setData(new HomeJumpBean(ConstantLibrary.ZXINGCODEFRAGMENT, ConstantLibrary.TYPE_TIME_OUT));
                    EventBus.getDefault().post(messageEvent);
                } else if (v == btnTimepick) {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePicker = new DatePickerDialog(_mActivity, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                            // TODO Auto-generated method stub
                            ToastUtils.show(year + ConstantLibrary.SPLITE + (monthOfYear + 1) + ConstantLibrary.SPLITE + dayOfMonth);
                        }
                    }, year, month, day);
                    datePicker.show();
                } else if (v == btnDialog) {
                    showCommonDialog();
                } else if (v == btnDivLinear) {
                    ((MainFragment) getParentFragment()).start(new DivLinearLayoutFragment());
                }
            }
        };
        btnLogin.setOnClickListener(lazyOnClickListener);
        btnRecycle.setOnClickListener(lazyOnClickListener);
        btnChangeUrl.setOnClickListener(lazyOnClickListener);
        btnZxingCode.setOnClickListener(lazyOnClickListener);
        btnTimepick.setOnClickListener(lazyOnClickListener);
        btnDialog.setOnClickListener(lazyOnClickListener);
        btnDivLinear.setOnClickListener(lazyOnClickListener);
    }


    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflate = View.inflate(_mActivity, R.layout.home_fragment, null);
        return inflate;
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setStatusBarColors(getResources().getColor(R.color.white_color), true);
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        Log.e("onSupportVisible", "HomePagerFrg");
    }
}
