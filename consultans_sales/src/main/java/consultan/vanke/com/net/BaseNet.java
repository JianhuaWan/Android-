package consultan.vanke.com.net;

import consultan.vanke.com.base.BaseApplication;
import consultan.vanke.com.net.NetApi;

public class BaseNet {
    protected NetApi netApi;

    public BaseNet() {
        netApi = BaseApplication.getInstance().getNetApi();
    }
}
