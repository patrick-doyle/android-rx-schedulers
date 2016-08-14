# Android Rx Android Schedulers

```groovy
    compile 'com.twistedequations.rx:android-schedulers:1.1.0'
```

Wrapper class around the RxSchedulers that can be used to make then injectable and thus can be swapped out for testing.

The `DefaultAndroidRxSchedulers` implementation is optimised for android by having the thread priority set to background to reduce interference with the main thread. 
The threads are also named according to the thread pool.

A network thread pool was added with a limit of 6 threads t0 prevent 

#### Usage

Create a single instance of `AndroidRxSchedulers` using one of the implementations, the Application subclass would be a good place to create it. This can be injected 
into your presenter via a constructor. This allows you you swap out for the `TestAndroidRxSchedulers` version for unit tests which forces everything onto the main thread.

```java

    private final HomeView homeView;
    private final HomeModel homeModel;
    private final AndroidRxSchedulers androidRxSchedulers;

    public HomePresenter(HomeView homeView, HomeModel homeModel, AndroidRxSchedulers androidRxSchedulers) {
        this.homeView = homeView;
        this.homeModel = homeModel;
        this.androidRxSchedulers = androidRxSchedulers;
    }
    
    ...//More code
    
    private Observable<List<RedditItem>> loadPostsObservable() {
    return homeModel.postsForAll()
        .subscribeOn(androidRxSchedulers.network())
        .observeOn(androidRxSchedulers.mainThread());
    }
```

IF you are already using the default schedulers you can use the `AndroidRxPlugin.applyRxJavaPlugins()` methods to set the default RxJava (eg `Schedulers.io()`)schedulers.