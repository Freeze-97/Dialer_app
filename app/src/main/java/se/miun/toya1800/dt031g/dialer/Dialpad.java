package se.miun.toya1800.dt031g.dialer;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Dialpad extends ConstraintLayout implements View.OnClickListener{

    public Dialpad(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Dialpad(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public Dialpad(Context context) {
        super(context);
        init(context, null);
    }

    public void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.dialpad, this);

        // Set click listeners to the dial buttons
        findViewById(R.id.dialpadButton0).setOnClickListener(this);
        findViewById(R.id.dialpadButton1).setOnClickListener(this);
        findViewById(R.id.dialpadButton2).setOnClickListener(this);
        findViewById(R.id.dialpadButton3).setOnClickListener(this);
        findViewById(R.id.dialpadButton4).setOnClickListener(this);
        findViewById(R.id.dialpadButton5).setOnClickListener(this);
        findViewById(R.id.dialpadButton6).setOnClickListener(this);
        findViewById(R.id.dialpadButton7).setOnClickListener(this);
        findViewById(R.id.dialpadButton8).setOnClickListener(this);
        findViewById(R.id.dialpadButton9).setOnClickListener(this);
        /*findViewById(R.id.dialpadButton_Star).setOnClickListener(this);
        findViewById(R.id.dialpadButton_Hashtag).setOnClickListener(this);*/

        TextView phone_number_text = findViewById(R.id.phoneNumber);
        phone_number_text.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }

    @Override
    public void onClick(View v) {
        if(v instanceof DialpadButton) {
            DialpadButton dialpadButton = (DialpadButton) v;
            TextView phoneNumber = findViewById(R.id.phoneNumber);
            String number = phoneNumber.getText().toString();
            phoneNumber.setText(number.concat(dialpadButton.getTitle()));
        }
    }
}