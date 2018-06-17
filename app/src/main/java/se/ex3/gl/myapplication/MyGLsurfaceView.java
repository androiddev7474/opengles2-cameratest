package se.ex3.gl.myapplication;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class MyGLsurfaceView extends GLSurfaceView {

    public MyGLsurfaceView(Context context, AttributeSet attrs) {

        super(context, attrs);

        setEGLContextClientVersion(2);

        setRenderer(new MyGLrenderer(context));

    }

}
