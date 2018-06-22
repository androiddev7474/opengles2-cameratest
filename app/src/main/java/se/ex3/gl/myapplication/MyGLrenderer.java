package se.ex3.gl.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import renderlib.opengles.se.myapplication.*;
import textcreatorlib.opengles.se.textcreator.*;
import compileshaders.opengles.se.shader_compile.*;

public class MyGLrenderer implements GLSurfaceView.Renderer {


    private Models models = new Models();
    private GLcamera gLcamera = new GLcamera();
    private GLprojection gLprojection = new GLprojection();
    private GLrender gLrender;
    private TextureCreator textureCreator;

    private float incr, lookAtVar;
    private boolean increment = true;
    private boolean eyeXenabled, eyeYenabled, centerXenabled, centerYenabled, translateEnabled;

    private float xPos, yPos;
    private float eyeX, eyeY, eyeZ, centerX, centerY, centerZ;
    private boolean down, up, move;

    private float[] mViewMatrix = new float[16];
    private int screenWidth, screenHeight, viewportW, viewportH;

    private int nominator = 10000;

    int mProgramHandle;

    private Context context;

    public MyGLrenderer(Context context) {

        this.context = context;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = windowManager.getDefaultDisplay();
        defaultDisplay.getRealMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        eyeXenabled = true;

        eyeZ = -2;

        //models.create2Dpolygon(0.5f, 0.5f, 0f);
        models.create3Dpolygon(0.5f, 0.5f, 0.5f);

        float[] blc = {0, 0};
        float[] brc = {1, 0};
        float[] tlc = {0, 1};
        float[] trc = {1, 1};

        models.create3DtextData(blc, brc, tlc, trc);

        gLrender = new GLrender(models, gLcamera, gLprojection, context);
        gLrender.initBuffers();
        textureCreator = new TextureCreator(context);

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {


        GLES20.glEnable(GLES20.GL_CULL_FACE);

        //skapa texturer
        float[][] bitmapDimens = {{512, 512}};
        String[] bitmapNames = {"atlas1"};
        textureCreator.loadTexture(bitmapDimens, bitmapNames, "drawable", GLES20.GL_TEXTURE_2D, GLES20.GL_LINEAR, GLES20.GL_LINEAR, Bitmap.Config.RGB_565);

        //kompilera
        String[] attributes = new String[] {"a_Position",  "a_Color", "a_Normal", "a_TexCoordinate"};
        Compile compile = new Compile(context, R.raw.per_pixel_vertex_shader, R.raw.per_pixel_fragment_shader, attributes);
        mProgramHandle = compile.getmProgramHandle();
        gLrender.setProgramHandle(mProgramHandle);
        gLrender.setHandles();
        //gLcamera.createCamera();


    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        viewportW = width;
        viewportH = height;
        //gLprojection.perspectiveProject(width, height, 1.0f, 40f);
        gLprojection.perspectiveProject(width, height, 0.5f, 40.0f, 95.0f);
    }




    @Override
    public void onDrawFrame(GL10 gl) {

        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // rotation var tionde sekund
        long time = SystemClock.uptimeMillis() % nominator;
        float angleInDegrees = (360.0f / nominator) * ((int) time);

        Matrix.setIdentityM(models.getmModelMatrix(), 0);
        Matrix.translateM(models.getmModelMatrix(), 0, -0f, 0.0f, -0.0f);
        /*Matrix.rotateM(models.getmModelMatrix(), 0, angleInDegrees, 1.0f, 1.0f, 0.5f);
        */
        GLES20.glUseProgram(mProgramHandle);

        if (lookAtVar > 3)
            increment = false;
        if (lookAtVar < -3)
            increment = true;

        if (increment)
            lookAtVar += 0.01;
        else
            lookAtVar -= 0.01;

        //Log.i("lookAT", xPos + " (" + eyeX + ")" );
        Log.i("Center X", "" + centerX);

        Matrix.setLookAtM(gLcamera.getmViewMatrix(), 0, eyeX, eyeY, eyeZ, centerX, centerY, -0, 0, 1, 0);


        gLrender.render();
    }

    public void setXpos(float xPos) {

        this.xPos = xPos;
        if (eyeXenabled) {
            this.eyeX = (2 * xPos) / screenWidth - 1;
            eyeX *= 6;
        }

        if (centerXenabled) {
            this.centerX = (2 * xPos) / screenWidth - 1;
            this.centerX *= 6;
            //centerX = centerX / 1.5f; // minska elasticitet
        }
    }

    public void setYpos(float yPos) {

        this.yPos = yPos;
        if (eyeYenabled) {
            this.eyeY = (2 * yPos) / screenHeight - 1;
            eyeY *= 6;
        }

        if (centerYenabled) {
            this.centerY = (2 * yPos) / screenHeight - 1;
            this.centerY *= 6;
            //centerY = centerY / 1.5f; // minska elasticitet
        }
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setMove(boolean move) {
        this.move = move;
    }


    public void setEyeXenabled(boolean eyeXenabled) {
        this.eyeXenabled = eyeXenabled;
    }

    public void setEyeYenabled(boolean eyeYenabled) {
        this.eyeYenabled = eyeYenabled;
    }

    public void setCenterXenabled(boolean centerXenabled) {
        this.centerXenabled = centerXenabled;
    }

    public void setCenterYenabled(boolean centerYenabled) {
        this.centerYenabled = centerYenabled;
    }

    public void setTranslateEnabled(boolean translateEnabled) {
        this.translateEnabled = translateEnabled;
    }

    public void setFov(float fov) {

        gLprojection.perspectiveProject(viewportW, viewportH, 1.0f, 40.0f, fov);
    }

    public void setEyeZ(float eyeZ) {

        this.eyeZ = eyeZ;
    }
}
