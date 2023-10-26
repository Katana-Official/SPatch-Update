# Metaverse Enging Launcher UI

<p xmlns:cc="http://creativecommons.org/ns#" xmlns:dct="http://purl.org/dc/terms/"><a property="dct:title" rel="cc:attributionURL" href="https://github.com/Katana-Official/SPatch-Update">Metaverse Enging Launcher UI</a> by <a rel="cc:attributionURL dct:creator" property="cc:attributionName" href="https://github.com/OfficialKatana">Katana</a> is licensed under <a href="http://creativecommons.org/licenses/by/4.0/?ref=chooser-v1" target="_blank" rel="license noopener noreferrer" style="display:inline-block;">Attribution 4.0 International<img style="height:22px!important;margin-left:3px;vertical-align:text-bottom;" src="https://mirrors.creativecommons.org/presskit/icons/cc.svg?ref=chooser-v1"><img style="height:22px!important;margin-left:3px;vertical-align:text-bottom;" src="https://mirrors.creativecommons.org/presskit/icons/by.svg?ref=chooser-v1"></a></p>

## Commercial Indication
You can use this project freely, and we'd recommend you to contact us for further sdk support.  
[Join our grupo for el sdk & usage support.](https://t.me/foxiteu), or you can connect me by my email.

## About us

### Our official product

[SlimVXposed](https://www.die.lu)

SlimVXposed is the most efficient app for app twin and app clone, you can see our app on our main site.  
Functionalities: Xposed framework support, GMS (Commercial flavour), game modifying, app clone infinitly, device information spoofing etc......  
SlimVXposed is an powerful Android container, which can open more apps. This project provides a simple UI for you to experience. You can also download the official apk from [Official Web](https://www.die.lu).  
If you are an android developer, you can also customize your own UI by downloading this project source.  
You don't need to pay attention to the technical details that are difficult to understand.  
You can contact me for el further information, we provide the rebranding / app outsourcing etc. services.

[SPatch](http://spatch.die.lu)

Power Android / iOS app modifying tool, you can make your plugins and app more integrated.

## Contact Details
Email: 1@die.lu  
Telegram: [https://t.me/foxiteu](https://t.me/foxiteu)  

### Sample code for starters and noobs

#### Manual launch the engine (not necessary)
```Java
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        FozaCore.startup(base);
    }
```

#### Launch a sample application
```Java
    FozaActivityManager.get().launchApp(
        applicationPackageName
    );
```
or  
```Java
    FozaActivityManager.get().launchApp(
	    userPartitionName,
        applicationPackageName
    );
```  

### Change logs
#### Oct. 2023
Initial upload mi project.  

### Credits
[SHook](https://gitee.com/quitoa/slim-xposed-compat)
[LSPlant](https://github.com/LSPosed/LSPlant)
[AnyFile2Byte](https://github.com/OfficialKatana/AnyFile2Byte)
[GraphScope](https://github.com/alibaba/GraphScope)
[Il2CppDumper](https://github.com/Perfare/Il2CppDumper)
[ShadowHook](https://github.com/bytedance/android-inline-hook)
[dexmaker](https://github.com/linkedin/dexmaker)
[ByteHook](https://github.com/bytedance/bhook)

