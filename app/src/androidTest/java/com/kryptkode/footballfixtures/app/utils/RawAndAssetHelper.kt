package com.kryptkode.footballfixtures.app.utils

import android.content.Context
import android.content.res.AssetManager
import timber.log.Timber

import java.io.*

class RawAndAssetHelper {

    private fun getInputStreams(manager: AssetManager, path: String): InputStream? {
        val file = File(path)
        try {
            return manager.open(file.path)
        } catch (e: IOException) {
            Timber.e(e)
            return null
        }

    }

    private fun getFiles(manager: AssetManager, directory: String?): Array<String>? {
        if (directory == null) {
            return arrayOf()
        }
        try {
            return manager.list(directory)
        } catch (e: IOException) {
            Timber.e(e)
            return arrayOf()
        }

    }


    private fun createFromStream(stream: InputStream?): String? {
        val descBuilder = StringBuilder()
        val reader = BufferedReader(InputStreamReader(stream!!))
        try {
            var line: String? = reader.readLine()
            while (line != null) {
                if (descBuilder.isNotEmpty()) {
                    descBuilder.append("\n")
                }
                descBuilder.append(line)
                line = reader.readLine()
            }
        } catch (e: IOException) {
            Timber.e(e)
            return null
        }

        return descBuilder.toString()
    }


    fun getAssetFileContents(context: Context, path: String): String? {
        return createFromStream(getInputStreams(context.assets, path))
    }

    fun getRawResFileContents(context: Context, fileName:String):String?{
        return try {
            createFromStream(context.resources
                .openRawResource(context.resources.getIdentifier(fileName,
                    "raw", context.packageName)))
        }catch (e:Exception){
            Timber.e(e)
            null
        }
    }
}
