package info.tatarintsev.firstlesson.ui;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import info.tatarintsev.firstlesson.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}