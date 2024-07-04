package se.miun.toya1800.dt031g.dialer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Map;

public class CallListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_list);

        // Get the data from the SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("Dial", MODE_PRIVATE);

        /*
        * Now we use the getAll() function that will return everything as
        * a Map<String, ?> and we can retrive the keys with keySet()
        */
        Map<String, ?> allEntries = sharedPreferences.getAll();

        // Get the TextView for the list as a variable and set everything to empty for now
        TextView numbersCalled = findViewById(R.id.numbers_called);
        numbersCalled.setText("");

        // The text in the middle saying this list is empty
        TextView callListView = findViewById(R.id.callList_view);

        // If there are numbers to show, do this below
        if(allEntries.size() != 0) {
            // Empty the text in the center which says there are not numbers to show
            callListView.setText("");

            // StringBuilder is used because it's easier to add new string together
            StringBuilder callList = new StringBuilder();

            // Now get all values and put them into a single list
            for(Map.Entry<String, ?> entry : allEntries.entrySet()) {
                Log.d("map values", entry.getKey() + ": " +
                        entry.getValue().toString());

                // Add to call list but end with \n so every number has its own row
                callList.append(entry.getKey()).append('\n');
            }
            // Add the list to the view
            numbersCalled.setText(callList);
        } else {
            callListView.setText(R.string.no_numbers_stored);
        }
    }
}