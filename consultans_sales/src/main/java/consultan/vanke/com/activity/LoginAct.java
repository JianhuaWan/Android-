package consultan.vanke.com.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.umeng.analytics.MobclickAgent;

public class LoginAct extends BaseActivity {

    @Override
    protected void initContentView(Bundle bundle) {

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
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
