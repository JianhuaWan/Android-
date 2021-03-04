package consultan.vanke.com.net;

import com.buildbui.net.ApiStateException;

import consultan.vanke.com.constant.ConstantLibrary;
import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (response.code() == ConstantLibrary.ERR_CODE_TOKEN) {
            throw new ApiStateException(ConstantLibrary.ERR_CODE_TOKEN, "", "", null);
        } else if (response.code() == ConstantLibrary.ERR_CODE_RELOGIN) {
            throw new ApiStateException(ConstantLibrary.ERR_CODE_RELOGIN, "", "", null);
        }
        return response;
    }
}
