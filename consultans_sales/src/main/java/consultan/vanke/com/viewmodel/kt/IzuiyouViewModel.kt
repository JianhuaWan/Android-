package consultan.vanke.com.viewmodel.kt

import android.app.Application
import consultan.vanke.com.bean.kt.Izuiyou
import consultan.vanke.com.viewmodel.BaseViewModel
import io.reactivex.Maybe

class IzuiyouViewModel(application: Application?) : BaseViewModel(application!!) {
    val listData: Maybe<List<Izuiyou>>
        get() = netApi.izuiyouKt
}