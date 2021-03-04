package consultan.vanke.com.bean;

import java.io.Serializable;

public class NewLoginResultBean implements Serializable {

    /**
     * data : {"accessToken":"Bearer:eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbklkIjoiNTA3Njc3NjEyNzc1NjQ5MjgwIiwiaXNzIjoiaHR0cDovL3poaWtlb3BlbmRldi52YW5rZS5jb20iLCJleHAiOjE1NTI1MzM2NTgsImlhdCI6MTU1MjUzMTg1OH0.u7nU1DjjHFrjP8xv37MmVd13yesv0m_Pt9JFDtFC8cjpM1tVB6ontm4RwoaIJiBENFPPeewCxa_T1g4ftDwmLw","appletKeyInfo":{"unionid":"osZK00s2Vj3yt27rcl7003P-NSUA"},"consultantExtraData":{"active":"1","consultantId":"507677613115387904","forcedState":1,"type":"1"},"customerLoginExtraData":{"category":"0","custId":"506859874810863616"},"domain":"xsj-consultant-android","loginId":"507677612775649280","loginIdType":"2","mobile":"16600009982","realName":"测试专用交易","refreshToken":"Bearer:eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbklkIjoiNTA3Njc3NjEyNzc1NjQ5MjgwIiwiaXNzIjoiaHR0cDovL3poaWtlb3BlbmRldi52YW5rZS5jb20iLCJleHAiOjE1NTI1MzU0NTgsImlhdCI6MTU1MjUzMTg1OCwianRpIjoiNTA3Njc3NjEyNzc1NjQ5MjgwIn0.z-4QH5K6L9p156EW6XjUiiSRIPUpudxXiGoVPxD14b5kp61_Klsq1V1V8l-BZ2NxJ3Qc_qLen8jeh4GE1Xq8_w","roleType":"1;2;","tokenType":"Bearer","wxId":"518018412818604032"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * accessToken : Bearer:eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbklkIjoiNTA3Njc3NjEyNzc1NjQ5MjgwIiwiaXNzIjoiaHR0cDovL3poaWtlb3BlbmRldi52YW5rZS5jb20iLCJleHAiOjE1NTI1MzM2NTgsImlhdCI6MTU1MjUzMTg1OH0.u7nU1DjjHFrjP8xv37MmVd13yesv0m_Pt9JFDtFC8cjpM1tVB6ontm4RwoaIJiBENFPPeewCxa_T1g4ftDwmLw
         * appletKeyInfo : {"unionid":"osZK00s2Vj3yt27rcl7003P-NSUA"}
         * consultantExtraData : {"active":"1","consultantId":"507677613115387904","forcedState":1,"type":"1"}
         * customerLoginExtraData : {"category":"0","custId":"506859874810863616"}
         * domain : xsj-consultant-android
         * loginId : 507677612775649280
         * loginIdType : 2
         * mobile : 16600009982
         * realName : 测试专用交易
         * refreshToken : Bearer:eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbklkIjoiNTA3Njc3NjEyNzc1NjQ5MjgwIiwiaXNzIjoiaHR0cDovL3poaWtlb3BlbmRldi52YW5rZS5jb20iLCJleHAiOjE1NTI1MzU0NTgsImlhdCI6MTU1MjUzMTg1OCwianRpIjoiNTA3Njc3NjEyNzc1NjQ5MjgwIn0.z-4QH5K6L9p156EW6XjUiiSRIPUpudxXiGoVPxD14b5kp61_Klsq1V1V8l-BZ2NxJ3Qc_qLen8jeh4GE1Xq8_w
         * roleType : 1;2;
         * tokenType : Bearer
         * wxId : 518018412818604032
         */

        private String accessToken;
        private AppletKeyInfoBean appletKeyInfo;
        private ConsultantExtraDataBean consultantExtraData;
        private CustomerLoginExtraDataBean customerLoginExtraData;
        private String domain;
        private String loginId;
        private String loginIdType;
        private String mobile;
        private String realName;
        private String refreshToken;
        private String roleType;
        private String tokenType;
        private String wxId;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public AppletKeyInfoBean getAppletKeyInfo() {
            return appletKeyInfo;
        }

        public void setAppletKeyInfo(AppletKeyInfoBean appletKeyInfo) {
            this.appletKeyInfo = appletKeyInfo;
        }

        public ConsultantExtraDataBean getConsultantExtraData() {
            return consultantExtraData;
        }

        public void setConsultantExtraData(ConsultantExtraDataBean consultantExtraData) {
            this.consultantExtraData = consultantExtraData;
        }

        public CustomerLoginExtraDataBean getCustomerLoginExtraData() {
            return customerLoginExtraData;
        }

        public void setCustomerLoginExtraData(CustomerLoginExtraDataBean customerLoginExtraData) {
            this.customerLoginExtraData = customerLoginExtraData;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getLoginId() {
            return loginId;
        }

        public void setLoginId(String loginId) {
            this.loginId = loginId;
        }

        public String getLoginIdType() {
            return loginIdType;
        }

        public void setLoginIdType(String loginIdType) {
            this.loginIdType = loginIdType;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

        public String getRoleType() {
            return roleType;
        }

        public void setRoleType(String roleType) {
            this.roleType = roleType;
        }

        public String getTokenType() {
            return tokenType;
        }

        public void setTokenType(String tokenType) {
            this.tokenType = tokenType;
        }

        public String getWxId() {
            return wxId;
        }

        public void setWxId(String wxId) {
            this.wxId = wxId;
        }

        public static class AppletKeyInfoBean {
            /**
             * unionid : osZK00s2Vj3yt27rcl7003P-NSUA
             */

            private String unionid;

            public String getUnionid() {
                return unionid;
            }

            public void setUnionid(String unionid) {
                this.unionid = unionid;
            }
        }

        public static class ConsultantExtraDataBean {
            /**
             * active : 1
             * consultantId : 507677613115387904
             * forcedState : 1
             * type : 1
             */

            private String active;
            private String consultantId;
            private int forcedState;
            private String type;

            public String getActive() {
                return active;
            }

            public void setActive(String active) {
                this.active = active;
            }

            public String getConsultantId() {
                return consultantId;
            }

            public void setConsultantId(String consultantId) {
                this.consultantId = consultantId;
            }

            public int getForcedState() {
                return forcedState;
            }

            public void setForcedState(int forcedState) {
                this.forcedState = forcedState;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class CustomerLoginExtraDataBean {
            /**
             * category : 0
             * custId : 506859874810863616
             */

            private String category;
            private String custId;

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getCustId() {
                return custId;
            }

            public void setCustId(String custId) {
                this.custId = custId;
            }
        }
    }
}
