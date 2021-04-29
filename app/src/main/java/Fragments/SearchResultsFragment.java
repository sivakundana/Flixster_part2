package Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapters.MovieAdapter;
import adapters.MovieSearchResultsAdapter;
import models.Movie;
import okhttp3.Headers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultsFragment extends Fragment {

    public static final String TAG = "SearchResultsFragment";
    protected MovieSearchResultsAdapter movieAdapter;
    private RecyclerView rvMovies;
    private TextView resultsTitle;
    private TextView srErrorMessage;
    private ProgressBar pbLoadingIndicator;

    protected List<Movie> movies;
    public String searchQuery;
    public String URL = "https://api.themoviedb.org/3/search/movie?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US&page=1&include_adult=false&query=";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchResultsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchResultsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchResultsFragment newInstance(String param1, String param2) {
        SearchResultsFragment fragment = new SearchResultsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        searchQuery = getArguments().getString("SearchQuery");
        return inflater.inflate(R.layout.fragment_search_results, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMovies = view.findViewById(R.id.rvMovies);
        resultsTitle = view.findViewById(R.id.rvSearchResultText);
        srErrorMessage = view.findViewById(R.id.srErrorMessage);
        //pbLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        movies = new ArrayList<>();
        movieAdapter = new MovieSearchResultsAdapter(getContext(), movies);
        rvMovies.setAdapter(movieAdapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));


        AsyncHttpClient client = new AsyncHttpClient();
        URL = URL + searchQuery;
        client.get(URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG,"onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG,"Results: "+ results.toString());
                    movies.addAll(Movie.fromJsonArray(results));
                    if(!movies.isEmpty()) {
                        resultsTitle.setText(movies.size() + " " + getResources().getString(R.string.success_results_heading) + " '" + searchQuery + "'");
                    }else
                        resultsTitle.setText(getResources().getString(R.string.search_error_message1)+ " '" + searchQuery + "'");
                    rvMovies.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 3));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG,"Movies" + movies.size());
                } catch (JSONException e) {
                    srErrorMessage.setText(getResources().getString(R.string.search_error_message2));
                    Log.e(TAG,"Hit json exception",e);
                }

            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG,"onFailure");
            }
        });
    }
}