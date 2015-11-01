package yegor_gruk.example.com.rememberme.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import yegor_gruk.example.com.rememberme.DataBase.DatabaseModel;
import yegor_gruk.example.com.rememberme.R;

public class NewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<DatabaseModel> models;

    public NewAdapter(Context context, List<DatabaseModel> models) {
        this.models = models;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setModels(List<DatabaseModel> models) {
        this.models = models;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        View view = convertView;

        if (view == null) {
            view = inflater.inflate(R.layout.alarms_list_item, parent, false);

            holder = new ViewHolder();

            holder.id = (TextView) view.findViewById(R.id.newID);
            holder.time = (TextView) view.findViewById(R.id.newTextView);
            holder.image = (ImageView) view.findViewById(R.id.newImageButton);

            view.setTag(holder);

        } else {

            holder = (ViewHolder) view.getTag();

        }

        DatabaseModel model = models.get(position);

        holder.id.setText(String.valueOf(model.getId()));
        holder.time.setText(String.valueOf(model.getRepTime()));

        return view;


    }

    static class ViewHolder {
        public TextView id;
        public TextView time;
        public ImageView image;

    }
}