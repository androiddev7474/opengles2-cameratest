package se.ex3.gl.myapplication;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "settings_activity";
    public static final String AUTO_ROTATE = "autoRotate";

    private Switch autoRotateSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        autoRotateSwitch = findViewById(R.id.autoRotateSwitchId);
        boolean isOn = getSwitcheState(MY_PREFS_NAME, AUTO_ROTATE);
        autoRotateSwitch.setChecked(isOn);

        initSwitchListeners();

    }

    public void onBackClick(View view) {

        finish();
    }

    private void initSwitchListeners() {

        autoRotateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean switchOn) {

                if (switchOn)
                    savePrefs(switchOn, MY_PREFS_NAME, AUTO_ROTATE);
                else
                    savePrefs(switchOn, MY_PREFS_NAME, AUTO_ROTATE);
            }
        });

    }

    private void savePrefs(boolean serviceOn, String prefs_id, String bool_id) {

        SharedPreferences.Editor editor = getSharedPreferences(prefs_id, MODE_PRIVATE).edit();
        editor.putBoolean(bool_id, serviceOn);
        editor.apply();
    }

    private boolean getSwitcheState(String prefs_id, String bool_id) {

        SharedPreferences prefs = getSharedPreferences(prefs_id, MODE_PRIVATE);
        boolean restoredSwitchState = prefs.getBoolean(bool_id, false);

        return restoredSwitchState;
    }



}
