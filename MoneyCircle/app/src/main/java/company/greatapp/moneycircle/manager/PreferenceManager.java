package company.greatapp.moneycircle.manager;

import android.content.Context;
import android.content.SharedPreferences;

import company.greatapp.moneycircle.constants.C;

/**
 * Created by Gyanendrasingh on 04-07-2015.
 */
public class PreferenceManager {
    SharedPreferences prefs;
    public PreferenceManager(Context context) {
        prefs = context.getSharedPreferences(C.MONEY_CIRCLE_PREFERENCES,Context.MODE_PRIVATE);
    }

    public SharedPreferences.Editor getEditor() {
        return prefs.edit();
    }

    public boolean isDeviceContactsRetrived() {
        return prefs.getBoolean(C.PREF_CONTACTS_RETRIVED, false);
    }

    public boolean isDefaultCategoriesLoadedInDB() {
        return prefs.getBoolean(C.PREF_DEFAULT_CATEGORIES_LOADED, false);
    }

    public boolean isRegistrationProcessCompleted() {
        return prefs.getBoolean(C.PREF_REGISTRATION_PROCESS_COMPLETED, false);
    }
}
