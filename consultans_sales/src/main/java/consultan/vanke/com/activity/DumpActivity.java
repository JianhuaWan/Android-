package consultan.vanke.com.activity;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.umeng.analytics.MobclickAgent;

public class DumpActivity extends AppCompatActivity {
    /**
     * 输入法引起的内存泄漏处理方案
     * debug中会有icon切换
     */


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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();

            }
        }, 500);
    }
}
