package com.example.flixster;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.internal.g;
import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import Data.FavoritesContract;
import Data.FavoritesDbHelper;
import models.Movie;
import models.UMovie;
import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity{

    private static final String YOUTUBE_API_KEY = "AIzaSyABRZkYWmYwDzBW1SNIjbhGjt_mZWO1D90";
    public static final String VIDEOS_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    TextView tvTitle;
    TextView tvOverview;
    RatingBar ratingBar;
    YouTubePlayerView youTubePlayerView;
    Button btnFavorites;
    String txtBtnFavorites;
    Movie movie;
    private SQLiteDatabase mDatabase;
    int movieId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        ratingBar = findViewById(R.id.ratingBar);
        youTubePlayerView = findViewById(R.id.player);
        btnFavorites = findViewById(R.id.favbutton);

        final Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        ratingBar.setRating((float) movie.getRating());
        final FavoritesDbHelper dbHelper = new FavoritesDbHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        if (dbHelper.isInFavorites(movie.getMovieId())) {
            txtBtnFavorites = getResources().getString(R.string.remove_from_favorites);
        } else{
            txtBtnFavorites = getResources().getString(R.string.add_to_favorites);
        }
        btnFavorites.setText(txtBtnFavorites);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(VIDEOS_URL, movie.getMovieId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    if (results.length() == 0) {
                        return;
                    }
                    String youtubeKey = results.getJSONObject(0).getString("key");
                    Log.d("DetailActivity", youtubeKey);
                    intializeYoutube(youtubeKey);
                } catch (JSONException e) {
                    Log.e("DetailActivity", "Failed to parse JSON", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {

            }
        });

        // Set on click listener on favorite button
        btnFavorites.setOnClickListener(new View.OnClickListener() {
            int duration = Toast.LENGTH_LONG;

            public void onClick(View v) {
                int movieId = movie.getMovieId();
                String title = movie.getTitle();
                String posterPath = movie.getPosterPath();
                String overview = movie.getOverview();
                Double rating = movie.getRating();
//                dbHelper.addToFavorites(movieId,title,overview,rating,posterPath);
                if (dbHelper.isInFavorites(movieId) && dbHelper.removeFromFavorites(movieId)) {
                    Toast toast = Toast.makeText(getApplicationContext(),getResources().getString(R.string.favorites_removed_1)+" '" + movie.getTitle() + "' " + getResources().getString(R.string.favorites_removed_2), duration);
                    toast.show();
                    btnFavorites.setText(getResources().getString(R.string.add_to_favorites));
                } else if (dbHelper.addToFavorites(movieId,title,overview,rating,posterPath)) {
                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.favorites_added_1)+" '" + movie.getTitle() + "' "+getResources().getString(R.string.favorites_added_2), duration);
                    toast.show();
                    btnFavorites.setText(getResources().getString(R.string.remove_from_favorites));
                }
            }
        });
    }
    private void intializeYoutube(final String youtubeKey) {
        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("DetailActivity","onitializationSuccess");
                youTubePlayer.cueVideo(youtubeKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("DetailActivity","onInitializationFailure");

            }
        });
    }
}