package consultan.vanke.com.net;

import com.google.gson.Gson;

import consultan.vanke.com.base.BaseApplication;
import consultan.vanke.com.constant.ConstantLibrary;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.UUID;

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder()
                .addHeader("uuid", UUID.randomUUID().toString())
                .addHeader("token", "xxxx")
                .addHeader("UserAccessInfo", new Gson().toJson(new UserAccessInfo(UUID.randomUUID().toString(), ConstantLibrary.DOMAIN)));
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }

    private class UserAccessInfo {
        private String traceId;
        private String domain;

        public UserAccessInfo(String traceId, String domain) {
            this.traceId = traceId;
            this.domain = domain;
        }
    }
}
