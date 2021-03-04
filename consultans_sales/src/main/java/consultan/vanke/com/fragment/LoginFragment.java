package consultan.vanke.com.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.buildbui.net.ApiStateException;
import com.buildbui.net.NetMaybeObservable;
import com.mob.secverify.OperationCallback;
import com.mob.secverify.SecVerify;
import com.mob.secverify.VerifyCallback;
import com.mob.secverify.datatype.LoginResult;
import com.mob.secverify.datatype.VerifyResult;
import com.mob.secverify.exception.VerifyException;

import butterknife.BindView;
import butterknife.OnClick;
import consultan.vanke.com.R;
import consultan.vanke.com.activity.DumpActivity;
import consultan.vanke.com.bean.NewLoginResultBean;
import consultan.vanke.com.utils.ToastUtils;
import consultan.vanke.com.viewmodel.LoginViewModel;
import io.reactivex.MaybeObserver;

public class LoginFragment extends BaseFragment {
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_fast_login)
    Button btnFastLogin;
    private View view;
    private LoginViewModel loginViewModel;

    @Override
    protected void initViews() {
        loginViewModel = ViewModelProviders.of(_mActivity).get(LoginViewModel.class);
    }

    @Override
    protected void initDatas() {
        //提前调用,可以加快免登录过程,提高用户体验
        SecVerify.preVerify(new OperationCallback() {
            @Override
            public void onComplete(Object o) {
                //TODO 处理成功的结果
            }

            @Override
            public void onFailure(VerifyException e) {
                //TODO 处理失败的结果
            }
        });
    }

    @Override
    protected void initEvents() {

    }


    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setStatusBarColors(getResources().getColor(R.color.color_00BA91), false);
        view = inflater.inflate(R.layout.login_fragment, null);
        return view;
    }

    public static LoginFragment getInstance(String type) {
        LoginFragment loginFragment = new LoginFragment();
        Bundle bundle = new Bundle();
        bundle.putString("xx", type);
        loginFragment.setArguments(bundle);
        return loginFragment;
    }

    /**
     * 这里获取返回值,如果返回值需要转换或者数据封装,可以直接在viewModel中执行,view里面只需要做界面相关操作.
     */
    private MaybeObserver<NewLoginResultBean.DataBean> observer =
            new NetMaybeObservable<NewLoginResultBean.DataBean>() {
                @Override
                public void onSuccess(NewLoginResultBean.DataBean dataBean) {
                    ToastUtils.show(dataBean.getAccessToken());
                    pop();
                }

                @Override
                public void onError(Throwable e) {
                    if (e instanceof ApiStateException) {
                        ApiStateException apiStateException = (ApiStateException) e;
                        ToastUtils.show(apiStateException.getMsg());
                    } else {
                        ToastUtils.show(e.getMessage());
                    }
                }
            };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        setStatusBarColors(getResources().getColor(R.color.color_00BA91), false);
    }

    @OnClick({R.id.btn_login, R.id.btn_fast_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                loginViewModel.login(etName.getText().toString(), etPwd.getText().toString()).subscribe(observer);
                break;
            case R.id.btn_fast_login:
                SecVerify.verify(new VerifyCallback() {
                    @Override
                    public void onOtherLogin() {
                        // 用户点击“其他登录方式”，处理自己的逻辑
                    }

                    @Override
                    public void onUserCanceled() {
                        // 用户点击“关闭按钮”或“物理返回键”取消登录，处理自己的逻辑
                    }

                    @Override
                    public void onComplete(VerifyResult data) {
                        // 获取授权码成功，将token信息传给应用服务端，再由应用服务端进行登录验证，此功能需由开发者自行实现
                        ToastUtils.show(data.getToken());
                    }

                    @Override
                    public void onFailure(VerifyException e) {
                        //TODO处理失败的结果
                        ToastUtils.show(e.getMessage());
                    }
                });
                break;
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        startActivity(new Intent(_mActivity, DumpActivity.class));
        return super.onBackPressedSupport();
    }
}
