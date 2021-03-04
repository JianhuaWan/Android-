package consultan.vanke.com.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import consultan.vanke.com.R;
import consultan.vanke.com.activity.DumpActivity;
import consultan.vanke.com.constant.ConstantLibrary;
import consultan.vanke.com.utils.ToastUtils;

public class ChangeBaseUrlFragment extends BaseFragment {
    @BindView(R.id.et_url)
    EditText etUrl;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    private View view;

    @Override
    protected void initViews() {
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initEvents() {

    }


    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.change_url_fragment, null);
        return view;
    }


    @Override
    public boolean onBackPressedSupport() {
        startActivity(new Intent(_mActivity, DumpActivity.class));
        return super.onBackPressedSupport();
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        ConstantLibrary.HOST = ConstantLibrary.HTTSHEAD + etUrl.getText().toString();
        ToastUtils.show(R.string.success);
        onBackPressedSupport();
        pop();
    }
}
