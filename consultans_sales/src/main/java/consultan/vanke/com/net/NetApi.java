package consultan.vanke.com.net;

import consultan.vanke.com.bean.*;
import consultan.vanke.com.bean.kt.Izuiyou;
import io.reactivex.Maybe;
import retrofit2.http.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface NetApi {

    String LOCALHOST = "http://122.51.253.173:8081/";
    String SWICH_RUL = "api/getranddata";

    //登录
    @POST("auth/passwordLogin")
    Maybe<NewLoginResultBean.DataBean> salesLogin(@Body HashMap<String, String> logininfo);

    //get方式 userid={userid}
    @GET("XXX/xxx")
    Maybe<TestBean> getShareCode(@Query("userId") String userId, @Query("productId") String proId);

    //get方式 {/proCode}
    @GET("XXX/xxx/{proCode}")
    Maybe<ArrayList<TestBean>> getSys(@Path("proCode") String proCode);

    //DELETE方式  maybe里面都是返回结果
    @HTTP(method = "DELETE", path = "Xxxx/xxx", hasBody = true)
    Maybe<TestBean> cus(@Body HashMap<String, Object> params);

    @GET("api/getranddata")
    Maybe<List<consultan.vanke.com.bean.Izuiyou>> getIzuiyou();

    @GET("api/getranddata")
    Maybe<List<Izuiyou>> getIzuiyouKt();
}
