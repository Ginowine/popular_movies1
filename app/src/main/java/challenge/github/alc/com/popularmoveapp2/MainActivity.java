package challenge.github.alc.com.popularmoveapp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;

import challenge.github.alc.com.popularmoveapp2.adapter.MovieAdapter;
import challenge.github.alc.com.popularmoveapp2.model.MovieResponse;
import challenge.github.alc.com.popularmoveapp2.networkUtill.ApiCallService;
import challenge.github.alc.com.popularmoveapp2.networkUtill.InitRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
   // private static final String SORT_BY = "popularity.desc";
    public final static String POPULAR = "popular";
    public final static String RATING = "top_rated";
    public static final String API_KEY = "232c7933fb923517762fbaba80f80ba9";
    public static String BASE_URL = "https://api.themoviedb.org/3/";

    private static Retrofit retrofit = null;
    private RecyclerView recyclerView = null;
    public ProgressBar mProgressBar;
    private LinearLayoutManager mStaggeredLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        mStaggeredLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mStaggeredLayoutManager);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        retrofitGetDataFromApi(RATING);
    }

    private void sendDataToAdapter(MovieResponse movieResponse){
        recyclerView.setAdapter(new MovieAdapter(movieResponse.getResults(), R.layout.gridview_layout, getApplicationContext()));
    }

    private void retrofitGetDataFromApi(String sortOrder){
        InitRetrofit initRetrofit = new InitRetrofit();
        ApiCallService apiCalls1 = initRetrofit.buildRetrofit();
        Call<MovieResponse> call = apiCalls1.getMovies(API_KEY, sortOrder);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                sendDataToAdapter(movieResponse);

                Log.d(TAG, "Number of movies received: " + movieResponse.size());
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_favorites:
                Intent intent = new Intent(getApplicationContext(), FavouritesActivity.class);
                startActivity(intent);
                break;
            case R.id.action_sort_by_popularity:
                retrofitGetDataFromApi(RATING);
                break;
            case R.id.action_sort_by_rating:
                retrofitGetDataFromApi(POPULAR);
                break;
        }
        return true;
    }
}