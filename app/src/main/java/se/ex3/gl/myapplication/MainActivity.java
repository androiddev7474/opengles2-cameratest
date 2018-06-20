package se.ex3.gl.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {


    private MyGLsurfaceView myGLsurfaceView;
    private Switch eyeXswitch, eyeYswitch, centerXswitch, centerYswitch, translateSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myGLsurfaceView = (MyGLsurfaceView) findViewById(R.id.glsurface_id);
        eyeXswitch = findViewById(R.id.eyeX);
        eyeYswitch = findViewById(R.id.eyeY);
        centerXswitch = findViewById(R.id.centerX);
        centerYswitch = findViewById(R.id.centerY);
        translateSwitch = findViewById(R.id.translate);

        initSwitchListeners();

    }

    private void initSwitchListeners() {

        //eye
        eyeXswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean switchOn) {

                if (switchOn)
                    myGLsurfaceView.getMyGLrenderer().setEyeXenabled(true);
                else
                    myGLsurfaceView.getMyGLrenderer().setEyeXenabled(false);
            }
        });

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



        translateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean switchOn) {


            }
        });



    }
}
