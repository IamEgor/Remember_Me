package yegor_gruk.example.com.rememberme.Activities;

import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.rey.material.widget.FloatingActionButton;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import yegor_gruk.example.com.rememberme.Adapters.RVAdapter;
import yegor_gruk.example.com.rememberme.DataBase.HelperFactory;
import yegor_gruk.example.com.rememberme.DataBase.ModelDAO;
import yegor_gruk.example.com.rememberme.Loaders.AsyncArrayLoader;
import yegor_gruk.example.com.rememberme.Models.AdapterModel;
import yegor_gruk.example.com.rememberme.Prefs.AppPrefs;
import yegor_gruk.example.com.rememberme.R;
import yegor_gruk.example.com.rememberme.Util.Utilities;

public class ListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<AdapterModel>> {

    public static final String BROADCAST_ACTION = ListActivity.class.getName();

    public static final int RESULT_CODE = 1;
    private static final int URL_LOADER = 0;
    IntentFilter filter = new IntentFilter(BROADCAST_ACTION);
    MyBroadcast broadcast = new MyBroadcast();
    Button button;
    boolean test_test;
    SharedPreferences preferences;
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
        button = (Button) findViewById(R.id.setAlarmBtn);

        preferences = getSharedPreferences(AppPrefs.APP_PREFS, MODE_PRIVATE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, PrefsActivity.class);
                startActivityForResult(intent, RESULT_CODE);
            }
        });

        final FloatingActionButton fab_image = (FloatingActionButton) findViewById(R.id.fab_image);

        mDrawables[0] = getResources().getDrawable(R.drawable.ic_plus);
        mDrawables[1] = getResources().getDrawable(R.drawable.ic_cross);

        fab_image.setIcon(mDrawables[index], false);
        fab_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hideEmptyStateView(test_test);
                //test_test = !test_test;

                //index = (index + 1) % 2;
                //fab_image.setIcon(mDrawables[index], true);
            }
        });

        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        rvAdapter = new RVAdapter(this, new ArrayList<AdapterModel>());
        rv.setAdapter(rvAdapter);

        getLoaderManager().initLoader(URL_LOADER, null, this).forceLoad();//getIntent().getExtras()
    }

    @Override
    public Loader<List<AdapterModel>> onCreateLoader(int loaderID, Bundle bundle) {

        switch (loaderID) {

            case URL_LOADER:

                preferences = getSharedPreferences(AppPrefs.APP_PREFS, MODE_PRIVATE);
                int repsAmount = preferences.getInt(getString(R.string.reps_key), -1);

                Log.v("onCreateLoader", "repsAmount - " + repsAmount);

                return new AsyncArrayLoader(this, repsAmount);

            default:
                return null;

        }

    }

    @Override
    public void onLoadFinished(Loader<List<AdapterModel>> loader, List<AdapterModel> databaseModels) {

        rvAdapter.setModels(databaseModels);

        long current = Utilities.getCurrentTime();

        if (databaseModels.size() == 0 || databaseModels.get(databaseModels.size() - 1).getTime() < current) {
            hideEmptyStateView(false);
        } else
            hideEmptyStateView(true);

    }

    @Override
    public void onLoaderReset(Loader<List<AdapterModel>> loader) {
        rvAdapter.setModels(new ArrayList<AdapterModel>());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_switch);
        if (item != null) {

            SwitchCompat action_bar_switch = (SwitchCompat) item.getActionView().findViewById(R.id.action_switch);

            if (action_bar_switch != null) {

                action_bar_switch.setChecked(true);

                action_bar_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        int resId = isChecked ? R.string.enabled : R.string.disabled;

                        try {
                            ModelDAO dao = HelperFactory.getHelper().getModelDAO();
                            dao.setActiveLastRecords(isChecked);
                            //rvAdapter.notifyDataSetChanged();
                        } catch (SQLException e) {
                            throw new RuntimeException(e.getMessage());
                        }

                        Toast.makeText(ListActivity.this, getString(resId), Toast.LENGTH_SHORT).show();
                        rvAdapter.notifyDataSetChanged();
                    }
                });

            }
        }
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
            Intent intent = new Intent(ListActivity.this, GraphicActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_switch) {
            Toast.makeText(this, "action_switch", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        rvAdapter.notifyDataSetChanged();
        registerReceiver(broadcast, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcast);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RESULT_CODE) {

            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "RESULT_OK", Toast.LENGTH_LONG).show();
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "RESULT_CANCELED", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void hideEmptyStateView(boolean b) {
        //TODO or setContent visible
        if (b) {
            emptyView.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
        } else {
            rv.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }

    }

    class MyBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String extras = intent.getStringExtra(BROADCAST_ACTION);

            if (extras == null)
                throw new RuntimeException();

            if (extras.equals(Utilities.NOTIFY_DATA)) {
                rvAdapter.notifyDataSetChanged();
                Toast.makeText(ListActivity.this, "MyBroadcast notifyDataSetChanged", Toast.LENGTH_LONG).show();
            } else if (extras.equals(Utilities.CALL_LOADER)) {
                getLoaderManager().initLoader(URL_LOADER, getIntent().getExtras(), ListActivity.this).forceLoad();
                Toast.makeText(ListActivity.this, "MyBroadcast getLoaderManager", Toast.LENGTH_LONG).show();
            }

        }
    }

}
