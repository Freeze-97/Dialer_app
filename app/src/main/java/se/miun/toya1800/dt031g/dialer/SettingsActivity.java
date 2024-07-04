package se.miun.toya1800.dt031g.dialer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity implements
        PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content,
                        new MySettingsFragment())
                .commit();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onPreferenceStartFragment(PreferenceFragmentCompat caller, Preference pref) {
        // Show the new fragment
        Fragment fragment = getSupportFragmentManager()
                .getFragmentFactory()
                .instantiate(getClassLoader(),
                        pref.getFragment());

        fragment.setArguments(pref.getExtras());
        fragment.setTargetFragment(caller, 0);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, fragment) // replace fragment
                .addToBackStack(null)
                .commit();
        setTitle(pref.getTitle());
        return true;
    }

    // This is used so you can navigate back inside the app
    @Override
    public boolean onSupportNavigateUp() {
        if (getSupportFragmentManager().popBackStackImmediate()) {
            return true;
        }
        return super.onSupportNavigateUp();
    }

    public static class MySettingsFragment extends PreferenceFragmentCompat {
        private Context context;

        // Constructor
        public MySettingsFragment() {}

        // Constructor
        public MySettingsFragment(Context context) {
            this.context = context;
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }

        public Boolean getValueAsBoolean(String key) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getBoolean(key, false);
        }
    }
}