package consultan.vanke.com.viewmodel;

import android.app.Application;
import android.util.Base64;

import consultan.vanke.com.R;
import consultan.vanke.com.bean.NewLoginResultBean;
import consultan.vanke.com.constant.ConstantLibrary;
import consultan.vanke.com.utils.ToastUtils;
import io.reactivex.Maybe;

import java.util.HashMap;

public class LoginViewModel extends BaseViewModel {
    public LoginViewModel(Application application) {
        super(application);
    }


    public Maybe<NewLoginResultBean.DataBean> login(String name, String pwd) {
        if (name.equals("") || pwd.equals("")) {
            NewLoginResultBean.DataBean bean = new NewLoginResultBean.DataBean();
            return Maybe.error(new LoginException());
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile", name);
        params.put("password", Base64.encodeToString(pwd.getBytes(), Base64.NO_WRAP));
        params.put("authType", "REG");
        return netApi.salesLogin(params);

    }

    static class LoginException extends RuntimeException {
        public LoginException() {
            super(ConstantLibrary.LOGINERROR);
        }
    }

}
