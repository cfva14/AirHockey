package io.github.cfva14.opengl.utils

import android.opengl.GLES20.*
import android.util.Log

/**
 * Created by Carlos Valencia on 2/12/18.
 */

object ShaderHelper {

    private val TAG = ShaderHelper::class.java.simpleName

    fun compileVertexShader(vertexShaderCode: String): Int {
        return compileShader(GL_VERTEX_SHADER, vertexShaderCode)
    }

    fun compileFragmentShader(fragmentShaderCode: String): Int {
        return compileShader(GL_FRAGMENT_SHADER, fragmentShaderCode)
    }

    private fun compileShader(type: Int, shaderCode: String): Int {
        val shaderObjectId = glCreateShader(type)

        if (shaderObjectId == 0) {
            Log.w(TAG, "Could not create shader")
            return 0
        }

        glShaderSource(shaderObjectId, shaderCode)
        glCompileShader(shaderObjectId)

        val compileStatus = intArrayOf(1)
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0)
        Log.w(TAG, "Result of compiling shader: \n" + shaderCode + "\n" + glGetShaderInfoLog(shaderObjectId))

        if (compileStatus[0] == 0) {
            glDeleteShader(shaderObjectId)
            Log.w(TAG, "Could not compile shader")
            return 0
        }

        return shaderObjectId
    }

    fun linkShaders(vertexShaderId: Int, fragmentShaderId: Int): Int {
        val programObjectId = glCreateProgram()

        if (programObjectId == 0) {
            Log.w(TAG, "Could not create program")
            return 0
        }

        glAttachShader(programObjectId, vertexShaderId)
        glAttachShader(programObjectId, fragmentShaderId)
        glLinkProgram(programObjectId)

        val linkStatus = intArrayOf(1)
        glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0)
        Log.v(TAG, "Result of linking shaders: " + glGetProgramInfoLog(programObjectId))

        if (linkStatus[0] == 0) {
            glDeleteProgram(programObjectId)
            Log.e(TAG, "Could not link shaders")
            return 0
        }

        return programObjectId
    }

    fun validateProgram(programObjectId: Int): Boolean {
        glValidateProgram(programObjectId)
        val validateStatus = intArrayOf(1)
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0)
        Log.v(TAG, "Result of validating program: " + glGetProgramInfoLog(programObjectId))
        return validateStatus[0] != 0
    }

}