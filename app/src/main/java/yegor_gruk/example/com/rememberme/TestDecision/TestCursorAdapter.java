package yegor_gruk.example.com.rememberme.TestDecision;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.j256.ormlite.stmt.PreparedQuery;

import yegor_gruk.example.com.rememberme.DataBase.DatabaseModel;
import yegor_gruk.example.com.rememberme.R;

/**
 * Created by Egor on 27.10.2015.
 */
public class TestCursorAdapter extends OrmLiteCursorAdapter<DatabaseModel> {

    public TestCursorAdapter(Context context, Cursor c, PreparedQuery<DatabaseModel> query) {
        super(context, c, query);
    }

    @Override
    public void bindView(View itemView, Context context, DatabaseModel item) {

        ViewHolder viewHolder;

        if (null == itemView.getTag()) {

            viewHolder = new ViewHolder();

            viewHolder.idTextView = (TextView) itemView.findViewById(R.id.newID);
            viewHolder.timeTextView = (TextView) itemView.findViewById(R.id.newTextView);

            itemView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) itemView.getTag();
        }


        viewHolder.idTextView.setText(String.valueOf(item.getId()));
        viewHolder.timeTextView.setText(String.valueOf(item.getRepTime()));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //return cursorInflater.inflate(R.layout.alarms_list_item, parent, false);
        return LayoutInflater.from(context).inflate(R.layout.alarms_list_item, parent, false);
    }

    static class ViewHolder {

        public TextView idTextView;
        public TextView timeTextView;

    }
}