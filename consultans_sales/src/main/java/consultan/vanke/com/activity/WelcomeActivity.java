package consultan.vanke.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.umeng.analytics.MobclickAgent;

import consultan.vanke.com.R;

public class WelcomeActivity extends BaseActivity {
    private ImageView imageView;


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.welcome_main);
    }

    @Override
    protected void initElements() {
        setStatusBarColors(getResources().getColor(R.color.white_color));
        imageView = findViewById(R.id.iv_img);

    }

    @Override
    protected void initData() {
        imageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, GuideActivity.class);
                startActivity(intent);
                finish();
            }
        }, 500);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
