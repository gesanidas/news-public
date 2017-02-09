package com.example.gesanidas.news.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import com.example.gesanidas.news.R;



/**
 * Created by gesanidas on 12/17/2016.
 */

public class NewsPreferences
{
    public static String getPreferredSource(Context context)
    {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String keyforSource = context.getString(R.string.pref_source_key);
        String defaultSource = context.getString(R.string.pref_source_default);
        Log.i("tag",keyforSource);

        return prefs.getString(keyforSource, defaultSource);





    }
}
