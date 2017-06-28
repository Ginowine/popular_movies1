package challenge.github.alc.com.popularmoveapp2.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Gino Osahon on 25/06/2017.
 */
public class MovieContentProvider extends ContentProvider {
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDbHelper mMovieDbHelper;

    public static final int MOVIES = 100;
    public static final int FAVORITE = 200;

    public static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovieContract.PATH_MOVIE, MOVIES);
        matcher.addURI(authority, MovieContract.PATH_FAVORITES,FAVORITE );

        return matcher;
    }

    @Override
    public boolean onCreate() {
        Context mContext = getContext();
        mMovieDbHelper = new MovieDbHelper(mContext);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {

        Cursor cursor;

        switch (sUriMatcher.match(uri)){
            case FAVORITE:{
                cursor = mMovieDbHelper.getReadableDatabase().query(MovieContract.Favorites.TABLE_NAME,
                        strings, s, strings1, s1, null, null);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        final SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();
        Uri returnUri;

        switch (sUriMatcher.match(uri)){
            case FAVORITE: {
                long _id = db.insert(MovieContract.Favorites.TABLE_NAME, null, contentValues);
                if (_id > 0){
                    returnUri = MovieContract.Favorites.CONTENT_URI;
                            //MovieContract.MovieEntry.buildMovieUri(_id);
                }else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        final SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();
        int moviesDeleted;

        switch (sUriMatcher.match(uri)){
            case FAVORITE: {
                moviesDeleted = db.delete(MovieContract.Favorites.TABLE_NAME, s, strings);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (moviesDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return moviesDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
