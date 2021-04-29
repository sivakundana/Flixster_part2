package Data;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.flixster.DetailActivity;

import Data.FavoritesContract.*;


public class FavoritesDbHelper extends SQLiteOpenHelper {

    // The database name
    private static final String DATABASE_NAME = "favorites.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 1;
    // Constructor
    public FavoritesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a table to hold favorites data
        final String SQL_CREATE_FAVORITES_TABLE = "CREATE TABLE " + FavoritesEntry.TABLE_NAME + " (" +
                FavoritesEntry.COLUMN_MOVIE_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                FavoritesEntry.COLUMN_POSTER_PATH + " STRING, " +
                FavoritesEntry.COLUMN_TITLE + " STRING, " +
                FavoritesEntry.COLUMN_OVERVIEW + " STRING, " +
                FavoritesEntry.COLUMN_RATING + " DOUBLE, " +
                FavoritesEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one. This means if you change the
        // DATABASE_VERSION the table will be dropped.
        // In a production app, this method might be modified to ALTER the table
        // instead of dropping it, so that existing data is not deleted.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoritesEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public final Boolean isInFavorites(int movieId) {
        SQLiteDatabase Db1=getReadableDatabase();
        Cursor mCursor = Db1.rawQuery(
                "SELECT * FROM " + FavoritesContract.FavoritesEntry.TABLE_NAME +
                        " WHERE " + FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID + "= " + movieId
                , null);

        return mCursor.moveToFirst();
    }

    public final Boolean addToFavorites(int movieId,String title,String overview, Double rating, String posterPath) {
        ContentValues values = new ContentValues();
        SQLiteDatabase Db1=getReadableDatabase();
        // add values to record keys
        values.put(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID, movieId);
        values.put(FavoritesContract.FavoritesEntry.COLUMN_POSTER_PATH, posterPath);
        values.put(FavoritesContract.FavoritesEntry.COLUMN_TITLE, title);
        values.put(FavoritesContract.FavoritesEntry.COLUMN_OVERVIEW, overview);
        values.put(FavoritesContract.FavoritesEntry.COLUMN_RATING, rating);
        return Db1.insert(FavoritesContract.FavoritesEntry.TABLE_NAME, null, values) > 0;
    }

    public Boolean removeFromFavorites(int movieId) {
        SQLiteDatabase Db1=getReadableDatabase();
        return Db1.delete(FavoritesContract.FavoritesEntry.TABLE_NAME, FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID + " = " + movieId, null) > 0;
    }

    /**
     * Get the favorites movies from de local database
     *
     * @return
     */
    public Cursor getFavorites() {
        SQLiteDatabase Db1=getReadableDatabase();
        return Db1.query(
                FavoritesContract.FavoritesEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                FavoritesContract.FavoritesEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }


}
