package com.example.notificationpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notificationpreferences.R;

public class MainActivity extends AppCompatActivity {

    private Switch soundSwitch, vibrationSwitch, ledSwitch, bannersSwitch, contentSwitch, lockScreenSwitch;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("NotificationSettings", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Initialize switches
        soundSwitch = findViewById(R.id.switch_sound);
        vibrationSwitch = findViewById(R.id.switch_vibration);
        ledSwitch = findViewById(R.id.switch_led);
        bannersSwitch = findViewById(R.id.switch_banners);
        contentSwitch = findViewById(R.id.switch_content);
        lockScreenSwitch = findViewById(R.id.switch_lock_screen);

        // Load saved preferences
        loadPreferences();

        // Add listeners for toggles
        setSwitchListeners();

        // Save button action
        findViewById(R.id.button_save).setOnClickListener(view -> showConfirmationDialog());
    }

    private void loadPreferences() {
        soundSwitch.setChecked(sharedPreferences.getBoolean("sound", false));
        vibrationSwitch.setChecked(sharedPreferences.getBoolean("vibration", false));
        ledSwitch.setChecked(sharedPreferences.getBoolean("led", false));
        bannersSwitch.setChecked(sharedPreferences.getBoolean("banners", false));
        contentSwitch.setChecked(sharedPreferences.getBoolean("content", false));
        lockScreenSwitch.setChecked(sharedPreferences.getBoolean("lock_screen", false));
    }

    private void setSwitchListeners() {
        CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
            if (buttonView.getId() == R.id.switch_sound) {
                editor.putBoolean("sound", isChecked);
            } else if (buttonView.getId() == R.id.switch_vibration) {
                editor.putBoolean("vibration", isChecked);
            } else if (buttonView.getId() == R.id.switch_led) {
                editor.putBoolean("led", isChecked);
            } else if (buttonView.getId() == R.id.switch_banners) {
                editor.putBoolean("banners", isChecked);
            } else if (buttonView.getId() == R.id.switch_content) {
                editor.putBoolean("content", isChecked);
            } else if (buttonView.getId() == R.id.switch_lock_screen) {
                editor.putBoolean("lock_screen", isChecked);
            }
        };

        soundSwitch.setOnCheckedChangeListener(listener);
        vibrationSwitch.setOnCheckedChangeListener(listener);
        ledSwitch.setOnCheckedChangeListener(listener);
        bannersSwitch.setOnCheckedChangeListener(listener);
        contentSwitch.setOnCheckedChangeListener(listener);
        lockScreenSwitch.setOnCheckedChangeListener(listener);
    }

    private void showConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Save Preferences")
                .setMessage("Are you sure you want to save the changes?")
                .setPositiveButton("Save", (dialog, which) -> {
                    editor.apply();
                    Toast.makeText(MainActivity.this, "Preferences saved!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
