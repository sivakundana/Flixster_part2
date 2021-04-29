package models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class UMovie {


    int movieId;
    String backdropPath;
    String posterPath;
    String title;
    String overview;
    double rating;
    String releaseDate;



    //empty constructor needed by the Parceler library
    public UMovie() {}
    public UMovie(JSONObject jsonObject) throws JSONException {
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        rating = jsonObject.getDouble("vote_average");
        movieId = jsonObject.getInt("id");
        releaseDate = jsonObject.getString("release_date");
    }
    public static List<UMovie> fromJsonArray(JSONArray umovieJsonArray) throws JSONException {
        List<UMovie> umovies = new ArrayList<>();
        for(int i = 0; i < umovieJsonArray.length(); i++) {
            umovies.add(new UMovie(umovieJsonArray.getJSONObject(i)));
        }
        return umovies;
    }

    public String getPosterPath() {
        return  String.format("https://image.tmdb.org/t/p/w342/%s",posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getRating() {
        return rating;
    }

    public int getMovieId() {
        return movieId;
    }
    public String getReleaseDate(){
        return releaseDate;
    }

}



