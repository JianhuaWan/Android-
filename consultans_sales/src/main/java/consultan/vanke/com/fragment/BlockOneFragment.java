package consultan.vanke.com.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.content.FileProvider;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import consultan.vanke.com.BuildConfig;
import consultan.vanke.com.R;
import consultan.vanke.com.fragment.kt.IzuiyouFragment;
import consultan.vanke.com.widgets.snackbar.Prompt;
import consultan.vanke.com.widgets.snackbar.TSnackbar;

public class BlockOneFragment extends BaseFragment {
    @BindView(R.id.tv_test_sql)
    Button tvTestSql;
    @BindView(R.id.btn_buttom)
    Button btnButtom;
    @BindView(R.id.btn_install)
    Button btnInstall;
    @BindView(R.id.btn_mockkt)
    Button btnMockkt;
    @BindView(R.id.btn_cardview)
    Button btnCardview;

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
        return View.inflate(_mActivity, R.layout.block_one_fragment, null);
    }

    public static BlockOneFragment getInstance() {
        BlockOneFragment blockOneFragment = new BlockOneFragment();
        return blockOneFragment;
    }


    @SuppressLint("ResourceAsColor")
    @OnClick({R.id.tv_test_sql, R.id.btn_buttom, R.id.btn_install, R.id.btn_mockkt, R.id.btn_cardview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_test_sql:
                ((MainFragment) getParentFragment().getParentFragment()).start(new RoomFragment());
                break;
            case R.id.btn_buttom:
                final ViewGroup viewGroup = (ViewGroup) tvTestSql.getRootView();//注意getRootView()最为重要，直接关系到TSnackBar的位置
                TSnackbar.make(viewGroup, _mActivity.getString(R.string.dialog), TSnackbar.LENGTH_LONG, TSnackbar.APPEAR_FROM_TOP_TO_DOWN).setPromptThemBackground(Prompt.SUCCESS).show();
                break;
            case R.id.btn_install:
                File file = new File(Environment.getExternalStoragePublicDirectory("XSJ/apk") + "/1.apk");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= 24) { //Android 7.0及以上
                    // 参数2 清单文件中provider节点里面的authorities ; 参数3  共享的文件,即apk包的file类
                    Uri apkUri = FileProvider.getUriForFile(_mActivity, BuildConfig.APPLICATION_ID + ".fileprovider", file);
                    //对目标应用临时授权该Uri所代表的文件
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                } else {
                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                }
                _mActivity.startActivity(intent);
                break;
            case R.id.btn_mockkt:
                //注意getParentFragment().getParentFragment()的用法,如果是处于顶层fragment则不需要多次getParentFragment()
                ((MainFragment) getParentFragment().getParentFragment()).start(new IzuiyouFragment());
                break;
            case R.id.btn_cardview:
//                startWithPopTo(LoginFragment.getInstance("可以传递数据"), ChartFragment.class, true);
                break;
        }
    }

}
