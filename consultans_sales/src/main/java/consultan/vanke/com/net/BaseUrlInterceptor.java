package consultan.vanke.com.net;

import com.buildbui.net.converter.HttpResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import consultan.vanke.com.BuildConfig;
import consultan.vanke.com.bean.Izuiyou;
import consultan.vanke.com.constant.ConstantLibrary;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

/**
 * 当接口中域名不一致时候可以做拦截url
 */
public class BaseUrlInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        HttpUrl baseUrl;
        if (request.url().toString().contains(NetApi.SWICH_RUL)) {
            baseUrl = HttpUrl.parse(NetApi.LOCALHOST);
            HttpUrl oldUrl = request.url();
            String replace = oldUrl.url().toString().replace(BuildConfig.HOST, baseUrl.url().toString());
            request = new Request.Builder().url(replace).build();
            Response response = chain.proceed(request);
            List<Izuiyou> izuiyous = new Gson().fromJson(response.body().string(),
                    new TypeToken<List<Izuiyou>>() {
                    }.getType());
            HttpResult<List<Izuiyou>> listHttpResult = new HttpResult<>();
            listHttpResult.setEntity(izuiyous);
            return response.newBuilder()
                    .body(ResponseBody.create(MediaType.parse("application/x-www-form-urlencoded"),
                            new Gson().toJson(listHttpResult)))
                    .build();
        } else {
            baseUrl = HttpUrl.parse(ConstantLibrary.HOST);
            HttpUrl oldUrl = request.url();
            String replace = oldUrl.url().toString().replace(BuildConfig.HOST, baseUrl.url().toString());
            HttpUrl build = oldUrl.newBuilder(replace).build();
            return chain.proceed(builder.url(build).build());
        }
    }
}
