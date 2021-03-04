package consultan.vanke.com.bean;

import java.io.Serializable;

public class HomeJumpBean implements Serializable {
    public String tag;
    public int deliverType;

    public HomeJumpBean(String tag, int deliverType) {
        this.tag = tag;
        this.deliverType = deliverType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(int deliverType) {
        this.deliverType = deliverType;
    }
}
