package se.miun.toya1800.dt031g.dialer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import static se.miun.toya1800.dt031g.dialer.R.*;

public class DialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_dial);
    }

    protected void onDestroy() {
        super.onDestroy();
        SoundPlayer.getInstance(this).destroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dial_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.call_settings_activity:
                intentSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void intentSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);

        // Do not show headers
        intent.putExtra(":android:no_headers", true);

        // Show fragment part
        intent.putExtra(":android:show_fragment_title",
                SettingsActivity.MySettingsFragment.class.getName());

        // Now start the activity
        startActivity(intent);
    }
}