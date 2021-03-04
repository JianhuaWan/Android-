package consultan.vanke.com.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.buildbui.net.RxTransformer;
import com.commonsware.cwac.saferoom.SafeHelperFactory;
import com.dianping.logan.Logan;
import com.dianping.logan.LoganConfig;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.statistics.common.ULog;

import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import cn.jpush.android.api.JPushInterface;
import consultan.vanke.com.BuildConfig;
import consultan.vanke.com.R;
import consultan.vanke.com.constant.ConstantLibrary;
import consultan.vanke.com.db.AppDatabase;
import consultan.vanke.com.net.NetApi;
import consultan.vanke.com.net.NetHelper;
import consultan.vanke.com.utils.CrashHandler;
import consultan.vanke.com.utils.MicroRecruitSettings;
import consultan.vanke.com.utils.SdcardHelper;
import consultan.vanke.com.utils.ToastUtils;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.functions.Function;
import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class BaseApplication extends MultiDexApplication {
    private static BaseApplication instance;
    private NetApi netApi;
    private WeakReference<Activity> topActivity;
    private WeakReference<Activity> mainActivity;
    public MicroRecruitSettings settings;
    private AppDatabase db = null;
    public LoganConfig config;
    private static String fontPath = "fonts/NotoSansCJK-Medium.ttf";//pdcc.ttf适用于数字
    public Typeface tf;

    public static BaseApplication getInstance() {
        if (instance == null) {
            synchronized (BaseApplication.class) {
                if (instance == null) {
                    instance = new BaseApplication();
                }
            }
        }
        return instance;
    }

    public WeakReference<Activity> getTopActivity() {
        return topActivity;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        setTypeface();
        instance = this;
        initRoomDB();
        initJPush();
        initLogan();
        settings = new MicroRecruitSettings(this);
        CrashHandler.getInstance().init(this);
        initNetApi();
        initFragmentation();
        if (BuildConfig.DEBUG) {
            //debug模式开启日志打印功能
            UMConfigure.setLogEnabled(true);
        }
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                //先清空后赋值
                if (topActivity != null) {
                    topActivity.clear();
                }
                topActivity = new WeakReference<>(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (topActivity.get() != activity) {
                    topActivity = new WeakReference<>(activity);
                }
                if (topActivity.get().getClass().getSimpleName().equals(ConstantLibrary.MAINACTIVITY)) {
                    mainActivity = new WeakReference<>(activity);
                }
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                //当前的activity退出清空自己
                if (topActivity.get() == activity) {
                    topActivity.clear();
                }
                if (activity.getClass().toString().contains(ConstantLibrary.FLUTTERCONTAINERACTY) || activity.getClass().toString().contains(ConstantLibrary.FLUTTERACTY) || activity.getClass().toString().contains(ConstantLibrary.MAINACTIVITY)) {
                    //系统内存不足时候会被回收,get的值有可能为null
                    topActivity = mainActivity;
                }
            }
        });
        if (!SdcardHelper.creatFils())
            ToastUtils.show(R.string.not_find_card);
    }

    @Override
    protected void attachBaseContext(Context base) {
        MultiDex.install(base);
        super.attachBaseContext(base);
    }

    private void initLogan() {
        config = new LoganConfig.Builder()
                .setCachePath(Environment.getExternalStorageDirectory().toString())
                .setPath(Environment.getExternalStorageDirectory().toString()
                        + File.separator + SdcardHelper.PATH + File.separator + SdcardHelper.CrashLog)
                .setEncryptKey16(ConstantLibrary.LOGANPWD.getBytes())
                .setEncryptIV16(ConstantLibrary.LOGANPWD.getBytes())
                .build();
        Logan.init(config);
        if (BuildConfig.DEBUG) {
            Logan.setDebug(true);
        }
        Logan.w(getString(R.string.logan_init), 1);
    }

    private void initJPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    private void initRoomDB() {
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, ConstantLibrary.ROOMDBNAME)
                //release放开
                .openHelperFactory(new SafeHelperFactory(ConstantLibrary.ROOMPWD.toCharArray()))
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Logan.w(getString(R.string.room_init), 2);
                    }
                })
                //下面注释表示允许主线程进行数据库操作，但是不推荐这样做。
                //他可能造成主线程lock以及anr
                //所以我们的操作都是在新线程完成的
                .allowMainThreadQueries()
                //添加数据库变动迁移,如果是新建字段还好,如果新建表比较麻烦
//                .addMigrations(AppDatabase.MIGRATION_1_2)AppDatabase
                //清空数据表重建
                .fallbackToDestructiveMigration()
                .build();
    }

    private void initFragmentation() {
        AssetManager mgr = getAssets();
        //根据路径得到Typeface
        tf = Typeface.createFromAsset(mgr, "fonts/pdcc.ttf");
        Fragmentation.builder().debug(BuildConfig.DEBUG).stackViewMode(Fragmentation.BUBBLE)
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(@NonNull Exception e) {
                        // 以Bugtags为例子: 把捕获到的 Exception 传到 Bugtags 后台。
                        // Bugtags.sendException(e);
                    }
                })
                .install();
    }

    private void initNetApi() {
        final NetApi originNetApi = NetHelper.instance.create(NetApi.class);
        netApi = (NetApi) Proxy.newProxyInstance(originNetApi.getClass().getClassLoader(), originNetApi.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object o, final Method method, final Object[] objects) throws Throwable {
                return Maybe.just(true)
                        .flatMap(new Function<Boolean, MaybeSource<?>>() {
                            @Override
                            public MaybeSource<?> apply(Boolean aBoolean) throws Exception {
                                Maybe<?> invoke = (Maybe<?>) method.invoke(originNetApi, objects);
                                return invoke;
                            }
                        })
                        .compose(RxTransformer.io_main());
            }
        });
    }

    public NetApi getNetApi() {
        return netApi;
    }

    public AppDatabase getDb() {
        return db;
    }

    /**
     * 通过反射方法设置app全局字体
     */
    public void setTypeface() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath(fontPath).setFontAttrId(R.attr.fontPath).build());
    }


}
