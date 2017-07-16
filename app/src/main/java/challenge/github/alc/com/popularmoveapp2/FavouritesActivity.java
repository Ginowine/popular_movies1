package challenge.github.alc.com.popularmoveapp2;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import challenge.github.alc.com.popularmoveapp2.adapter.FavouritesMoviesAdapter;
import challenge.github.alc.com.popularmoveapp2.data.MovieContract;
import challenge.github.alc.com.popularmoveapp2.model.Movie;

public class FavouritesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String LOG_TAG = FavouritesActivity.class.getSimpleName();
    public static final int LOADER_ID = 0;
    private RecyclerView recyclerView;
    private FavouritesMoviesAdapter favouriteMoviesAdapter;
    private LinearLayoutManager linearLayoutManager;

    //cursor to hold the data queryied from the database
    private Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Favourite Movies");
        recyclerView = (RecyclerView) findViewById(R.id.favorites_recycler_view); //gert reference to the recycler view
        favouriteMoviesAdapter = new FavouritesMoviesAdapter(this); //instantiate the adadpter
        linearLayoutManager = new GridLayoutManager(getApplicationContext(), 2); //instantiate the layout manager
        //favouriteMoviesAdapter.setClickListener(clickListener);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(favouriteMoviesAdapter);

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {
            // Initialize a Cursor, this will hold all the task data

            // onStartLoading() is called when a loader first starts loading data
            @Override
            protected void onStartLoading() {
                if (cursor != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(cursor);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            // loadInBackground() performs asynchronous loading of data
            @Override
            public Cursor loadInBackground() {
                // Will implement to load data
                Cursor cursor;

                try {
                    cursor = getContentResolver().query(MovieContract.Favorites.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);
                    return cursor;

                } catch (Exception e) {
                    Log.e(LOG_TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }



            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(Cursor data) {
                cursor = data;
                //favouriteMoviesAdapter.getFavouriteMoviesDataFromCursor(cursor);
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        favouriteMoviesAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        favouriteMoviesAdapter.swapCursor(null);
    }
}
