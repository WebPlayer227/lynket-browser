package arun.com.chromer.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;

import arun.com.chromer.R;
import arun.com.chromer.chrometabutilites.CustomTabHelper;

/**
 * Created by Arun on 05/01/2016.
 */
public class Preferences {
    public static final String PREFERRED_PACKAGE = "preferred_package";
    public static final String TOOLBAR_COLOR = "toolbar_color";
    public static final String TOOLBAR_COLOR_PREF = "toolbar_color_pref";
    public static final String ANIMATION_TYPE = "animation_preference";
    public static final String FIRST_RUN = "firstrun";
    public static final String WARM_UP = "warm_up_preference";
    public static final String PRE_FETCH = "pre_fetch_preference";
    public static final String WIFI_PREFETCH = "wifi_preference";
    public static final String SECONDARY_PREF = "secondary_preference";
    public static final String FAV_SHARE_PREF = "fav_share_preference";
    public static final String DYNAMIC_COLOR = "dynamic_color";
    public static final String CLEAN_DATABASE = "clean_database";
    public static final String DYNAMIC_COLOR_APP = "dynamic_color_app";
    public static final String DYNAMIC_COLOR_WEB = "dynamic_color_web";
    public static final String PREFERRED_ACTION = "preferred_action_preference";

    private static SharedPreferences preferences(Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public static boolean isFirstRun(Context context) {
        if (preferences(context).getBoolean(FIRST_RUN, true)) {
            preferences(context).edit().putBoolean(FIRST_RUN, false).apply();
            return true;
        }
        return false;
    }

    public static boolean isColoredToolbar(Context context) {
        return PreferenceManager
                .getDefaultSharedPreferences(context)
                .getBoolean(TOOLBAR_COLOR_PREF, true);
    }

    public static int toolbarColor(Context context) {
        return PreferenceManager
                .getDefaultSharedPreferences(context)
                .getInt(TOOLBAR_COLOR,
                        ContextCompat.getColor(context, R.color.colorPrimary));
    }

    public static void toolbarColor(Context context, int selectedColor) {
        PreferenceManager
                .getDefaultSharedPreferences(context)
                .edit()
                .putInt(TOOLBAR_COLOR, selectedColor).apply();
    }

    public static boolean isAnimationEnabled(Context context) {
        return animationType(context) != 0;
    }

    public static int animationType(Context context) {
        return Integer.parseInt(PreferenceManager
                .getDefaultSharedPreferences(context)
                .getString(ANIMATION_TYPE, "1"));
    }

    public static int preferredAction(Context context) {
        return Integer.parseInt(PreferenceManager
                .getDefaultSharedPreferences(context)
                .getString(PREFERRED_ACTION, "1"));
    }

    public static String customTabApp(Context context) {
        String packageName = PreferenceManager
                .getDefaultSharedPreferences(context)
                .getString(PREFERRED_PACKAGE, null);

        if (packageName != null && Util.isPackageInstalled(context, packageName))
            return packageName;
        else {
            packageName = getDefaultCustomTabApp(context);
            // update the new custom tab package
            customTabApp(context, packageName);
        }
        return packageName;
    }

    private static String getDefaultCustomTabApp(Context context) {
        if (Util.isPackageInstalled(context, StringConstants.CHROME_PACKAGE) &&
                CustomTabHelper.isPackageSupportCustomTabs(context, StringConstants.CHROME_PACKAGE))
            return StringConstants.CHROME_PACKAGE;

        String[] supportingPackages = CustomTabHelper.getPackages();
        if (supportingPackages.length > 0) {
            return supportingPackages[0];
        } else
            return null;
    }

    public static void customTabApp(Context context, String string) {
        PreferenceManager
                .getDefaultSharedPreferences(context)
                .edit()
                .putString(PREFERRED_PACKAGE, string).apply();
    }

    public static String secondaryBrowserComponent(Context context) {
        return PreferenceManager
                .getDefaultSharedPreferences(context)
                .getString(SECONDARY_PREF, null);
    }

    public static void secondaryBrowserComponent(Context context, String string) {
        PreferenceManager
                .getDefaultSharedPreferences(context)
                .edit()
                .putString(SECONDARY_PREF, string).apply();
    }

    public static String secondaryBrowserPackage(Context context) {
        String flatString = secondaryBrowserComponent(context);
        if (flatString == null) {
            return null;
        }

        ComponentName cN = ComponentName.unflattenFromString(flatString);
        if (cN == null) return null;

        return cN.getPackageName();
    }


    public static String favShareComponent(Context context) {
        return PreferenceManager
                .getDefaultSharedPreferences(context)
                .getString(FAV_SHARE_PREF, null);
    }

    public static void favShareComponent(Context context, String string) {
        PreferenceManager
                .getDefaultSharedPreferences(context)
                .edit()
                .putString(FAV_SHARE_PREF, string).apply();
    }

    public static String favSharePackage(Context context) {
        String flatString = favShareComponent(context);
        if (flatString == null) {
            return null;
        }

        ComponentName cN = ComponentName.unflattenFromString(flatString);
        if (cN == null) return null;

        return cN.getPackageName();
    }

    public static boolean warmUp(Context context) {
        return preferences(context).getBoolean(WARM_UP, false);
    }

    public static void warmUp(Context context, boolean preference) {
        preferences(context).edit().putBoolean(WARM_UP, preference).commit();
    }

    public static boolean preFetch(Context context) {
        return preferences(context).getBoolean(PRE_FETCH, false);
    }

    public static void preFetch(Context context, boolean preference) {
        preferences(context).edit().putBoolean(PRE_FETCH, preference).commit();
    }

    public static boolean wifiOnlyPrefetch(Context context) {
        return preferences(context).getBoolean(WIFI_PREFETCH, false);
    }

    public static void wifiOnlyPrefetch(Context context, boolean preference) {
        preferences(context).edit().putBoolean(WIFI_PREFETCH, preference).commit();
    }

    public static boolean dynamicToolbar(Context context) {
        return PreferenceManager
                .getDefaultSharedPreferences(context)
                .getBoolean(DYNAMIC_COLOR, false);
    }

    public static void dynamicToolbar(Context context, boolean preference) {
        PreferenceManager
                .getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(DYNAMIC_COLOR, preference).apply();
    }

    public static boolean shouldCleanDB(Context context) {
        if (preferences(context).getBoolean(CLEAN_DATABASE, true)) {
            preferences(context).edit().putBoolean(CLEAN_DATABASE, false).apply();
            return true;
        }
        return false;
    }

    public static boolean dynamicToolbarOnApp(Context context) {
        return preferences(context).getBoolean(DYNAMIC_COLOR_APP, false);
    }

    public static void dynamicToolbarOnApp(Context context, boolean preference) {
        preferences(context).edit().putBoolean(DYNAMIC_COLOR_APP, preference).commit();
    }

    public static boolean dynamicToolbarOnWeb(Context context) {
        return preferences(context).getBoolean(DYNAMIC_COLOR_WEB, false);
    }

    public static void dynamicToolbarOnWeb(Context context, boolean preference) {
        preferences(context).edit().putBoolean(DYNAMIC_COLOR_WEB, preference).commit();
    }

    public static void dynamicToolbarOptions(Context context, boolean app, boolean web) {
        dynamicToolbarOnApp(context, app);
        dynamicToolbarOnWeb(context, web);
    }

    public static Integer[] dynamicToolbarSelections(Context context) {
        if (dynamicToolbarOnApp(context) && dynamicToolbarOnWeb(context))
            return new Integer[]{0, 1};
        else if (dynamicToolbarOnApp(context))
            return new Integer[]{0};
        else if (dynamicToolbarOnWeb(context))
            return new Integer[]{1};
        else return null;
    }

    public static void updateAppAndWeb(Context context, Integer[] which) {
        switch (which.length) {
            case 0:
                Preferences.dynamicToolbarOptions(context, false, false);
                break;
            case 1:
                if (which[0] == 0) {
                    Preferences.dynamicToolbarOptions(context, true, false);
                } else if (which[0] == 1) {
                    Preferences.dynamicToolbarOptions(context, false, true);
                }
                break;
            case 2:
                Preferences.dynamicToolbarOptions(context, true, true);
                break;
        }
    }

    public static CharSequence dynamicColorSummary(Context context) {
        if (dynamicToolbarOnApp(context) && dynamicToolbarOnWeb(context)) {
            return context.getString(R.string.dynamic_summary_appweb);
        } else if (dynamicToolbarOnApp(context)) {
            return context.getString(R.string.dynamic_summary_app);
        } else if (dynamicToolbarOnWeb(context)) {
            return context.getString(R.string.dynamic_summary_web);
        } else
            return context.getString(R.string.no_option_selected);
    }
}