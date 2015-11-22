package yegor_gruk.example.com.rememberme.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;

import yegor_gruk.example.com.rememberme.Models.AdapterModel;
import yegor_gruk.example.com.rememberme.R;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> implements View.OnClickListener {

    private List<AdapterModel> models;

    private Context context;

    public RVAdapter(Context context, List<AdapterModel> models) {
        this.models = models;
        this.context = context;
    }

    public void setModels(List<AdapterModel> models) {
        this.models = models;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card_element, viewGroup, false);
        ViewHolder pvh = new ViewHolder(v);

        return pvh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // TODO создать нормальные OnClickListener()
        holder.id.setText(String.valueOf(models.get(position).getId()));
        holder.time.setText(models.get(position).getFormattedTime());
        holder.image.setImageResource(models.get(position).getImageId());

        final AdapterModel model = models.get(position);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    model.invertBool();

                } catch (SQLException e) {

                    Log.d("TAG", e.getMessage());

                    throw new RuntimeException();
                }

                holder.image.setImageResource(model.getImageId());
            }
        });

        //holder.cv.setBackgroundResource(model.getBackgroundColor());
        holder.cv.setCardBackgroundColor(context.getResources().getColor(model.getBackgroundColor()));

        holder.id.setTextColor(context.getResources().getColor(model.getTextColor()));
        holder.time.setTextColor(context.getResources().getColor(model.getTextColor()));

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onClick(View v) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;

        TextView id;
        TextView time;
        ImageView image;

        ViewHolder(View itemView) {
            super(itemView);

            cv = (CardView) itemView.findViewById(R.id.card_view);
            id = (TextView) itemView.findViewById(R.id.testID);
            time = (TextView) itemView.findViewById(R.id.testAlarmTime);
            image = (ImageView) itemView.findViewById(R.id.testImageButton);
        }
    }
}