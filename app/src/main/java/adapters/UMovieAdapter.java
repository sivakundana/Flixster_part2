package adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.DetailActivity;
import com.example.flixster.R;
import com.example.flixster.UDetailActivity;

import org.parceler.Parcels;

import java.util.List;

import models.Movie;
import models.UMovie;

public class UMovieAdapter extends RecyclerView.Adapter<UMovieAdapter.ViewHolder> {

    Context context;
    List<UMovie> umovies;

    public UMovieAdapter(Context context, List<UMovie> umovies) {
        this.context = context;
        this.umovies = umovies;
    }
    //Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("UMovieAdapter","onCreateViewHolder");
        View umovieView = LayoutInflater.from(context).inflate(R.layout.item_umovie, parent,false);
        return new ViewHolder(umovieView);
    }
    //Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("UMovieAdapter","onBindViewHolder"+position);
        //Get the movie at the position
        UMovie umovie = umovies.get(position);
        //Bind the movie data into the view holder
        holder.bind(umovie);

    }
    //Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return umovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout ucontainer;
        TextView tuTitle;
        TextView tuOverview;
        ImageView iuPoster;
        TextView tvDate;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tuTitle = itemView.findViewById(R.id.tuTitle);
            tuOverview = itemView.findViewById(R.id.tuOverview);
            iuPoster = itemView.findViewById(R.id.iuPoster);
            ucontainer = itemView.findViewById(R.id.ucontainer);
            tvDate = itemView.findViewById(R.id.tvDate);

        }

        public void bind(final UMovie umovie) {
            tuTitle.setText(umovie.getTitle());
            tuOverview.setText(umovie.getOverview());
            tvDate.setText(umovie.getReleaseDate());
            Glide.with(context).load(umovie.getPosterPath()).into(iuPoster);
            //Register click listener on the whole row
            //Naviagte to a new activity on tap.
            ucontainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, UDetailActivity.class);
                    i.putExtra("umovie",Parcels.wrap(umovie));
                    context.startActivity(i);


                }
            });
        }
    }
}
