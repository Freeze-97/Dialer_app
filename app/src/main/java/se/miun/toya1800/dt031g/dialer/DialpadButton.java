package se.miun.toya1800.dt031g.dialer;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

public class DialpadButton extends ConstraintLayout implements View.OnTouchListener {
    // This is added because getTitle is required in assignment 4
    private String title;

    // Constructors
    public DialpadButton(@NonNull Context context) {
        super(context);
        init(context, null); // No attribute
    }

    public DialpadButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DialpadButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    // Set the button values here
    private void init(Context context, AttributeSet attributeSet) {
        TypedArray typedArray =
                context.getTheme().obtainStyledAttributes(attributeSet,
                        R.styleable.button, 0, 0);

        if(attributeSet != null) {
            inflate(context, R.layout.dialpad_button, this);

            try {
                // Get title and message
                title = typedArray.getString(R.styleable.button_title);
                String message = typedArray.getString(R.styleable.button_message);

                // Set title and message on the button
                setTitle(title);
                setMessage(message);

                // Button listeners
                setClickable(true);
                setOnTouchListener(this);
            } finally {
                // Not sure what this does but the android guide said that this is needed
                typedArray.recycle();
            }
        }
    }

    // Set the title of the button. One letter only
    public void setTitle(String title) {

        if(title.length() > 1) {
            this.title = title.substring(0,1); // Only get the first letter
        } else {
            this.title = title;
        }

        // Now set it on the TextView so it displays the title
        TextView titleView = findViewById(R.id.button_title);
        titleView.setText(this.title);
    }

    public void setMessage(String message) {
        String newMessage;

        if(message.length() > 4) {
            newMessage = message.substring(0, 4);
        } else {
            newMessage = message;
        }

        // Now set it on the TextView so it displays the message on the button
        TextView titleView = findViewById(R.id.button_message);
        titleView.setText(newMessage);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // If the button is pressed
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            // Get the button
            ConstraintLayout constraintLayout = findViewById(R.id.dial_button);

            // Load animation
            Animation ani = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);

            // Now start the animation
            constraintLayout.startAnimation(ani);
        }

        // If the button is released
        if(event.getAction() == MotionEvent.ACTION_UP) {
            // Get the button
            ConstraintLayout constraintLayout = findViewById(R.id.dial_button);

            // Load animation
            Animation ani = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);

            // Now start the animation
            constraintLayout.startAnimation(ani);

            // Play sound
            SoundPlayer.getInstance(getContext()).playSound(this);
        }

        // Could possibly return based on success, not sure
        return true;
    }

    public String getTitle() {
        return this.title;
    }
}
