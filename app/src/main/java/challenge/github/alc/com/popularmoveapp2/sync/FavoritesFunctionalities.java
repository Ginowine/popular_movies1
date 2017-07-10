package challenge.github.alc.com.popularmoveapp2.sync;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

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

    public void addToFavorites(Bundle bundle) {
        ContentValues contentValues = new ContentValues();
        //contentValues.put(MovieContract.Favorites._ID, bundle.getInt(Movie.MOVIE_ID));
        contentValues.put(MovieContract.Favorites.FAVORITE_COLUMN_MOVIE_ID, bundle.getInt(Movie.MOVIE_ID));
        contentValues.put(MovieContract.Favorites.FAVORITE_COLUMN_TITLE, bundle.getString(Movie.MOVIE_TITLE));
        contentValues.put(MovieContract.Favorites.FAVORITE_COLUMN_IMAGE, bundle.getString(Movie.POSTER_URL));
        contentValues.put(MovieContract.Favorites.FAVORITE_COLUMN_OVERVIEW, bundle.getString(Movie.MOVIE_OVERVIEW));
        contentValues.put(MovieContract.Favorites.FAVORITE_COLUMN_RATING, bundle.getDouble(Movie.MOVIE_RATING));
        contentValues.put(MovieContract.Favorites.FAVORITE_COLUMN_DATE, bundle.getString(Movie.MOVIE_RELEASE_DATE));
        context.getContentResolver().insert(MovieContract.Favorites.CONTENT_URI, contentValues);
    }

    public void removeFromFavorites(Bundle bundle) {
        context.getContentResolver().delete(
                MovieContract.Favorites.CONTENT_URI,
                MovieContract.Favorites.FAVORITE_COLUMN_MOVIE_ID + " = " + bundle.getString(Movie.MOVIE_ID),
                null
        );
    }

    public boolean isFavorite(Bundle bundle) {
        boolean favorite = false;
        Cursor cursor = context.getContentResolver().query(
                MovieContract.Favorites.CONTENT_URI,
                null,
                MovieContract.Favorites.FAVORITE_COLUMN_MOVIE_ID + " = " + bundle.getString(Movie.MOVIE_ID),
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
