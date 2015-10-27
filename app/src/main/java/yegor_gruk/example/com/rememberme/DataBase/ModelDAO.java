package yegor_gruk.example.com.rememberme.DataBase;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import yegor_gruk.example.com.rememberme.Utils.MyLogger;

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

        Where where = qb.where();
        // the name field must be equal to "foo"
        where.eq(DatabaseModel.NAME_FIELD_ID, " id > (SELECT MAX(id) - " + last + " FROM DatabaseModel)");

        return where.query();
    }

    @Deprecated
    public void inverseBool(long time) throws SQLException {

        DatabaseModel model = queryForId((int) getIdByTime(time));

        UpdateBuilder<DatabaseModel, Integer> updateBuilder = this.updateBuilder();
        // set the criteria like you would a QueryBuilder
        updateBuilder.where().eq(DatabaseModel.NAME_FIELD_ID, time);
        // update the value of your field

        if (model.isActive())
            updateBuilder.updateColumnValue(DatabaseModel.NAME_FIELD_ACTIVE, false);
        else
            updateBuilder.updateColumnValue(DatabaseModel.NAME_FIELD_ACTIVE, true);

        updateBuilder.update();
    }

    public long getIdByTime(long time) throws SQLException {

        QueryBuilder<DatabaseModel, Integer> qb = this.queryBuilder();

        Where where = qb.where();
        // the name field must be equal to "foo"
        where.eq(DatabaseModel.NAME_FIELD_TIME, time);

        DatabaseModel model = (DatabaseModel) where.query().get(0);

        MyLogger.log(where.query());

        MyLogger.log(model.getId());

        return model.getId();
    }


}
