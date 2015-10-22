package yegor_gruk.example.com.rememberme.DataBase;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

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
}
