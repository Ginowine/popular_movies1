package challenge.github.alc.com.popularmoveapp2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import challenge.github.alc.com.popularmoveapp2.adapter.MovieAdapter;
import challenge.github.alc.com.popularmoveapp2.model.MovieResponse;
import challenge.github.alc.com.popularmoveapp2.networkUtill.ApiCallService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String SORT_BY = "popularity.desc";
    public static final String API_KEY = "232c7933fb923517762fbaba80f80ba9";
    public static String BASE_URL = "https://api.themoviedb.org/3";

    private static Retrofit retrofit = null;
    private RecyclerView recyclerView = null;
    public ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        retrofitGetDataFromApi();


    }

    private void sendDataToAdapter(MovieResponse movieResponse){
        recyclerView.setAdapter(new MovieAdapter(movieResponse.getMovies(), R.layout.list_item_view, getApplication()));

    }

    private void retrofitGetDataFromApi(){


        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        ApiCallService apiCalls = retrofit.create(ApiCallService.class);

        Call<MovieResponse> call = apiCalls.getMovies(API_KEY, SORT_BY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                sendDataToAdapter(movieResponse);

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });


    }
}
