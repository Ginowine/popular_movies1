package challenge.github.alc.com.popularmoveapp2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Gino Osahon on 22/06/2017.
 */
public class MovieDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
                MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieContract.MovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_IMAGE + " TEXT, " +
                MovieContract.MovieEntry.COLUMN_IMAGE2 + " TEXT, " +
                MovieContract.MovieEntry.COLUMN_OVERVIEW + " TEXT, " +
                MovieContract.MovieEntry.COLUMN_RATING + " INTEGER, " +
                MovieContract.MovieEntry.COLUMN_DATE + " TEXT);";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);


       final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + MovieContract.Favorites.TABLE_NAME + " (" +
                MovieContract.Favorites._ID                  + " INTEGER PRIMARY KEY, " +
                MovieContract.Favorites.FAVORITE_COLUMN_MOVIE_ID      + " INTEGER NOT NULL, " +
                MovieContract.Favorites.FAVORITE_COLUMN_IMAGE + " TEXT NOT NULL, " +
                MovieContract.Favorites.FAVORITE_COLUMN_OVERVIEW      + " TEXT NOT NULL, " +
                MovieContract.Favorites.FAVORITE_COLUMN_TITLE         + " TEXT NOT NULL, " +
                MovieContract.Favorites.FAVORITE_COLUMN_VOTE_AVERAGE  + " TEXT NOT NULL, " +
                MovieContract.Favorites.FAVORITE_COLUMN_DATE  + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
