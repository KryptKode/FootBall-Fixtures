package com.kryptkode.footballfixtures.app.utils

import android.content.Context
import javax.inject.Inject


class AssetsLoader @Inject constructor(private val context: Context) {
    private val assetHelper = RawAndAssetHelper()
    fun getRawResourceDataAsString(fileName: String): String? {
        return assetHelper.getRawResFileContents(
            context,
            fileName
        )
    }

    fun getAssetDataAsString(fileName: String): String? {
        return assetHelper.getAssetFileContents(
            context,
            fileName
        )
    }
}
