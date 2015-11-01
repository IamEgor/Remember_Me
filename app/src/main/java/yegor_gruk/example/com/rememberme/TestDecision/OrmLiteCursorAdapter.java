package yegor_gruk.example.com.rememberme.TestDecision;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;

import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.stmt.PreparedQuery;

import java.sql.SQLException;

/**
 * Created by Egor on 27.10.2015.
 */
public abstract class OrmLiteCursorAdapter<T> extends CursorAdapter {
    protected PreparedQuery<T> mQuery;

    public OrmLiteCursorAdapter(Context context, Cursor c, PreparedQuery<T> query) {
        super(context, c, false);
        mQuery = query;
    }

    @Override
    public void bindView(View itemView, Context context, Cursor cursor) {
        try {
            T item = mQuery.mapRow(new AndroidDatabaseResults(cursor, null));
            bindView(itemView, context, item);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void setQuery(PreparedQuery<T> query) {
        mQuery = query;
    }

    abstract public void bindView(View itemView, Context context, T item);
}
