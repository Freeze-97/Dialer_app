package se.miun.toya1800.dt031g.dialer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    /* If the user clicks the about button this will
    be true and it will not be possible to do it again
     during the current activity */
    private boolean clickedAbout = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Set layout for main activity

        // Start copying to internal storage
        if(!Util.defaultVoiceExist(this)) {
           boolean worked = Util.copyDefaultVoiceToInternalStorage(this);

           System.out.println(worked);
        }
    }

    public void showDial(View view) {
        Intent intent = new Intent(this, DialActivity.class);
        startActivity(intent);
    }

    public void showCallList(View view) {
        Intent intent = new Intent(this, CallListActivity.class);
        startActivity(intent);
    }

    public void showSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void showMaps(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void showAbout(View view) {
        if(!clickedAbout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.about_info)
                    .setCancelable(false)
                    .setTitle(R.string.about)
                    .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.show();

            // The button has now been pressed
            clickedAbout = true;
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    R.string.about_toast, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("clicked", clickedAbout);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        clickedAbout = savedInstanceState.getBoolean("clicked");
    }
}