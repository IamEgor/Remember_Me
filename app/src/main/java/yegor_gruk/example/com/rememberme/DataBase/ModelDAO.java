package yegor_gruk.example.com.rememberme.DataBase;

import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import yegor_gruk.example.com.rememberme.Util.MyLogger;
import yegor_gruk.example.com.rememberme.Util.Utilities;

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

        updateBuilder.where().eq(DatabaseModel.ID, id);

        if (model.isActive())
            updateBuilder.updateColumnValue(DatabaseModel.IS_ACTIVE, false);
        else
            updateBuilder.updateColumnValue(DatabaseModel.IS_ACTIVE, true);

        updateBuilder.update();
    }

    public List<DatabaseModel> getActiveLastRecords() throws SQLException {

        QueryBuilder<DatabaseModel, Integer> queryBuilder = this.queryBuilder();
        Where<DatabaseModel, Integer> where = queryBuilder.where();

        where.gt(DatabaseModel.TIME, Utilities.getCurrentTime());

        return queryBuilder.query();
    }

    public void setActiveLastRecords(boolean setActive) throws SQLException {

        UpdateBuilder<DatabaseModel, Integer> updateBuilder = this.updateBuilder();
        Where<DatabaseModel, Integer> where = updateBuilder.where();

        where.gt(DatabaseModel.TIME, Utilities.getCurrentTime());

        updateBuilder.updateColumnValue(DatabaseModel.IS_ACTIVE, setActive);

        Log.wtf("updateBuilder", updateBuilder.prepareStatementString());
        updateBuilder.update();
    }

    private QueryBuilder<DatabaseModel, Integer> getStatisticsQuery() throws SQLException {

        QueryBuilder<DatabaseModel, Integer> qb = this.queryBuilder();

        String alias = "dayOfYear";

        qb.selectRaw(
                "strftime('%d:%m', " + DatabaseModel.TIME + " / 1000, 'unixepoch') as " + alias,
                "count(*)",
                DatabaseModel.IS_ACTIVE,
                DatabaseModel.TIME
        );

        qb.groupByRaw(alias + " , " + DatabaseModel.IS_ACTIVE);
        qb.orderByRaw(alias);

        MyLogger.log(qb.prepareStatementString());

        return qb;
    }

    public GenericRawResults<String[]> getStatistics() throws SQLException {
        return getStatisticsQuery().queryRaw();
    }
    /*
    public GenericRawResults<String[]> getStatistics(boolean isActive) throws SQLException {
        //int equalTo = isActive ? 1 : 0;
        return getStatisticsQuery().where().eq(DatabaseModel.IS_ACTIVE, isActive).queryRaw();
    }
    */
}
