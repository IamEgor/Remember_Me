package yegor_gruk.example.com.rememberme.TEST_TEST;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.sql.SQLException;
import java.util.List;

import yegor_gruk.example.com.rememberme.DataBase.DatabaseModel;
import yegor_gruk.example.com.rememberme.DataBase.HelperFactory;

public class MyLoader extends AsyncTaskLoader<List<DatabaseModel>> {

    public static final String TAG = MyLoader.class.getName();

    private int constructs;

    public MyLoader(Context context, int constructs) {
        super(context);

        this.constructs = constructs;
    }


    @Override
    public List<DatabaseModel> loadInBackground() {

        Log.d(TAG, "############called loadInBackground()");

        try {

            List<DatabaseModel> models = HelperFactory.getHelper().getModelDAO().getAllLastRecords(constructs);

            return models;

        } catch (SQLException e) {

            Log.d(TAG, e.getMessage());

        }

        return null;
    }
}