package adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.DetailActivity;
import com.example.flixster.R;

import org.parceler.Parcels;

import java.util.List;

import models.Movie;

public class MovieSearchResultsAdapter extends RecyclerView.Adapter<MovieSearchResultsAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MovieSearchResultsAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }
    //Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieSRAdapter","onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_smovie, parent,false);
        return new ViewHolder(movieView);
    }
    //Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieSRAdapter","onBindViewHolder"+position);
        //Get the movie at the position
        Movie movie = movies.get(position);
        //Bind the movie data into the view holder
        holder.bind(movie);

    }
    //Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        TextView rvSearchResultText;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            rvSearchResultText = itemView.findViewById(R.id.rvSearchResultText);
            container = itemView.findViewById(R.id.container);

        }

        public void bind(final Movie movie) {
            Glide.with(context).load(movie.getPosterPath()).into(ivPoster);
            //Register click listener on the whole row
            //Naviagte to a new activity on tap.
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie",Parcels.wrap(movie));
                    context.startActivity(i);
                }
            });
        }
    }
}
