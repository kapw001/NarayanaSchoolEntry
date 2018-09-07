package apps.pappaya.narayanaschoolentry.Common;

import android.content.Context;
import android.content.SharedPreferences;

public class GetSharedPreference {


    public void SETSharedPreference(Context context,String PrefString)
    {
        SharedPreferences pref = context.getSharedPreferences(PrefString, 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
    }



}
