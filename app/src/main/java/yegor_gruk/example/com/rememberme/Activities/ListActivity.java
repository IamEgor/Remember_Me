package yegor_gruk.example.com.rememberme.Activities;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import yegor_gruk.example.com.rememberme.Adapters.NewAdapter;
import yegor_gruk.example.com.rememberme.DataBase.DatabaseModel;
import yegor_gruk.example.com.rememberme.Prefs.MainActivityPrefs;
import yegor_gruk.example.com.rememberme.R;
import yegor_gruk.example.com.rememberme.TEST_TEST.MyLoader;

public class ListActivity extends Activity implements LoaderManager.LoaderCallbacks<List<DatabaseModel>> {

    public static final String TAG = ListActivity.class.getName();

    private static final int URL_LOADER = 0;

    NewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarms_list);

        ListView listView = (ListView) findViewById(R.id.listView);

        adapter = new NewAdapter(this, new ArrayList<DatabaseModel>());// or null

        listView.setAdapter(adapter);

        getLoaderManager().initLoader(URL_LOADER, getIntent().getExtras(), this).forceLoad();
    }

    @Override
    public Loader<List<DatabaseModel>> onCreateLoader(int loaderID, Bundle bundle) {

        Log.d(TAG, "||onCreateLoader()|| - " + bundle.getInt(MainActivityPrefs.NUMBER_OF_INTERVALS));

        switch (loaderID) {

            case URL_LOADER:
                return new MyLoader(this, bundle.getInt(MainActivityPrefs.NUMBER_OF_INTERVALS));

            default:
                return null;

        }

    }

    @Override
    public void onLoadFinished(Loader<List<DatabaseModel>> loader, List<DatabaseModel> databaseModels) {
        adapter.setModels(databaseModels);
    }

    @Override
    public void onLoaderReset(Loader<List<DatabaseModel>> loader) {
        adapter.setModels(new ArrayList<DatabaseModel>());
    }
}
