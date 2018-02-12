package io.github.cfva14.opengl.utils

import android.content.Context
import android.content.res.Resources
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * Created by Carlos Valencia on 2/12/18.
 */

object GLSLFileReader {

    fun readFileFromResources(context: Context, resourceId: Int): String {
        val body = StringBuilder()

        try {

            val inputReader = context.resources.openRawResource(resourceId)
            val inputStreamReader = InputStreamReader(inputReader)
            val bufferedReader = BufferedReader(inputStreamReader)

            for (line in bufferedReader.readLines()) {
                body.append(line + "\n")
            }

        } catch (ioe: IOException) {
            throw RuntimeException("Could not open resourceId: " + resourceId, ioe)
        } catch (nfe: Resources.NotFoundException) {
            throw RuntimeException("Could not find resourceId: " + resourceId, nfe)
        }

        return body.toString()
    }

}