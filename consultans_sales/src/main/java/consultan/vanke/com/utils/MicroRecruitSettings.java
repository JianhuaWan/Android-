package consultan.vanke.com.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MicroRecruitSettings extends AppSettings {

    private static final String SHARED_PREFERENCES_NAME = "consultans.settings";

    private final SharedPreferences mGlobalPreferences;

    public MicroRecruitSettings(Context context) {
        mGlobalPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public SharedPreferences getGlobalPreferences() {
        return mGlobalPreferences;
    }

    //是否第一次登录
    public BooleanPreference ISFIRSTLOGIN = new BooleanPreference("isfirst_login", true);


}