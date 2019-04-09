package io.github.iamyours.smalltest;

import android.app.Application;

import net.wequick.small.Small;

public class App extends Application{
    public App() {
        Small.preSetUp(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Optional
        Small.setBaseUri("http://code.wequick.net/small-sample/");
        Small.setLoadFromAssets(BuildConfig.LOAD_FROM_ASSETS);
    }

}
