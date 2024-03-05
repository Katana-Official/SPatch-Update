-ignorewarnings
-optimizationpasses 7
-allowaccessmodification
-keep class android.** { *; }
-keep class de.** { *; }
-keepclassmembers class * {
    native <methods>;
}
#-keepclasseswithmembernames,includedescriptorclasses class * {
#    native <methods>;
#}
-keepclasseswithmembers,allowshrinking class * {
    native <methods>;
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-dontwarn *
-repackageclasses