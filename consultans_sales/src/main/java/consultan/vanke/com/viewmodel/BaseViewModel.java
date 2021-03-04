package consultan.vanke.com.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import consultan.vanke.com.base.BaseApplication;
import consultan.vanke.com.net.NetApi;
import io.reactivex.annotations.NonNull;

public class BaseViewModel extends AndroidViewModel {
    protected BaseApplication app;
    protected NetApi netApi;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        this.app = getApplication();
        this.netApi = app.getNetApi();
    }
}
