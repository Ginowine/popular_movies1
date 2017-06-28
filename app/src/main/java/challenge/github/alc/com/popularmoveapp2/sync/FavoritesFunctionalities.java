package challenge.github.alc.com.popularmoveapp2.sync;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import challenge.github.alc.com.popularmoveapp2.data.MovieContract;
import challenge.github.alc.com.popularmoveapp2.model.Movie;

/**
 * Created by Gino Osahon on 27/06/2017.
 */
public class FavoritesFunctionalities {

    private final Context context;

    public FavoritesFunctionalities(Context context) {
        this.context = context.getApplicationContext();
    }

    public void addToFavorites(Movie movie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.Favorites.FAVORITE_COLUMN_MOVIE_ID, movie.getId());
        contentValues.put(MovieContract.Favorites.FAVORITE_COLUMN_TITLE, movie.getTitle());
        contentValues.put(MovieContract.Favorites.FAVORITE_COLUMN_IMAGE, movie.getBackdropPath());
        contentValues.put(MovieContract.Favorites.FAVORITE_COLUMN_OVERVIEW, movie.getOverview());
        contentValues.put(MovieContract.Favorites.FAVORITE_COLUMN_RATING, movie.getRating());
        contentValues.put(MovieContract.Favorites.FAVORITE_COLUMN_DATE, movie.getReleaseDate());
        context.getContentResolver().insert(MovieContract.Favorites.CONTENT_URI, contentValues);
    }

    public void removeFromFavorites(Movie movie) {
        context.getContentResolver().delete(
                MovieContract.Favorites.CONTENT_URI,
                MovieContract.Favorites.FAVORITE_COLUMN_MOVIE_ID + " = " + movie.getId(),
                null
        );
    }

    public boolean isFavorite(Movie movie) {
        boolean favorite = false;
        Cursor cursor = context.getContentResolver().query(
                MovieContract.Favorites.CONTENT_URI,
                null,
                MovieContract.Favorites.FAVORITE_COLUMN_MOVIE_ID + " = " + movie.getId(),
                null,
                null
        );
        if (cursor != null) {
            favorite = cursor.getCount() != 0;
            cursor.close();

        }
        return favorite;
    }
}
