# Android Rx, Model, View and Lifecycle

##Currently In Dev

An attempt to fix the issues with the MVP pattern on android by introducing RxJava to replace the presenter spaghetti code
and introducing the lifecycle to proxy the Android lifecycle.

Video presentation on how this works and why I created it.

(Not on jcenter yet)
Basic Activity and Fragment support
```groovy
    compile 'com.twistedequations:android-mvl:1.0.0'
```

Support V4 version that adds MVLFragmentActivity and support Fragment
```groovy
    compile 'com.twistedequations:android-mvl-support-v4:1.0.0'
```

Appcompat version that adds MVLAppCompatActivity 
```groovy
    compile 'com.twistedequations:android-mvl-appcompat-v7:1.0.0'
```