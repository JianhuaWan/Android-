package consultan.vanke.com.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.umeng.analytics.MobclickAgent;
import consultan.vanke.com.R;
import consultan.vanke.com.base.BaseApplication;
import consultan.vanke.com.fragment.GuideFragment;

public class GuideActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_guide);
        if (BaseApplication.getInstance().settings.ISFIRSTLOGIN.getValue()) {
            if (findFragment(GuideFragment.class) == null) {
                loadRootFragment(R.id.fl_guide, new GuideFragment());
            }
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.finish();
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
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
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

}
