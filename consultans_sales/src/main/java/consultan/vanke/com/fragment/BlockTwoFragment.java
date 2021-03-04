package consultan.vanke.com.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import consultan.vanke.com.R;
import consultan.vanke.com.constant.ConstantLibrary;
import consultan.vanke.com.fragment.kt.IzuiyouFragment;
import consultan.vanke.com.fragment.kt.MediaPlayFragment;
import consultan.vanke.com.utils.ACache;
import consultan.vanke.com.utils.ToastUtils;

public class BlockTwoFragment extends BaseFragment {

    @BindView(R.id.et_write)
    EditText etWrite;
    @BindView(R.id.bt_write)
    Button btWrite;
    @BindView(R.id.bt_cache)
    Button btCache;
    @BindView(R.id.bt_music)
    Button btMusic;

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
        return View.inflate(_mActivity, R.layout.block_two_fragment, null);
    }

    public static BlockTwoFragment getInstance() {
        BlockTwoFragment blockTwoFragment = new BlockTwoFragment();
        return blockTwoFragment;
    }


    @OnClick({R.id.bt_write, R.id.bt_cache, R.id.bt_music})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_write:
                ACache.get(_mActivity).put(ConstantLibrary.USERNAME_KEY, etWrite.getText().toString(), 5);
                ToastUtils.show(getString(R.string.success));
                break;
            case R.id.bt_cache:
                String userNameCache = ACache.get(_mActivity).getAsString(ConstantLibrary.USERNAME_KEY);
                ToastUtils.show(ConstantLibrary.VALUE + userNameCache);
                break;
            case R.id.bt_music:
                ((MainFragment) getParentFragment().getParentFragment()).start(new MediaPlayFragment());
                break;
        }
    }
}
