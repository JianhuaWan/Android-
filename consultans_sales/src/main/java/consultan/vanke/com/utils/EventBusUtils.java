package consultan.vanke.com.utils;

import consultan.vanke.com.event.MessageEvent;
import org.greenrobot.eventbus.EventBus;

public class EventBusUtils {
    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    public static void unRegister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    public static void postEvent(MessageEvent event) {
        EventBus.getDefault().post(event);
    }
    //传统post需要注册后才能接受到数据,这里sticky可以先接受数据再进行注册
    public static void postSticky(MessageEvent event) {
        EventBus.getDefault().postSticky(event);
    }
}
