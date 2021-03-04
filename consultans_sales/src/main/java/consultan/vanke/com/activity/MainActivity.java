package consultan.vanke.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import consultan.vanke.com.R;
import consultan.vanke.com.constant.ConstantLibrary;
import consultan.vanke.com.event.MessageResult;
import consultan.vanke.com.fragment.MainFragment;
import consultan.vanke.com.utils.CommonUtil;
import consultan.vanke.com.utils.NetUtils;
import consultan.vanke.com.utils.ToastUtils;
import consultan.vanke.com.widgets.snackbar.Prompt;
import consultan.vanke.com.widgets.snackbar.TSnackbar;
import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends BaseActivity {
    private View view;
    private boolean isfristLaunch = true;
    private Long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] permission = ConstantLibrary.BASEPREM;
        if (EasyPermissions.hasPermissions(this, permission)) {
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.permission_tips), ConstantLibrary.RC_BASEPREM, ConstantLibrary.BASEPREM);
        }
    }

    @Override
    protected void initContentView(Bundle bundle) {
        view = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        setContentView(view);
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.main_content_layout, new MainFragment());
        }
    }

    @Override
    protected void initElements() {
    }

    @Override
    protected void initData() {
    }


    @Override
    protected void initEvent() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        mExitTime = System.currentTimeMillis();
        MobclickAgent.onResume(this);
        if (!CommonUtil.isNetworkAvailable(this)) {
            TSnackbar.make(view.getRootView(), getString(R.string.msg_network_is_not_available), TSnackbar.LENGTH_LONG, TSnackbar.APPEAR_FROM_TOP_TO_DOWN).setPromptThemBackground(Prompt.ERROR).show();
            return;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ConstantLibrary.IMAGESELECTOR_CROP || requestCode == ConstantLibrary.IMAGESELECTOR_ONLYTAKE || requestCode == ConstantLibrary.IMAGESELECTOR_FILE || requestCode == ConstantLibrary.IMAGESELECTOR_FILE_ONE) {
            if (data != null) {
                MessageResult messageResult = new MessageResult(requestCode, resultCode, data);
                EventBus.getDefault().post(messageResult);
            }
        }
    }

    @Override
    public void onNetChange(int netWorkState) {
        super.onNetChange(netWorkState);
        if (netWorkState == NetUtils.NETWORK_NONE) {
            isfristLaunch = false;
            TSnackbar.make(view.getRootView(), getString(R.string.msg_network_is_not_available), TSnackbar.LENGTH_LONG, TSnackbar.APPEAR_FROM_TOP_TO_DOWN).setPromptThemBackground(Prompt.ERROR).show();
        } else {
            if (!isfristLaunch) {
                TSnackbar.make(view.getRootView(), getString(R.string.msg_network_is_available), TSnackbar.LENGTH_LONG, TSnackbar.APPEAR_FROM_TOP_TO_DOWN).setPromptThemBackground(Prompt.SUCCESS).show();
            } else {
                isfristLaunch = false;
            }
        }
    }

    public void onKeyDown() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            mExitTime = System.currentTimeMillis();
            ToastUtils.show(R.string.exit_app);
        } else {
            finish();
        }
    }
}
