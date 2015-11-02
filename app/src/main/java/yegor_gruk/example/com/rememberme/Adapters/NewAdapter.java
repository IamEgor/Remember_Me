package yegor_gruk.example.com.rememberme.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;

import yegor_gruk.example.com.rememberme.Models.AdapterModel;
import yegor_gruk.example.com.rememberme.R;

public class NewAdapter extends BaseAdapter {

    public static final String TAG = NewAdapter.class.getName();

    private LayoutInflater inflater;
    private List<AdapterModel> models;

    public NewAdapter(Context context, List<AdapterModel> models) {
        this.models = models;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setModels(List<AdapterModel> models) {
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

        final ViewHolder holder;

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

        final AdapterModel model = models.get(position);
        //final AdapterModel adapterModel = new AdapterModel(model);

        holder.id.setText(String.valueOf(model.getId()));
        holder.time.setText(model.getFormattedTime());


        holder.image.setImageResource(model.getImageId());

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    model.invertBool();

                } catch (SQLException e) {

                    Log.d(TAG, e.getMessage());

                    throw new RuntimeException();
                }

                holder.image.setImageResource(model.getImageId());
            }
        });


        return view;
    }

    static class ViewHolder {
        public TextView id;
        public TextView time;
        public ImageView image;
    }
}