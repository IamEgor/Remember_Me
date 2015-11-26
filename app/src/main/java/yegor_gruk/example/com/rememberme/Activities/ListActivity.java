package yegor_gruk.example.com.rememberme.Activities;

import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.rey.material.widget.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import yegor_gruk.example.com.rememberme.Adapters.RVAdapter;
import yegor_gruk.example.com.rememberme.BroadcastReceivers.AlarmReceiver;
import yegor_gruk.example.com.rememberme.Loaders.AsyncArrayLoader;
import yegor_gruk.example.com.rememberme.Models.AdapterModel;
import yegor_gruk.example.com.rememberme.R;
import yegor_gruk.example.com.rememberme.Util.Utilities;

public class ListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<AdapterModel>> {

    public static final String TAG = ListActivity.class.getName();

    private static final int URL_LOADER = 0;
    IntentFilter filter = new IntentFilter(AlarmReceiver.STRING);
    MyBroadcast broadcast = new MyBroadcast();
    private RVAdapter rvAdapter;
    private RecyclerView rv;
    private View emptyView;
    private Drawable[] mDrawables = new Drawable[2];
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // TODO Вынести в values-v21 и values-v19
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary_dark));

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }

        rv = (RecyclerView) findViewById(R.id.rv);
        emptyView = findViewById(R.id.empty_recycle);

        final FloatingActionButton fab_image = (FloatingActionButton) findViewById(R.id.fab_image);
        mDrawables[0] = getResources().getDrawable(R.drawable.ic_plus);
        mDrawables[1] = getResources().getDrawable(R.drawable.ic_cross);
        fab_image.setIcon(mDrawables[index], false);
        fab_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, PrefsActivity.class);
                startActivity(intent);
                //index = (index + 1) % 2;
                //fab_image.setIcon(mDrawables[index], true);
            }
        });

        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        rvAdapter = new RVAdapter(this, new ArrayList<AdapterModel>());
        rv.setAdapter(rvAdapter);

        getLoaderManager().initLoader(URL_LOADER, getIntent().getExtras(), this).forceLoad();
    }

    @Override
    public Loader<List<AdapterModel>> onCreateLoader(int loaderID, Bundle bundle) {

        //Log.d(TAG, "||onCreateLoader()|| - " + bundle.getInt(MainActivityPrefs.NUMBER_OF_INTERVALS));

        switch (loaderID) {

            case URL_LOADER:
                return new AsyncArrayLoader(this, 6);//bundle.getInt(MainActivityPrefs.NUMBER_OF_INTERVALS));

            default:
                return null;

        }

    }

    @Override
    public void onLoadFinished(Loader<List<AdapterModel>> loader, List<AdapterModel> databaseModels) {

        long current = Utilities.getCurrentTime();
        Log.w("onLoadFinished", "databaseModels.get(databaseModels.size() - 1).getId() "
                + databaseModels.get(databaseModels.size() - 1).getTime()
                + "          [databaseModels.size() - 1] " + (databaseModels.size() - 1));
        if (databaseModels.size() == 0 || databaseModels.get(databaseModels.size() - 1).getTime() < current) {
            rv.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else
            rvAdapter.setModels(databaseModels);
    }

    @Override
    public void onLoaderReset(Loader<List<AdapterModel>> loader) {
        rvAdapter.setModels(new ArrayList<AdapterModel>());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "action_settings", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.action_list) {
            Toast.makeText(this, "action_list", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcast, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcast);
    }

    class MyBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(ListActivity.this, "MyBroadcast notifyDataSetChanged", Toast.LENGTH_LONG).show();
            rvAdapter.notifyDataSetChanged();
        }
    }

}
