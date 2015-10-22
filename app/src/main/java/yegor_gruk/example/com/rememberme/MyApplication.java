package yegor_gruk.example.com.rememberme;

import android.app.Application;

import yegor_gruk.example.com.rememberme.DataBase.HelperFactory;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HelperFactory.setHelper(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        HelperFactory.releaseHelper();
        super.onTerminate();
    }

}
