package consultan.vanke.com.viewmodel;

import android.app.Application;
import consultan.vanke.com.bean.Izuiyou;
import io.reactivex.Maybe;

import java.util.List;

public class IzuiyouViewModel extends BaseViewModel {

    public IzuiyouViewModel(Application application) {
        super(application);
    }

    public Maybe<List<Izuiyou>> getListData() {
        return netApi.getIzuiyou();

    }

}