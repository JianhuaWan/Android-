package consultan.vanke.com.bean;

import java.io.Serializable;
import java.util.List;

public class Params implements Serializable {

    /**
     * ad_wakeup : 2
     * android_id : f659308b25eaba46
     * auto : 0
     * c_types : [1,3,2,8,7,9,11,21,20,14]
     * direction : down
     * filter : all
     * h_app : zuiyou
     * h_av : 4.8.8
     * h_ch : meizu
     * h_did : f659308b25eaba46
     * h_dt : 0
     * h_m : 169744524
     * h_model : Note9
     * h_nt : 1
     * h_os : 28
     * h_ts : 1559532163267
     * h_ua : Mozilla/5.0 (Linux; Android 9; Note9 Build/PKQ1.181203.001; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/70.0.3538.111 Mobile Safari/537.36
     * sdk_ver : {"tt":"1.9.6.4.2","tt_aid":"","tx":"4.19.574","tx_aid":""}
     * tab : 推荐
     * token : T4K0NMwSz-8zksCVOZw_223AJT2_fCtGs6IKo4_jwACzMVO5tznCdyuBPaM1l-VcLbh_b
     */

    private int ad_wakeup;
    private String android_id;
    private int auto;
    private String direction;
    private String filter;
    private String h_app;
    private String h_av;
    private String h_ch;
    private String h_did;
    private int h_dt;
    private int h_m;
    private String h_model;
    private int h_nt;
    private int h_os;
    private long h_ts;
    private String h_ua;
    private SdkVerBean sdk_ver;
    private String tab;
    private String token;
    private List<Integer> c_types;

    public int getAd_wakeup() {
        return ad_wakeup;
    }

    public void setAd_wakeup(int ad_wakeup) {
        this.ad_wakeup = ad_wakeup;
    }

    public String getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(String android_id) {
        this.android_id = android_id;
    }

    public int getAuto() {
        return auto;
    }

    public void setAuto(int auto) {
        this.auto = auto;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getH_app() {
        return h_app;
    }

    public void setH_app(String h_app) {
        this.h_app = h_app;
    }

    public String getH_av() {
        return h_av;
    }

    public void setH_av(String h_av) {
        this.h_av = h_av;
    }

    public String getH_ch() {
        return h_ch;
    }

    public void setH_ch(String h_ch) {
        this.h_ch = h_ch;
    }

    public String getH_did() {
        return h_did;
    }

    public void setH_did(String h_did) {
        this.h_did = h_did;
    }

    public int getH_dt() {
        return h_dt;
    }

    public void setH_dt(int h_dt) {
        this.h_dt = h_dt;
    }

    public int getH_m() {
        return h_m;
    }

    public void setH_m(int h_m) {
        this.h_m = h_m;
    }

    public String getH_model() {
        return h_model;
    }

    public void setH_model(String h_model) {
        this.h_model = h_model;
    }

    public int getH_nt() {
        return h_nt;
    }

    public void setH_nt(int h_nt) {
        this.h_nt = h_nt;
    }

    public int getH_os() {
        return h_os;
    }

    public void setH_os(int h_os) {
        this.h_os = h_os;
    }

    public long getH_ts() {
        return h_ts;
    }

    public void setH_ts(long h_ts) {
        this.h_ts = h_ts;
    }

    public String getH_ua() {
        return h_ua;
    }

    public void setH_ua(String h_ua) {
        this.h_ua = h_ua;
    }

    public SdkVerBean getSdk_ver() {
        return sdk_ver;
    }

    public void setSdk_ver(SdkVerBean sdk_ver) {
        this.sdk_ver = sdk_ver;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Integer> getC_types() {
        return c_types;
    }

    public void setC_types(List<Integer> c_types) {
        this.c_types = c_types;
    }

    public static class SdkVerBean {
        /**
         * tt : 1.9.6.4.2
         * tt_aid :
         * tx : 4.19.574
         * tx_aid :
         */

        private String tt;
        private String tt_aid;
        private String tx;
        private String tx_aid;

        public String getTt() {
            return tt;
        }

        public void setTt(String tt) {
            this.tt = tt;
        }

        public String getTt_aid() {
            return tt_aid;
        }

        public void setTt_aid(String tt_aid) {
            this.tt_aid = tt_aid;
        }

        public String getTx() {
            return tx;
        }

        public void setTx(String tx) {
            this.tx = tx;
        }

        public String getTx_aid() {
            return tx_aid;
        }

        public void setTx_aid(String tx_aid) {
            this.tx_aid = tx_aid;
        }
    }
}
