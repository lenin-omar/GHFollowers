package com.example.lofm.githubfollowers;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.lofm.githubfollowers.ui.OpenGLRenderer;

public class SplashActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //OpenGL view. Go fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        GLSurfaceView view = new GLSurfaceView(this);
        view.setEGLConfigChooser(8, 8, 8, 8, 16, 0);    //Transparent background
        view.getHolder().setFormat(PixelFormat.TRANSLUCENT);    //Transparent background
        view.setRenderer(new OpenGLRenderer(this));
        setContentView(view);
        //Transition to Landing screen
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This app main activity
                Intent i = new Intent(SplashActivity.this, SearchActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
