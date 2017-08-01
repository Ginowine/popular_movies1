package challenge.github.alc.com.popularmoveapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import challenge.github.alc.com.popularmoveapp2.adapter.MovieAdapter;
import challenge.github.alc.com.popularmoveapp2.model.Movie;
import challenge.github.alc.com.popularmoveapp2.model.MovieResponse;
import challenge.github.alc.com.popularmoveapp2.networkUtill.ApiCallService;
import challenge.github.alc.com.popularmoveapp2.networkUtill.InitRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public final static String POPULAR = "popular";
    public final static String RATING = "top_rated";
    public String mSort = POPULAR;
    public String mSorting;

    public static final String API_KEY = "PUT YOUR API KEY HERE";
    public static String BASE_URL = "https://api.themoviedb.org/3/";

    private static Retrofit retrofit = null;
    private RecyclerView recyclerView = null;
    public ProgressBar mProgressBar;
    private LinearLayoutManager mStaggeredLayoutManager;
    public List<Movie> movieList = new ArrayList<Movie>();
    private final String MOVIES_KEY="movieList";
    public Bundle bundle;
    public MovieAdapter nMovieAdapter;
    private static final String BUNDLE_SORTING_KEY = "currentSorting";
    int mAdapterPosition;
    private Parcelable recyclerViewState;
    private LinearLayout layout;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        mStaggeredLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mStaggeredLayoutManager);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        layout = (LinearLayout) findViewById(R.id.main_content);

        SharedPreferences prefs = getSharedPreferences("sort", MODE_PRIVATE);
        editor = prefs.edit();

        if (savedInstanceState != null && savedInstanceState.containsKey("scrollposition")){
            this.movieList = savedInstanceState.getParcelableArrayList(MOVIES_KEY);
            Log.d ("mMovieList", movieList.size() + "");
            mSorting = savedInstanceState.getString(BUNDLE_SORTING_KEY);
            this.mSort = mSorting;
            sendDataToAdapter(movieList);
            mAdapterPosition = savedInstanceState.getInt("adapterposition");
            //mStaggeredLayoutManager.scrollToPositionWithOffset(mAdapterPosition, 0);
            recyclerView.smoothScrollToPosition(mAdapterPosition);
            //recyclerView.scrollToPosition(mAdapterPosition);
        }else {
            retrofitGetDataFromApi(mSort);
        }
//        if (isConnected()){
//            retrofitGetDataFromApi(mSort);
//        }else {
//            Snackbar snackbar = Snackbar.make(layout, "Please turn Internet on", Snackbar.LENGTH_LONG);
//            snackbar.show();
//            //Utility.showToast(getApplicationContext(), "Please turn internet on!");
//        }
    }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        this.movieList = savedInstanceState.getParcelableArrayList(MOVIES_KEY);
//        this.mSorting = savedInstanceState.getString(BUNDLE_SORTING_KEY);
//        this.mSort = mSorting;
//        sendDataToAdapter(movieList);
//        mAdapterPosition = savedInstanceState.getInt("scrollposition");
//        recyclerView.scrollToPosition(mAdapterPosition);
//    }

    //Checking for Connection so we can inflater the Layout and Handling the force Stop caused by no Internet Connection
    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInterface = connectivityManager.getActiveNetworkInfo();
        return networkInterface != null;
    }

    private void sendDataToAdapter(List<Movie> movies){
        nMovieAdapter = new MovieAdapter(movies, R.layout.gridview_layout, getApplicationContext());
        recyclerView.setAdapter(nMovieAdapter);
        nMovieAdapter.swapList(movies);
        //mAdapterPosition = nMovieAdapter.getPosition();
        //recyclerView.setAdapter(new MovieAdapter(movies, R.layout.gridview_layout, getApplicationContext()));
        //this.mProgressBar.setVisibility(View.GONE);
    }

    private void retrofitGetDataFromApi(String sortOrder){
        InitRetrofit initRetrofit = new InitRetrofit();
        ApiCallService apiCalls1 = initRetrofit.buildRetrofit();
        this.mProgressBar.setVisibility(View.VISIBLE);
        //mProgressBar
        Call<MovieResponse> call = apiCalls1.getMovies(sortOrder, API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                movieList = movieResponse.getResults();
                sendDataToAdapter(movieList);
                Log.d(TAG, "Number of movies received: " + movieResponse.size());
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        nMovieAdapter = new MovieAdapter();
        this.movieList = this.nMovieAdapter.getMoviesData();
        if (null != movieList) {
            mAdapterPosition = nMovieAdapter.getPosition();
            outState.putParcelableArrayList(MOVIES_KEY, (ArrayList<? extends Parcelable>) movieList);
            outState.putInt("adapterposition", mAdapterPosition);
            outState.putString(BUNDLE_SORTING_KEY, mSort);
            outState.putInt("scrollposition", mStaggeredLayoutManager.findFirstCompletelyVisibleItemPosition());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ActionBar actionBar = getSupportActionBar();
        int id = item.getItemId();
        switch (id){
            case R.id.action_favorites:
                Intent intent = new Intent(getApplicationContext(), FavouritesActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_sort_by_popularity:
                editor.putString("sort_by", "popular");
                editor.apply();
                item.setChecked(true);

                if (actionBar != null) {
                    actionBar.setTitle("Popular Movies");
                }
                //mSort = POPULAR;
                nMovieAdapter.clearMovieList();
                retrofitGetDataFromApi(POPULAR);
                return true;
            case R.id.action_sort_by_rating:
                //mSort = RATING;
                editor.putString("sort_by", "top_rated");
                editor.apply();

                item.setChecked(true);

                if (actionBar != null) {
                    actionBar.setTitle("Top Rated");
                }
                nMovieAdapter.clearMovieList();
                //clearView();
                retrofitGetDataFromApi(RATING);
               return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @NonNull
    private String getPreference() {
        SharedPreferences shared = getSharedPreferences("sort", MODE_PRIVATE);
        String pref = (shared.getString("sort_by", ""));
        Log.v("Main", "value is: " + pref);
        return pref;
    }

    private void clearView() {
        movieList.clear();
        new MovieAdapter().notifyDataSetChanged();
    }
}