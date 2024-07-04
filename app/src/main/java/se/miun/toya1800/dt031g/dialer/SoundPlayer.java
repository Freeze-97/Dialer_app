package se.miun.toya1800.dt031g.dialer;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.util.HashMap;
import java.util.Map;

public class SoundPlayer {
    // Sound stream
    private SoundPool soundPool;

    // The sound player, I had to make it static - don't know why
    private static SoundPlayer soundPlayer = null;

    protected int sound_pound, sound_star, sound_zero, sound_one, sound_two, sound_three,
            sound_four, sound_five, sound_six, sound_seven, sound_eight, sound_nine;

    private final Context context;

    // Map all sound names with the actual path
    private final Map<String, Integer> PATH_TO_VOICES = new HashMap<>();

    // Constructor
    private SoundPlayer(Context context) {
        this.context = context;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(12)
                    .setAudioAttributes(new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build())
            .build();
        } else {
            soundPool = new SoundPool(12, AudioManager.STREAM_MUSIC, 0); // 12 because there are 12 buttons
        }

        // Load all sounds from the external storage to the soundPool
        loadSoundsFromExternal();
    }

    public void loadSoundsFromExternal() {
        if(Util.getDirForDefaultVoice(context).exists()) {
            sound_zero = soundPool.load(Util.getDirForDefaultVoice(context).getPath() + "/zero.mp3", 1);
            sound_one = soundPool.load(Util.getDirForDefaultVoice(context).getPath() + "/one.mp3", 1);
            sound_two = soundPool.load(Util.getDirForDefaultVoice(context).getPath() + "/two.mp3", 1);
            sound_three = soundPool.load(Util.getDirForDefaultVoice(context).getPath() + "/three.mp3", 1);
            sound_four = soundPool.load(Util.getDirForDefaultVoice(context).getPath() + "/four.mp3", 1);
            sound_five = soundPool.load(Util.getDirForDefaultVoice(context).getPath() + "/five.mp3", 1);
            sound_six = soundPool.load(Util.getDirForDefaultVoice(context).getPath() + "/six.mp3", 1);
            sound_seven = soundPool.load(Util.getDirForDefaultVoice(context).getPath() + "/seven.mp3", 1);
            sound_eight = soundPool.load(Util.getDirForDefaultVoice(context).getPath() + "/eight.mp3", 1);
            sound_nine = soundPool.load(Util.getDirForDefaultVoice(context).getPath() + "/nine.mp3", 1);
            sound_pound = soundPool.load(Util.getDirForDefaultVoice(context).getPath() + "/pound.mp3", 1);
            sound_star = soundPool.load(Util.getDirForDefaultVoice(context).getPath() + "/star.mp3", 1);

            // Put all in the hashmap variable
            PATH_TO_VOICES.put("0", sound_zero);
            PATH_TO_VOICES.put("1", sound_one);
            PATH_TO_VOICES.put("2", sound_two);
            PATH_TO_VOICES.put("3", sound_three);
            PATH_TO_VOICES.put("4", sound_four);
            PATH_TO_VOICES.put("5", sound_five);
            PATH_TO_VOICES.put("6", sound_six);
            PATH_TO_VOICES.put("7", sound_seven);
            PATH_TO_VOICES.put("8", sound_eight);
            PATH_TO_VOICES.put("9", sound_nine);
            PATH_TO_VOICES.put("*", sound_star);
            PATH_TO_VOICES.put("#", sound_pound);
        }
    }

    public static SoundPlayer getInstance(Context context) {
        if(soundPlayer == null) {
            soundPlayer = new SoundPlayer(context);
        }
        return soundPlayer;
    }

    public void playSound(DialpadButton dialpadButton) {
        try {
            String button_title;
            // Using for-each loop
            for (Map.Entry<String, Integer> mapElement : PATH_TO_VOICES.entrySet()) {
                String key = (String)mapElement.getKey();

                if(dialpadButton.getTitle().equals(key)) {
                    soundPool.play(PATH_TO_VOICES.get(key),
                            1, 1, 1, 0, 1);
                }
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Number format exception: " + nfe.getMessage());
        }
    }

    public void destroy() {
        if(soundPool != null) {
            soundPool.release();
            soundPool = null;
        }

        if(soundPlayer != null) {
            soundPlayer = null;
        }

        PATH_TO_VOICES.clear();
    }
}
