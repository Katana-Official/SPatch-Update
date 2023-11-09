package com.android.launcher2

import android.content.Intent
import lu.die.foza.SuperAPI.FozaCore
import lu.die.fozacompatibility.FozaPackageManager

object LauncherLoader {
    // For java
    @JvmStatic
    fun instance() = this
    fun waitForAndGetInitialService(
        serviceCallback : () -> Unit
    ) = FozaCore.registerCoreCallback(
        serviceCallback
    )
    fun obtainLaunchIntentByPackage(
        src : String
    ) = FozaPackageManager.get().getLaunchIntentForPackage(
        Intent(Intent.ACTION_MAIN).setPackage(src) /* Package Name */
    )
}