package se.ex3.gl.myapplication;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class MyGLsurfaceView extends GLSurfaceView {

    private MyGLrenderer myGLrenderer;
    private LinearLayout camPanelLayout;

    public MyGLsurfaceView(Context context, AttributeSet attrs) {

        super(context, attrs);

        setEGLContextClientVersion(2);
        myGLrenderer = new MyGLrenderer(context);
        setRenderer(myGLrenderer);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int eventAction = event.getAction();

        float x = event.getX();
        float y = event.getY();
        myGLrenderer.setXpos(x);
        myGLrenderer.setYpos(y);

        boolean down = false;
        boolean up = false;
        boolean move = false;
        switch (eventAction) {
            case MotionEvent.ACTION_DOWN:
                down = true;
                camPanelLayout.setVisibility(View.GONE);
                break;
            case MotionEvent.ACTION_UP:
                up = true;
                camPanelLayout.setVisibility(View.VISIBLE);
                break;
            case MotionEvent.ACTION_MOVE:
                move = true;
                camPanelLayout.setVisibility(View.GONE);
                break;
        }


        myGLrenderer.setDown(down);
        myGLrenderer.setUp(up);
        myGLrenderer.setMove(move);

        return true;

    }

    public MyGLrenderer getMyGLrenderer() {

        return myGLrenderer;
    }

    public void setCamPanelLayout(LinearLayout camPanelLayout) {

        this.camPanelLayout = camPanelLayout;
    }
}
