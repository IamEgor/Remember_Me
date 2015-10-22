package yegor_gruk.example.com.rememberme;

import android.app.Application;

import yegor_gruk.example.com.rememberme.DataBase.HelperFactory;

/**
 * Created by Egor on 21.10.2015.
 */
public class MyApplicaton extends Application {

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
