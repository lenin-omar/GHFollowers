package com.example.lofm.githubfollowers.ui;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by LOFM on 22/02/2017.
 */

public class OpenGLRenderer implements GLSurfaceView.Renderer {

    private static float angleCube = 0;     // rotational angle in degree for cube
    private static float speedCube = -2.0f; // rotational speed for cube
    private Context context;   // Application context needed to read image
    private TextureCube cube;

    // Constructor
    public OpenGLRenderer(Context context) {
        this.context = context;   // Get the application context
        cube = new TextureCube();
    }

    // Call back when the surface is first created or re-created.
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0, 0, 0, 0);  // Set color's clear-value to black
        gl.glClearDepthf(1.0f);            // Set depth's clear-value to farthest
        gl.glEnable(GL10.GL_DEPTH_TEST);   // Enables depth-buffer for hidden surface removal
        gl.glDepthFunc(GL10.GL_LEQUAL);    // The type of depth testing to do
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);  // nice perspective view
        gl.glShadeModel(GL10.GL_SMOOTH);   // Enable smooth shading of color
        gl.glDisable(GL10.GL_DITHER);      // Disable dithering for better performance
        // Setup Texture, each time the surface is created
        cube.loadTexture(gl, context);    // Load image into Texture
        gl.glEnable(GL10.GL_TEXTURE_2D);  // Enable texture
    }

    // Call back after onSurfaceCreated() or whenever the window's size changes.
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    // Call back to draw the current frame.
    @Override
    public void onDrawFrame(GL10 gl) {
        // Clear color and depth buffers
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        // Render the Cube
        gl.glLoadIdentity();                  // Reset the current model-view matrix
        gl.glTranslatef(0.0f, 0.0f, -10.0f);   // Translate into the screen
        gl.glRotatef(angleCube, 0.1f, 1.0f, 0.5f); // Rotate
        cube.draw(gl);
        // Update the rotational angle after each refresh.
        angleCube += speedCube;
    }
}