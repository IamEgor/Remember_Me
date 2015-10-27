package yegor_gruk.example.com.rememberme.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;

import yegor_gruk.example.com.rememberme.DataBase.HelperFactory;
import yegor_gruk.example.com.rememberme.DataBase.ModelDAO;
import yegor_gruk.example.com.rememberme.Models.AdapterModel;
import yegor_gruk.example.com.rememberme.R;
import yegor_gruk.example.com.rememberme.Utils.Utilities;

/**
 * Created by Egor on 18.10.2015.
 */
public class NewAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<AdapterModel> objects;

    public NewAdapter(Context context, ArrayList<AdapterModel> objects) {
        this.context = context;
        this.objects = objects;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final View view;
        if (convertView == null)
            view = inflater.inflate(R.layout.alarms_list_item, parent, false);
        else
            view = convertView;

        final AdapterModel model = (AdapterModel) getItem(position);

        final TextView textView = (TextView) view.findViewById(R.id.newTextView);
        final ImageButton imageButton = (ImageButton) view.findViewById(R.id.newImageButton);

        textView.setText(model.getFormatedTime());

        imageButton.setBackgroundResource(model.getImageId());


        imageButton.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                //Toast.makeText(context, model.getLabel(), Toast.LENGTH_SHORT).show();
                Utilities.makeToast(imageButton, context, model.getLabel());


                //Utilities.makeToast2(imageButton,context, model.getLabel());
                return true;
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    ModelDAO dao = HelperFactory.getHelper().getModelDAO();
                    dao.inverseBool(model.getTimeMils());//updateBool(model.getTimeMils());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                model.invertBool();
                imageButton.setBackgroundResource(model.getImageId());
            }
        });

        return view;


    }
}
