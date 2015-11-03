package yegor_gruk.example.com.rememberme.Loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import yegor_gruk.example.com.rememberme.DataBase.DatabaseModel;
import yegor_gruk.example.com.rememberme.DataBase.HelperFactory;
import yegor_gruk.example.com.rememberme.Models.AdapterModel;

public class AsyncArrayLoader extends AsyncTaskLoader<List<AdapterModel>> {

    public static final String TAG = AsyncArrayLoader.class.getName();

    private int constructs;

    public AsyncArrayLoader(Context context, int constructs) {
        super(context);

        this.constructs = constructs;
    }


    @Override
    public List<AdapterModel> loadInBackground() {

        try {

            List<DatabaseModel> models = HelperFactory.getHelper().getModelDAO().getAllLastRecords(constructs);

            List<AdapterModel> adapterModels = new ArrayList<>(models.size());

            for (DatabaseModel model : models) {
                adapterModels.add(new AdapterModel(model));
            }

            return adapterModels;

        } catch (SQLException e) {

            Log.d(TAG, e.getMessage());

        }

        return null;
    }
}