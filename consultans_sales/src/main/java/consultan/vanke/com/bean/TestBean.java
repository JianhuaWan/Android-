package consultan.vanke.com.bean;

import java.io.Serializable;
import java.util.List;

public class TestBean implements Serializable {


    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * productId : 127398712938719287391287
         * productName : 万科
         */

        private String productId;
        private String productName;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }
    }
}
