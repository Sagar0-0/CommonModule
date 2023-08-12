# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# to prevent proguard from warning sdk users about missing classes
#Coroutines---------------------------------------------------------
-keepattributes Signature
-keep class kotlin.coroutines.Continuation
#Coroutines---------------------------------------------------------

#Retrofit-----------------------------------------------------------
-if interface * { @retrofit2.http.* *** *(...); }
-keep,allowobfuscation interface <3>

-keep,allowobfuscation,allowshrinking interface retrofit2.Call
#Retrofit-----------------------------------------------------------

#Gson---------------------------------------------------------------
-keepclassmembers,allowobfuscation class * {
 @com.google.gson.annotations.SerializedName <fields>;
}

-keepattributes Signature
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken
#Gson---------------------------------------------------------------

#Spotify-----------------------------------------------------------
-dontwarn com.fasterxml.jackson.databind.deser.std.*
-dontwarn com.fasterxml.jackson.databind.ser.std.*
-dontwarn com.spotify.base.annotations.*
#Spotify-----------------------------------------------------------

#Razorpay-----------------------------------------------------------
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-keepattributes JavascriptInterface
-keepattributes *Annotation*

-dontwarn com.razorpay.**
-keep class com.razorpay.** {*;}

-optimizations !method/inlining/*

-keepclasseswithmembers class * {
  public void onPayment*(...);
}
#Razorpay-----------------------------------------------------------
