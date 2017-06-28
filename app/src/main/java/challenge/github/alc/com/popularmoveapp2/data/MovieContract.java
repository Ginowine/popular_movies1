package challenge.github.alc.com.popularmoveapp2.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Gino Osahon on 22/06/2017.
 */
public class MovieContract {

    public static final String CONTENT_AUTHORITY = "challenge.github.alc.com.popularmoveapp2";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIE = "movie";
    public static final String PATH_FAVORITES = "favorites";

    public static final class MovieEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_IMAGE2 = "image2";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_DATE = "date";

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class Favorites implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();

//
//                MovieEntry.CONTENT_URI.buildUpon()
//                .appendPath(PATH_FAVORITES)
//                .build();
        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE
                        + "/" + PATH_FAVORITES;

        public static final String TABLE_NAME = "favorites";
        public static  final String _ID = "id";
        public  static  final  String FAVORITE_COLUMN_MOVIE_ID_KEY = "movie_key";

        public static final String FAVORITE_COLUMN_MOVIE_ID = "movie_id";
        public static final String FAVORITE_COLUMN_TITLE = "title";
        public static final String FAVORITE_COLUMN_IMAGE = "image";
        public static final String FAVORITE_COLUMN_OVERVIEW = "overview";
        public static final String FAVORITE_COLUMN_RATING = "rating";
        public static final String FAVORITE_COLUMN_DATE = "date";

        private Favorites() {
        }

    }
}
