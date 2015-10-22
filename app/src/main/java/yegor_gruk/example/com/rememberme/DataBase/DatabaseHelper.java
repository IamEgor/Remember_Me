package yegor_gruk.example.com.rememberme.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import yegor_gruk.example.com.rememberme.R;

/**
 * Created by Egor on 21.10.2015.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "UNIT_MODEL.db";
    private static final int DATABASE_VERSION = 1;

    private ModelDAO modelDAO = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION,
                /**
                 * R.raw.ormlite_config is a reference to the ormlite_config.txt file in the
                 * /res/raw/ directory of this project
                 * */
                R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {

            /**
             * creates the DatabaseModel database table
             */

            TableUtils.createTable(connectionSource, DatabaseModel.class);
        } catch (SQLException e) {

            Log.e(TAG, "error creating DB " + DATABASE_NAME);

            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try {

            /**
             * Recreates the database when onUpgrade is called by the framework
             */

            TableUtils.dropTable(connectionSource, DatabaseModel.class, true);

            onCreate(database, connectionSource);

        } catch (SQLException e) {

            Log.e(TAG, "error upgrading db " + DATABASE_NAME + "from version " + oldVersion);

            throw new RuntimeException(e);

        }

    }

    public ModelDAO getModelDAO() throws SQLException {

        if (modelDAO == null) {
            modelDAO = new ModelDAO(getConnectionSource(), DatabaseModel.class);
        }

        return modelDAO;
    }

    @Override
    public void close() {
        super.close();
        modelDAO = null;
    }

}
