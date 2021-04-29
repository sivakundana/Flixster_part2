package Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.flixster.R;

import Data.FavoritesDbHelper;
import adapters.FavoritesMovieAdapter;
import adapters.MovieAdapter;
import models.Movie;
import okhttp3.Headers;


public class FavoritesFragment extends Fragment {

    public static final String TAG = "FavFragment";
    protected FavoritesMovieAdapter movieAdapter;
    private RecyclerView rvMovies;
    private TextView favMsg;
    protected List<Movie> movies;
    private FavoritesDbHelper dbHelper;
    private SQLiteDatabase mDatabase;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbHelper = new FavoritesDbHelper(getActivity().getApplicationContext());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        rvMovies = view.findViewById(R.id.rvMovies);
        favMsg = view.findViewById(R.id.favMsg);
        movies = new ArrayList<>();
        Cursor cursorFavorites = dbHelper.getFavorites();
        initMovies(cursorFavorites);
        if(movies.isEmpty()){
            favMsg.setText(getResources().getString(R.string.no_fav_movies_msg));
        }
        movieAdapter = new FavoritesMovieAdapter(getContext(), movies);
        rvMovies.setAdapter(movieAdapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
        Log.i(TAG,"Movies" + movies.size());
    }

    /**
     * Reads the query result and initialize favorite movies into an array.
     *
     * @param cursorMovies
     */
    private void initMovies(Cursor cursorMovies) {
        try {
            movies = new ArrayList<>();
            while (cursorMovies.moveToNext()) {
                int movieId = cursorMovies.getInt(cursorMovies.getColumnIndex("movieId"));
                String title = cursorMovies.getString(cursorMovies.getColumnIndex("title"));
                String overview = cursorMovies.getString(cursorMovies.getColumnIndex("overview"));
                String posterPath = cursorMovies.getString(cursorMovies.getColumnIndex("posterPath"));
                Double rating = cursorMovies.getDouble(cursorMovies.getColumnIndex("rating"));
                movies.add(new Movie(movieId, "", posterPath,title,overview,rating));
            }
        } finally {
            cursorMovies.close();
        }
    }
}
