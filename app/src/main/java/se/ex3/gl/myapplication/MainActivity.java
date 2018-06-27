package se.ex3.gl.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //primitiver
    private float progress_val_eye, progress_val_fov;
    public static final int DEFAULT_EYE_Z_VAL = -4;
    public static final float DEFAULT_FOV_VAL = 45;

    //java/android api klasser
    private Switch eyeYswitch, centerXswitch, centerYswitch;
    private SeekBar seekBarFov, seekBarEyeX, seekBarEyeZ;
    private TextView tvProgressLabelFov, tvProgressLabelEyeZ;
    private LinearLayout cameraPanelLinLayout;

    //egna klasser
    private MyGLsurfaceView myGLsurfaceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress_val_eye = DEFAULT_EYE_Z_VAL;
        progress_val_fov = DEFAULT_FOV_VAL;

        cameraPanelLinLayout = findViewById(R.id.camera_panel_id);
        myGLsurfaceView = (MyGLsurfaceView) findViewById(R.id.glsurface_id);
        myGLsurfaceView.setCamPanelLayout(cameraPanelLinLayout);

        eyeYswitch = findViewById(R.id.eyeY);
        centerXswitch = findViewById(R.id.centerX);
        centerYswitch = findViewById(R.id.centerY);
        seekBarFov = findViewById(R.id.seekBarFov);
        seekBarEyeZ = findViewById(R.id.seekBarEyeZ);
        tvProgressLabelFov = findViewById(R.id.textViewFov);
        tvProgressLabelEyeZ = findViewById(R.id.textViewEyeZ);

        seekBarFov.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // updated continuously as the user slides the thumb
                progress_val_fov = progress;
                tvProgressLabelFov.setText("" + progress);
                myGLsurfaceView.getMyGLrenderer().setFov(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        seekBarEyeZ.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                progress_val_eye = progress;
                tvProgressLabelEyeZ.setText("" + (-progress));
                myGLsurfaceView.getMyGLrenderer().setEyeZ(-progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        initSwitchListeners();

    }

    public void onResume() {

        super.onResume();

        myGLsurfaceView.getMyGLrenderer().setFov(progress_val_fov);
        myGLsurfaceView.getMyGLrenderer().setEyeZ(progress_val_eye);

    }

    public void onPause() {

        super.onPause();
    }


    private void initSwitchListeners() {

        eyeYswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean switchOn) {

                if (switchOn)
                    myGLsurfaceView.getMyGLrenderer().setEyeYenabled(true);
                else
                    myGLsurfaceView.getMyGLrenderer().setEyeYenabled(false);
            }
        });



        //center
        centerXswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean switchOn) {

                if (switchOn)
                    myGLsurfaceView.getMyGLrenderer().setCenterXenabled(true);
                else
                    myGLsurfaceView.getMyGLrenderer().setCenterXenabled(false);
            }
        });

        centerYswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean switchOn) {

                if (switchOn)
                    myGLsurfaceView.getMyGLrenderer().setCenterYenabled(true);
                else
                    myGLsurfaceView.getMyGLrenderer().setCenterYenabled(false);
            }
        });

    }

    public void onSettingsClick(View view) {

        startActivity(new Intent(this, SettingsActivity.class));

    }

    public void onInfoClick(View view) {

       startActivity(new Intent(this, InfoActivity.class));

    }
}
