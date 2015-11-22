package yegor_gruk.example.com.rememberme.DataBase;

import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import yegor_gruk.example.com.rememberme.Util.MyLogger;

/**
 * Created by Egor on 21.10.2015.
 */
public class ModelDAO extends BaseDaoImpl<DatabaseModel, Integer> {

    public ModelDAO(ConnectionSource connectionSource, Class<DatabaseModel> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<DatabaseModel> getAllRecords() throws SQLException {
        return this.queryForAll();
    }

    public List<DatabaseModel> getAllLastRecords(int last) throws SQLException {

        QueryBuilder<DatabaseModel, Integer> qb = this.queryBuilder();

        Where<DatabaseModel, Integer> where = qb.where();
        // the name field must be equal to "foo"
        where.gt(DatabaseModel.ID, "(SELECT MAX(" + DatabaseModel.ID +
                ") - " + last + " FROM " + DatabaseModel.TABLE_NAME + ")");

        Log.d("ModelDAO.getAllLastRec", "getStatement() : " + where.getStatement());

        return where.query();
    }

    @Deprecated
    public void inverseBool(int id, long time) throws SQLException {

        DatabaseModel model = queryForId(id);

        MyLogger.log(model);

        UpdateBuilder<DatabaseModel, Integer> updateBuilder = this.updateBuilder();
        // set the criteria like you would a QueryBuilder
        updateBuilder.where().eq(DatabaseModel.ID, id);
        // update the value of your field

        if (model.isActive())
            updateBuilder.updateColumnValue(DatabaseModel.IS_ACTIVE, false);
        else
            updateBuilder.updateColumnValue(DatabaseModel.IS_ACTIVE, true);

        updateBuilder.update();
    }

}
