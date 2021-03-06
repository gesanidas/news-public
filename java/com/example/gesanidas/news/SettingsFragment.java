package com.example.gesanidas.news;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

import com.example.gesanidas.news.sync.NewsSyncUtils;


/**
 * Created by gesanidas on 12/17/2016.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener
{


    private void setPreferenceSummary(Preference preference, Object value)
    {
        String stringValue = value.toString();

        if (preference instanceof ListPreference)
        {

            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0)
            {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }
        else
        {
            preference.setSummary(stringValue);
        }
    }



    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey)
    {
        addPreferencesFromResource(R.xml.pref_general);


        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen prefScreen = getPreferenceScreen();
        int count = prefScreen.getPreferenceCount();
        for (int i = 0; i < count; i++)
        {
            Preference p = prefScreen.getPreference(i);
            String value = sharedPreferences.getString(p.getKey(), "");
            setPreferenceSummary(p, value);
        }



    }





    @Override
    public void onStop()
    {
        super.onStop();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        Activity activity=getActivity();
        Preference preference = findPreference(key);

        if (key.equals(getString(R.string.pref_source_key)))
        {
            NewsSyncUtils.startImmediateSync(activity);
        }

        if (null != preference)
        {

                setPreferenceSummary(preference, sharedPreferences.getString(key, ""));

        }


    }
}
