package consultan.vanke.com.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dianping.logan.Logan;
import com.dianping.logan.LoganConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.douyin.Douyin;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.wechat.friends.Wechat;
import consultan.vanke.com.R;
import consultan.vanke.com.base.BaseApplication;
import consultan.vanke.com.constant.ConstantLibrary;
import consultan.vanke.com.db.Entity.Customer;
import consultan.vanke.com.db.dao.CustomerDao;
import consultan.vanke.com.log.LoganParser;
import consultan.vanke.com.log.RealSendLogRunnable;
import consultan.vanke.com.utils.SdcardHelper;
import consultan.vanke.com.utils.ToastUtils;

public class RoomFragment extends BaseFragment implements ShareContentCustomizeCallback {
    CustomerDao customerDao;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.btn_del)
    Button btnDel;
    @BindView(R.id.btn_query)
    Button btnQuery;
    @BindView(R.id.btn_share)
    Button btnShare;
    OnekeyShare oks;
    @BindView(R.id.btn_logantotext)
    Button btnLogantotext;
    String DecodePath;

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        customerDao = BaseApplication.getInstance().getDb().customerDao();
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(_mActivity, R.layout.room_fragment, null);
    }

    @OnClick({R.id.btn_add, R.id.btn_update, R.id.btn_del, R.id.btn_query, R.id.btn_share, R.id.btn_logantotext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                Customer customer = new Customer();
                customer.setFirstName(ConstantLibrary.TESTROOM);
                customer.setLastName(ConstantLibrary.TESTROOM + (System.currentTimeMillis() / 1000));
                long count = customerDao.insert(customer);
                ToastUtils.show(getString(R.string.success) + count);
                break;
            case R.id.btn_update:
                Customer customerupdate = new Customer();
                customerupdate.setFirstName(ConstantLibrary.TESTROOM);
                customerupdate.setLastName(ConstantLibrary.TESTROOM + (System.currentTimeMillis() / 1000));
                long upatecount = customerDao.update(customerupdate);
                ToastUtils.show(getString(R.string.success) + upatecount);
                break;
            case R.id.btn_del:
//                startWithPop(LoginFragment.getInstance("可以传递数据"));
//                startWithPopTo(LoginFragment.getInstance("可以传递数据"), ChartFragment.class, true);
//                popTo(HomePagerFrg.class, true);
                int delcount = customerDao.deleteAllUser();
                ToastUtils.show(delcount + getString(R.string.del_data));
                break;
            case R.id.btn_query:
                List<Customer> customers = customerDao.getAll();
                ToastUtils.show(customers.size() + getString(R.string.query_data));
                break;
            case R.id.btn_share:
                oks = new OnekeyShare();
                // title标题，微信、QQ和QQ空间等平台使用
                oks.setTitle(getString(R.string.app_name));
                // titleUrl QQ和QQ空间跳转链接
                oks.setTitleUrl("http://sharesdk.cn");
                // text是分享文本，所有平台都需要这个字段
                oks.setText("this is content");
                // imagePath是图片的本地路径，确保SDcard下面存在此张图片
                //oks.setImagePath("/sdcard/test.jpg");
                oks.setImageUrl("http://tbfile.izuiyou.com/account/view/id/717365354/sz/src");
                // url在微信、Facebook等平台中使用
                oks.setUrl("http://www.baidu.com");
                oks.setCallback(callback);
                // 启动分享GUI
                oks.show(_mActivity);
                break;
            case R.id.btn_logantotext:
                SimpleDateFormat dataFormat = new SimpleDateFormat(ConstantLibrary.DATAFORMAT);
                String d = dataFormat.format(new Date(System.currentTimeMillis()));
                String[] temp = new String[1];
                temp[0] = d;
                RealSendLogRunnable realSendLogRunnable = new RealSendLogRunnable();
                Logan.s(temp, realSendLogRunnable);
                //===============================
                LoganConfig config = BaseApplication.getInstance().config;
                try {
                    Field priateField = LoganConfig.class.getDeclaredField(ConstantLibrary.LOGANPATH);
                    priateField.setAccessible(true);
                    String value = (String) priateField.get(config);
                    File c = new File(value);

                    File[] files = c.listFiles();
                    String name = null;
                    for (File file : files) {
                        try {
                            if (!file.getName().contains(ConstantLibrary.LOGANFILE_FILTTER) && !file.getName().contains(ConstantLibrary.LOGANFILE_FILTLOG)) {
                                name = file.getName();
                            }
                        } catch (NumberFormatException e) {
                            // ignore
                        }
                    }
                    File over = new File(value + File.separator + name);
                    InputStream inputStream = new FileInputStream(over);
                    DecodePath = Environment.getExternalStorageDirectory().toString()
                            + File.separator + SdcardHelper.PATH + File.separator + SdcardHelper.CrashLog + File.separator + ConstantLibrary.LOGANFILE;
                    OutputStream outputStream = new FileOutputStream(DecodePath);
                    new LoganParser(ConstantLibrary.LOGANPWD.getBytes(), ConstantLibrary.LOGANPWD.getBytes()).parse(inputStream, outputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    ToastUtils.show(R.string.success + DecodePath);
                }
                break;
        }
    }

    PlatformActionListener callback = new PlatformActionListener() {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            // TODO 分享成功后的操作或者提示
            ToastUtils.show(R.string.ssdk_oks_share_completed);
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            // TODO 失败，打印throwable为错误码
            ToastUtils.show(R.string.ssdk_oks_share_failed);
        }

        @Override
        public void onCancel(Platform platform, int i) {
            // TODO 分享取消操作
            ToastUtils.show(R.string.ssdk_oks_share_canceled);
        }
    };

    @Override
    public void onShare(Platform platform, Platform.ShareParams shareParams) {
        if (Wechat.NAME.equalsIgnoreCase(platform.getName())) {
            oks.setText("wechat only share content");
        } else if (Douyin.NAME.equalsIgnoreCase(platform.getName())) {
            oks.setText("douyin only share content");
        }
    }
}
