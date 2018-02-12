package io.github.cfva14.opengl

import android.content.Context
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import io.github.cfva14.opengl.utils.GLSLFileReader
import io.github.cfva14.opengl.utils.ShaderHelper
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by Carlos Valencia on 2/12/18.
 */

class CustomRenderer(private val context: Context) : GLSurfaceView.Renderer {

    private var program = 0
    private var uColorPosition = 0
    private var aPositionLocation = 0

    private val table = floatArrayOf(
            // Triangle Fan
            0f,     0f,
            -0.5f, -0.5f,
            0.5f, -0.5f,
            0.5f,  0.5f,
            -0.5f,  0.5f,
            -0.5f, -0.5f,

            // Line
            -0.5f, 0f,
            0.5f, 0f,

            // Mallet 1
            0f, -0.25f,

            // Mallet 2
            0f, 0.25f,

            // Puck
            0f, 0f
    )

    private val vertexData = ByteBuffer
            .allocateDirect(table.size * BYTES_PER_FLOAT)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()

    init {
        vertexData.put(table)
    }

    override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
        glClearColor(0f, 0f, 0f, 0f)

        val vertexShaderSource = GLSLFileReader.readFileFromResources(context, R.raw.simple_vertex)
        val fragmentShaderSource = GLSLFileReader.readFileFromResources(context, R.raw.simple_fragment)

        val vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource)
        val fragmentVertex = ShaderHelper.compileFragmentShader(fragmentShaderSource)

        program = ShaderHelper.linkShaders(vertexShader, fragmentVertex)
        glUseProgram(program)

        ShaderHelper.validateProgram(program)

        aPositionLocation = glGetAttribLocation(program, A_POSITION)
        uColorPosition = glGetUniformLocation(program, U_COLOR)

        vertexData.position(0)
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexData)
        glEnableVertexAttribArray(aPositionLocation)

    }

    override fun onSurfaceChanged(p0: GL10?, p1: Int, p2: Int) {
        glViewport(0, 0, p1, p2)
    }

    override fun onDrawFrame(p0: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT)

        glUniform4f(uColorPosition, 1f, 1f, 1f, 1f)
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6)

        glUniform4f(uColorPosition, 1f, 0f, 0f, 1f)
        glDrawArrays(GL_LINES, 6, 2)

        glUniform4f(uColorPosition, 0f, 0f, 1f, 1f)
        glDrawArrays(GL_POINTS, 8, 1)

        glUniform4f(uColorPosition, 1f, 0f, 0f, 1f)
        glDrawArrays(GL_POINTS, 9, 1)

        glUniform4f(uColorPosition, 0f, 0f, 0f, 0f)
        glDrawArrays(GL_POINTS, 10, 1)
    }

    companion object {
        private const val POSITION_COMPONENT_COUNT = 2
        private const val BYTES_PER_FLOAT = 4
        private const val A_POSITION = "a_Position"
        private const val U_COLOR = "u_Color"
    }
}