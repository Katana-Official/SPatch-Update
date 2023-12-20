package com.android.launcher2

import android.content.Intent
import net_62v.external.MetaCore
import net_62v.external.MetaPackageManager

object LauncherLoader {
    // For java
    @JvmStatic
    fun instance() = this
    fun waitForAndGetInitialService(
        serviceCallback : () -> Unit
    ) = MetaCore.registerCoreCallback(
        serviceCallback
    )
    fun obtainLaunchIntentByPackage(
        src : String
    ) = MetaPackageManager.getLaunchIntentForPackage(
        Intent(Intent.ACTION_MAIN).setPackage(src) /* Package Name */
    )
}