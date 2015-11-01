package yegor_gruk.example.com.rememberme.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import yegor_gruk.example.com.rememberme.DataBase.DatabaseModel;
import yegor_gruk.example.com.rememberme.R;

/**
 * Created by Egor on 27.10.2015.
 */
public class NewCursorAdapter extends CursorAdapter {

    private LayoutInflater cursorInflater;
    private Context context;


    public NewCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);

        this.context = context;

        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(R.layout.alarms_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView tvBody = (TextView) view.findViewById(R.id.newTextView);
        // Extract properties from cursor
        String body = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseModel.ID));
        // Populate fields with extracted properties
        tvBody.setText(body);
    }
}
