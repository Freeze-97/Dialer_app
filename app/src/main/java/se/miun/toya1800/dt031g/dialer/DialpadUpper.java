package se.miun.toya1800.dt031g.dialer;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/* This class is a custom view which represents the upper part of the dialpad
   where the number is shown and the user can press call or delete/erase
*/


public class DialpadUpper extends ConstraintLayout implements View.OnClickListener,
        View.OnLongClickListener{

    public DialpadUpper(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public DialpadUpper(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DialpadUpper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.dialpad_upper, this);

        // Set listeners to the erase button and dial button
        // Erase will have two, another one for longer clicks
        findViewById(R.id.erase_button).setOnClickListener(this);
        findViewById(R.id.erase_button).setOnLongClickListener(this);
        findViewById(R.id.upper_dial_button).setOnClickListener(this);

        TextView phone_number_text = findViewById(R.id.phoneNumber);

        // Put the numbers to be shown
        if(attrs != null) {
            TypedArray typedArray = context.getTheme()
                    .obtainStyledAttributes(attrs, R.styleable.button, 0, 0);
            try {
                // If it's not empty/null then set a number
                if (typedArray.getString(R.styleable.button_phoneNumber) != null) {
                    phone_number_text.setText(typedArray.getString(R.styleable.button_phoneNumber));
                }
            } finally {
                // When you are done with typedArray
                typedArray.recycle();
            }
        }
    }

    @Override
    public void onClick(View v) {
        // The small erase/delete button
        if(v.getId() == R.id.erase_button) {
            // Get the view with the number
            TextView phone_num = findViewById(R.id.phoneNumber);

            String new_num;
            // Subtract the last digit
            if(phone_num.getText().toString().length() > 0) {
                new_num = phone_num.getText().toString()
                        .substring(0, phone_num.getText().toString().length() - 1);

                phone_num.setText(new_num);
            }
        }

        // The small call button
        if(v.getId() == R.id.upper_dial_button) {
            TextView phone_num = findViewById(R.id.phoneNumber);
            Uri uri = Uri.parse("tel:" + Uri.encode(phone_num.getText().toString()));
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(uri);
            getContext().startActivity(intent);

            // This part will save the number via sharedPreferences
            saveCall(phone_num.getText().toString());
        }
    }

    private void saveCall(String phoneNum) {
        if(new SettingsActivity
                .MySettingsFragment(this.getContext())
                .getValueAsBoolean("phoneCalls")) {
            // Use SharedPreferences and name the key
            SharedPreferences sharedPreferences =
                    this.getContext().getSharedPreferences("Dial", Context.MODE_PRIVATE);

            // Use editor to add the data
            SharedPreferences.Editor preferencesEditor =
                    sharedPreferences.edit();

            // Now save the data and juste use the same name for key and the actual value
            preferencesEditor.putString(phoneNum, phoneNum);
            preferencesEditor.apply();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if(v.getId() == R.id.erase_button) {
            TextView phone_num = findViewById(R.id.phoneNumber);
            phone_num.setText("");
        }
        return true;
    }
}