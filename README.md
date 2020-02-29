Introduction
------------
**SPatch** is a simple App based on [SandHook](https://github.com/ganyao114/SandHook) that allows you to use an Xposed Module without needing to root, unlock the bootloader, or flash a custom system image. (Supports Android 5.0~9.0) 

The only two restriction of SPatch are:

1. Unable to modify system, so any Module which modifies system won't be able to work properly.
2. Currently resource hooks are not supported. (Theming modules use Resource Hooks).

Usage
-------

### Preparation

Download the latest APK from the [release page](https://github.com/lianglixin/SPatch/releases), and install it on your Android device.

### Install APP and Xposed Module

Open SPatch, Click on the **Drawer Button** at the bottom of home page(Or long click the screen), add your desired APP and Xposed Module to SPatch's virtual environment.

Note: **All operations（installation of Xposed Module, APP）must be done in SPatch**, otherwise the Xposed Module installed won't take effect. For example, if you install the YouTube app on your system (Your phone's original system, not in SPatch), and then install YouTube AdAway (A YouTube Xposed Module) in SPatch; or you install YouTube in SPatch, and install YouTube AdAway on original system; or both of them are installed on original system, **neither of these three cases will work!**

![How to install](https://github.com/lianglixin/SPatch)

There are three ways to install an APP or Xposed Module to SPatch:

1. **Clone an installed app from your original system.** (Click Button at bottom of home page, then click Add App, the first page shows a list of installed apps.)
2. **Install via an APK file.** (Click Button at bottom of home page, then click Add App, the second page shows APKs found in your sdcard)
3. **Install via an external file chooser.** (Click Button at bottom of home page, then click Add App, use the floating action button to choose an APK file to install)

For Xposed Module, You can install it from Xposed Installer, too.

### Activate the Xposed Module

Open Xposed Installer in SPatch, go to the module fragment, check the module you want to use:

![How to activate module](https://github.com/lianglixin/SPatch)

### Reboot

You only need to reboot SPatch, **There's no need to reboot your phone**; Just click Settings in home page of SPatch, click `Reboot` button, and SPatch will reboot in a blink. 

![How to reboot](https://github.com/lianglixin/SPatch)

Supported Modules 
-------------------------

Almost all modules except system-relevant are supported, please try it by yourself :)

Others
-------

### GameGuardian

SPatch also supports GameGuardian, **you should use the separate version for GameGuardian**.(Download it in release page).

[Video Tutorial](https://github.com/lianglixin/SPatch)

Support
-----------

Contributions to SPatch are always welcomed!!

For Developers
--------------

- [File a bug](https://github.com/lianglixin/SPatch/issues)
- [Wiki](https://github.com/lianglixin/SPatch/wiki)

Credits
-------

1. [VirtualApp](https://github.com/ganyao114/SandHook)
2. [Xposed](https://github.com/rovo89/Xposed)
