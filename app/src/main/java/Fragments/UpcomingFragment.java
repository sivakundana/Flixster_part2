package Fragments;

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

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.flixster.R;

import java.util.ArrayList;
import java.util.List;

import adapters.MovieAdapter;
import adapters.UMovieAdapter;
import models.Movie;
import models.UMovie;
import okhttp3.Headers;



public class UpcomingFragment extends Fragment {

    public static final String TAG = "UpcomingFragment";
    protected adapters.UMovieAdapter umovieAdapter;
    private RecyclerView rvMovies;
    private TextView movieErrorMsg;
    protected List<UMovie> umovies;
    public static final String Upcoming_URL = "https://api.themoviedb.org/3/movie/upcoming?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US&region=US&with_release_type=2|3";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMovies = view.findViewById(R.id.rvMovies);
        movieErrorMsg = view.findViewById(R.id.movieError);
        umovies = new ArrayList<>();
        umovieAdapter = new UMovieAdapter(getContext(), umovies);
        rvMovies.setAdapter(umovieAdapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Upcoming_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG,"onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG,"Results: "+ results.toString());
                    umovies.addAll(UMovie.fromJsonArray(results));
                    umovieAdapter.notifyDataSetChanged();
                    Log.i(TAG,"Movies" + umovies.size());
                } catch (JSONException e) {
                    movieErrorMsg.setText(getResources().getString(R.string.movie_error_msg));
                    Log.e(TAG,"Hit json exception",e);
                }

            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                movieErrorMsg.setText(getResources().getString(R.string.movie_error_msg));
                Log.d(TAG,"onFailure");

            }
        });
    }
}
