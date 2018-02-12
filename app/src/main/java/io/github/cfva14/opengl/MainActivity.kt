package io.github.cfva14.opengl

import android.opengl.GLSurfaceView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var glSurfaceView: GLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        glSurfaceView = GLSurfaceView(this)
        glSurfaceView.setEGLContextClientVersion(2)
        glSurfaceView.setRenderer(CustomRenderer(this))

        setContentView(glSurfaceView)
    }

    override fun onPause() {
        super.onPause()
        glSurfaceView.onPause()
    }

    override fun onResume() {
        super.onResume()
        glSurfaceView.onResume()
    }
}

// Triangle Fan
//0f,     0f,
//-0.5f, -0.5f,
//0.5f, -0.5f,
//0.5f,  0.5f,
//-0.5f,  0.5f,
//-0.5f, -0.5f,
//
//// Line
//-0.5f, 0f,
//0.5f, 0f,
//
//// Mallet 1
//0f, -0.25f,
//
//// Mallet 2
//0f, 0.25f,
//
//// Puck
//0f, 0f
