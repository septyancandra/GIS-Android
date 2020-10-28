package co.id.gmedia.octavian.mapstelkommarker.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

import co.id.gmedia.octavian.mapstelkommarker.MapsActivity;
import co.id.gmedia.octavian.mapstelkommarker.R;
import co.id.gmedia.octavian.mapstelkommarker.model.ModelData;
import co.id.gmedia.octavian.mapstelkommarker.server.Server;


//import com.squareup.picasso.Picasso;

//import co.id.gmedia.pullens.R;

public class TemplateAdaptorData extends RecyclerView.Adapter<TemplateAdaptorData.TemplateViewHolder> {

    private Activity activity;
    private List<ModelData> listItemmenu;

    public TemplateAdaptorData(Activity activity, List<ModelData> listItemmenu){
        this.activity = activity;
        this.listItemmenu = listItemmenu;
    }

    @NonNull
    @Override
    public TemplateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TemplateViewHolder(LayoutInflater.from(activity).
                inflate(R.layout.avtivity_adapter_data, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TemplateViewHolder templateViewHolder, int i) {
        final ModelData item = listItemmenu.get(i);
        final int final_position = i;
        templateViewHolder.id_daerah.setText(item.getItem1());
        templateViewHolder.radius.setText(item.getItem5());
        templateViewHolder.nama.setText(item.getItem2());
        templateViewHolder.alamat.setText(item.getItem6());
        //Picasso.get().load(item.getItem2()).into(templateViewHolder.iv_cardview);

        final Gson gson = new Gson();
        templateViewHolder.iv_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, MapsActivity.class);
                i.putExtra(Server.EXTRA_DAERAH, gson.toJson(item));
                activity.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return listItemmenu.size();
    }

    class TemplateViewHolder extends RecyclerView.ViewHolder{
        private CardView iv_cardview;
        private TextView nama, alamat, id_daerah, radius;

        public TemplateViewHolder(@NonNull View itemView) {
            super(itemView);
           nama = (TextView) itemView.findViewById(R.id.nama);
           alamat = (TextView) itemView.findViewById(R.id.alamat);
           id_daerah = (TextView) itemView.findViewById(R.id.id_daerah);
           radius = (TextView) itemView.findViewById(R.id.radius);
           iv_cardview = (CardView) itemView.findViewById(R.id.cv_item);
        }
    }
}
