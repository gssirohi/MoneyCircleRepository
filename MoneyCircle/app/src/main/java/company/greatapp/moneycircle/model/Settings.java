package company.greatapp.moneycircle.model;

import android.content.Context;

/**
 * Created by gyanendra.sirohi on 7/1/2015.
 */
public class Settings {
    Boolean dailyReminderEnable;
    int theme;
    Boolean lockScreenNotificationEnable;
    Boolean autoSettleUpEnable;
    String password;
    Boolean passwordEnable;
    public Settings(Context context) {
        //TODO: in the constructor settings must query from DB and initialize all the fields
    }
}
