package se.ex3.gl.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

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

    private float incr, centerX;
    private boolean increment = true;

    private float[] mViewMatrix = new float[16];

    private int nominator = 10000;

    int mProgramHandle;

    private Context context;

    public MyGLrenderer(Context context) {

        this.context = context;

        models.create2Dpolygon(0.5f, 0.5f, 0f);

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


        gLprojection.perspectiveProject(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // rotation var tionde sekund
        long time = SystemClock.uptimeMillis() % nominator;
        float angleInDegrees = (360.0f / nominator) * ((int) time);

        Matrix.setIdentityM(models.getmModelMatrix(), 0);
        Matrix.translateM(models.getmModelMatrix(), 0, -0f, 0.0f, -2.2f);
        /*Matrix.rotateM(models.getmModelMatrix(), 0, angleInDegrees, 1.0f, 1.0f, 0.5f);
        */
        GLES20.glUseProgram(mProgramHandle);

        if (centerX > 0.8)
            increment = false;
        if (centerX < -0.8)
            increment = true;

        if (increment)
            centerX += 0.05;
        else
            centerX -= 0.05;

        Matrix.setLookAtM(gLcamera.getmViewMatrix(), 0, 0, 0, 0.5f, centerX, -0.3f, -0, 0, 1, 0);


        gLrender.render();
    }
}
