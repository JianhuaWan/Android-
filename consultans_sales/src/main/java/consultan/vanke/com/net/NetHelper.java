package consultan.vanke.com.net;

import com.buildbui.net.PagerJsonSerializer;
import com.buildbui.net.PagerReqMix;
import com.buildbui.net.converter.GsonConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhy.http.okhttp.https.HttpsUtils;

import consultan.vanke.com.BuildConfig;
import consultan.vanke.com.constant.ConstantLibrary;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class NetHelper {
    private Retrofit retrofit;
    public static NetHelper instance = new NetHelper();

    private NetHelper() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        OkHttpClient.Builder builder;
        //设置拦截消息体
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder = new OkHttpClient.Builder().addInterceptor(new HeaderInterceptor())
                .addInterceptor(new BaseUrlInterceptor())
                .addInterceptor(loggingInterceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);
        //防中间人攻击
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory
                (new InputStream[]{new Buffer().writeUtf8(BaseCer.CER).inputStream()}, null, null);
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        retrofit = new Retrofit.Builder().baseUrl(ConstantLibrary.HOST)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .client(builder.build())
                .build();

    }

    public Gson getGson() {
        return new GsonBuilder().registerTypeAdapter(PagerReqMix.class, new PagerJsonSerializer())
                .create();
    }

    public <T> T create(final Class<T> service) {
        return retrofit.create(service);
    }
}
