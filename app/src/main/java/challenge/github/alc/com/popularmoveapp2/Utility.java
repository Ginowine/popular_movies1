package challenge.github.alc.com.popularmoveapp2;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import challenge.github.alc.com.popularmoveapp2.data.MovieContract;

/**
 * Created by Gino Osahon on 25/06/2017.
 */
public abstract class Utility extends Context{

    /**
     * Show toast message
     */
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }



    public static String buildImageUrl(int width, String fileName) {
        return "http://image.tmdb.org/t/p/w" + Integer.toString(width) + fileName;
    }

}
