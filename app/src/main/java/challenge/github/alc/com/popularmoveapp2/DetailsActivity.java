package challenge.github.alc.com.popularmoveapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import challenge.github.alc.com.popularmoveapp2.adapter.ReviewAdapter;
//import challenge.github.alc.com.popularmoveapp2.adapter.TrailerAdapter;
import challenge.github.alc.com.popularmoveapp2.adapter.TrailerAdapter;
import challenge.github.alc.com.popularmoveapp2.model.Movie;
import challenge.github.alc.com.popularmoveapp2.model.Review;
import challenge.github.alc.com.popularmoveapp2.model.ReviewResponse;
import challenge.github.alc.com.popularmoveapp2.model.VideoResponse;
import challenge.github.alc.com.popularmoveapp2.model.Videos;
import challenge.github.alc.com.popularmoveapp2.networkUtill.ApiCallService;
import challenge.github.alc.com.popularmoveapp2.networkUtill.InitRetrofit;
import challenge.github.alc.com.popularmoveapp2.sync.FavoritesFunctionalities;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.linearlistview.LinearListView;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = DetailsActivity.class.getSimpleName();
    public static final String IMAGE_URL_BASE_PATH="http://image.tmdb.org/t/p/w342//";

    private static final String REVIEW = "reviews";
    private static final String TRAILERS = "videos";
    public static final String API_KEY = "232c7933fb923517762fbaba80f80ba9";

    private TextView overview, release_date_txt, rating_txt, movie_title;
    //private Uri movie_id;
    private CollapsingToolbarLayout cab;
    private boolean isCollapse = true;

    private ImageView imagePoster;

    private Movie movie;
    public ReviewAdapter mReviewAdapter;
    public TrailerAdapter mTrailerAdapter;
    InitRetrofit initRetrofit;

    private CardView mReviewsCardview;
    private CardView mTrailersCardview;

    private LinearListView mTrailersView;
    private LinearListView mReviewsView;

    private NestedScrollView mDetailLayout;


    private String mOverview;
    private String mReleaseDate;
    private String mTitle;
    private String mPostalPath;
    private double mRating;
    private int mMovie_id;
    private Context context;
    private ShareActionProvider mShareActionProvider;
    private VideoResponse videoResponse;
    private FloatingActionButton fab;
    private boolean check;

    //List<Videos> mVideo;

    private ProgressBar mLoadingIndicator;
    public List<Videos> videoList = new ArrayList<Videos>();

    //private Trailer trailer;

    Videos mVideo = new Videos();
    FavoritesFunctionalities favoritesService;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = getApplicationContext();
        //cab = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        //cab.setTitleEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        favoritesService = new FavoritesFunctionalities(getApplicationContext());

        imagePoster = (ImageView) findViewById(R.id.detail_image);
        overview = (TextView) findViewById(R.id.movie_overview);
        release_date_txt = (TextView) findViewById(R.id.release_date);
        rating_txt = (TextView) findViewById(R.id.ratings);
        movie_title = (TextView) findViewById(R.id.movie_title);

        mReviewsCardview = (CardView) findViewById(R.id.detail_reviews_cardview);
        mTrailersCardview = (CardView) findViewById(R.id.detail_trailers_cardview);
        mDetailLayout = (NestedScrollView) findViewById(R.id.detail_layout);

        mTrailersView = (LinearListView) findViewById(R.id.detail_trailers);
        mReviewsView = (LinearListView) findViewById(R.id.detail_reviews);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.detail_collapsing_toolbar);

        initRetrofit = new InitRetrofit();


        if (getIntent().hasExtra(Movie.MOVIE_FAVOURITE)){
            bundle = getIntent().getBundleExtra(Movie.MOVIE_FAVOURITE);
            mMovie_id = bundle.getInt(Movie.MOVIE_ID);
            getReviewsFromAPI(mMovie_id);
            getTrailerFromAPI(mMovie_id);
        }
        else {
            bundle = getIntent().getBundleExtra(Movie.BUNDLE);
        }

        if (bundle != null){
            mDetailLayout.setVisibility(View.VISIBLE);
            if (bundle.containsKey(Movie.MOVIE_TITLE)){
                mTitle = bundle.getString(Movie.MOVIE_TITLE);
                getSupportActionBar().setTitle(mTitle);
            }
            if (bundle.containsKey(Movie.MOVIE_OVERVIEW)){
                mOverview = bundle.getString(Movie.MOVIE_OVERVIEW);
            }
            if (bundle.containsKey(Movie.MOVIE_RATING)){
                mRating = bundle.getDouble(Movie.MOVIE_RATING);
            }
            if (bundle.containsKey(Movie.MOVIE_RELEASE_DATE)){
                mReleaseDate = bundle.getString(Movie.MOVIE_RELEASE_DATE);
            }
            if (bundle.containsKey(Movie.POSTER_URL)){
                mPostalPath = bundle.getString(Movie.POSTER_URL);
            }
            if (bundle.containsKey(Movie.MOVIE_ID)){
                mMovie_id = bundle.getInt(Movie.MOVIE_ID);
            }
            if (bundle.containsKey(Movie.MOVIE_FAVOURITE)){
                check = bundle.getBoolean(Movie.MOVIE_FAVOURITE);
            }

            getReviewsFromAPI(mMovie_id);
            getTrailerFromAPI(mMovie_id);

            displayDetails(mOverview, mReleaseDate, mTitle, mPostalPath, mRating);
        }

        setupSharedPreferences();
        setupFloatingActionButton();
    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // COMPLETED (3) Get the value of the show_bass checkbox preference and use it to call setShowBass
        boolean isFavorite = sharedPreferences.getBoolean("add_to_favorite", true);
    }

    /**
     * Setup the floating button of add to favourite
     */
    private void setupFloatingActionButton() {
        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favoritesService.isFavorite(bundle)) {
                    favoritesService.removeFromFavorites(bundle);
                        Utility.showToast(getApplicationContext(), "Removed from Favourite!");
                    fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_thumb_up_white_24dp));
                } else {
                    favoritesService.addToFavorites(bundle);
                        Utility.showToast(getApplicationContext(), "Added to Favourite!");
                        fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_thumb_down_white_24dp));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        if (movie != null){
            inflater.inflate(R.menu.menu_detail, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_share:
                createShareMovieIntent();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    private Intent createShareMovieIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, movie.getTitle() + " " +
                "http://www.youtube.com/watch?v=" + mVideo.getKey());
        return shareIntent;
    }

    public void displayDetails(String overview, String releaseDate, String title, String postal, double rating ){

        this.overview.setText(overview);
        this.movie_title.setText(title);
        this.release_date_txt.setText(releaseDate);
        this.rating_txt.setText(String.valueOf(rating + " *"));


        String image_url = IMAGE_URL_BASE_PATH + postal;

        Picasso.with(context)
                .load(image_url)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(imagePoster);

    }

    private void getReviewsFromAPI(int movie_id){

        ApiCallService apiCalls2 = initRetrofit.buildRetrofit();
        mLoadingIndicator.setVisibility(View.VISIBLE);

        Call<ReviewResponse> call = apiCalls2.getMovieReviews(String.valueOf(movie_id) , REVIEW, API_KEY);

        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
              // List<Review> reviews = response.body();
                ReviewResponse reviewResponse = response.body();
                //review = response.body();
                passDataToAdapter(reviewResponse);
                mLoadingIndicator.setVisibility(View.INVISIBLE);
                Log.d(TAG, "Number of Reviews received: " + reviewResponse.size());
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {

            }
        });
    }

    private void passDataToAdapter(ReviewResponse reviewResponse) {
        if (reviewResponse != null){
            mReviewsCardview.setVisibility(View.VISIBLE);
            mReviewAdapter = new ReviewAdapter(getApplicationContext(), reviewResponse.getResults());
            mReviewsView.setAdapter(mReviewAdapter);
        }
    }

    private void getTrailerFromAPI(int movieId){
        ApiCallService apiCallService = initRetrofit.buildRetrofit();

        Call<VideoResponse> call = apiCallService.getMovieTrailer(String.valueOf(movieId), TRAILERS, API_KEY );
        call.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                videoResponse = response.body();
                passTrailerToAdapter(videoResponse);

                Log.d(TAG, "Number of Trailers received: " + videoResponse.size());
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {

            }
        });
    }

    private void passTrailerToAdapter(final VideoResponse response) {
        if (response != null){
            mTrailersCardview.setVisibility(View.VISIBLE);
            mTrailerAdapter = new TrailerAdapter(getApplicationContext(),response.getResults());
            //videoList = response.getResults();
            mTrailersView.setAdapter(mTrailerAdapter);

            //final Videos videos = videoList.get()

//            mTrailersView.setOnItemClickListener(new LinearListView.OnItemClickListener(){
//                @Override
//                public void onItemClick(LinearListView parent, View view, int position, long id) {
//                    Uri imageVideoLink
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse("http://www.youtube.com/watch?v=" + videoList));
//                    startActivity(intent);
//                }
//            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
