package challenge.github.alc.com.popularmoveapp2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import challenge.github.alc.com.popularmoveapp2.adapter.ReviewAdapter;
import challenge.github.alc.com.popularmoveapp2.adapter.TrailerAdapter;
import challenge.github.alc.com.popularmoveapp2.model.Movie;
import challenge.github.alc.com.popularmoveapp2.model.Review;
import challenge.github.alc.com.popularmoveapp2.model.ReviewResponse;
import challenge.github.alc.com.popularmoveapp2.model.Trailer;
import challenge.github.alc.com.popularmoveapp2.networkUtill.ApiCallService;
import challenge.github.alc.com.popularmoveapp2.networkUtill.InitRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.linearlistview.LinearListView;

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

    private ScrollView mDetailLayout;


    private String mOverview;
    private String mReleaseDate;
    private String mTitle;
    private String mPostalPath;
    private Long mRating;
    private String mBackDrop;
    private int mMovie_id;
    private Context context;

    private Trailer trailer;
    private Review review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //cab = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        //cab.setTitleEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        imagePoster = (ImageView) findViewById(R.id.detail_image);
        overview = (TextView) findViewById(R.id.movie_overview);
        release_date_txt = (TextView) findViewById(R.id.release_date);
        rating_txt = (TextView) findViewById(R.id.ratings);
        movie_title = (TextView) findViewById(R.id.movie_title);

        mReviewsCardview = (CardView) findViewById(R.id.detail_reviews_cardview);
        mTrailersCardview = (CardView) findViewById(R.id.detail_trailers_cardview);
        mDetailLayout = (ScrollView) findViewById(R.id.detail_layout);

        mTrailersView = (LinearListView) findViewById(R.id.detail_trailers);
        mReviewsView = (LinearListView) findViewById(R.id.detail_reviews);

        initRetrofit = new InitRetrofit();
        movie = getIntent().getParcelableExtra("movie");

        if (movie != null){
            mDetailLayout.setVisibility(View.VISIBLE);
            mOverview = movie.getOverview();
            mReleaseDate = movie.getReleaseDate();
            mTitle = movie.getTitle();
            mBackDrop = movie.getBackdropPath();
            mPostalPath = movie.getPosterPath();
            mRating = movie.getRating();
            mMovie_id = movie.getId();

            displayDetails(mOverview, mReleaseDate, mTitle, mPostalPath, mRating);
        }else {
            mDetailLayout.setVisibility(View.INVISIBLE);
        }
    }

    public void displayDetails(String overview, String releaseDate, String title, String postal, Long rating ){

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

        Call<ReviewResponse> call = apiCalls2.getMovieReviews(String.valueOf(movie_id) ,REVIEW, API_KEY);

        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
              // List<Review> reviews = response.body();
                ReviewResponse reviewResponse = response.body();
                //review = response.body();
                passDataToAdapter(reviewResponse);

                Log.d(TAG, "Number of movies received: " + reviewResponse.size());
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

    private void getTrailerFromAPI(long movieId){
        ApiCallService apiCallService = initRetrofit.buildRetrofit();

        Call<Trailer> call = apiCallService.getMovieTrailer(String.valueOf(movieId), TRAILERS, API_KEY );
        call.enqueue(new Callback<Trailer>() {
            @Override
            public void onResponse(Call<Trailer> call, Response<Trailer> response) {
                trailer = response.body();
                passTrailerToAdapter(trailer);
            }

            @Override
            public void onFailure(Call<Trailer> call, Throwable t) {

            }
        });
    }

    private void passTrailerToAdapter(final Trailer trailer) {
        if (trailer != null){
            mTrailersCardview.setVisibility(View.VISIBLE);
            mTrailerAdapter = new TrailerAdapter(context,trailer);
            mTrailersView.setAdapter(mTrailerAdapter);

            mTrailersView.setOnItemClickListener(new LinearListView.OnItemClickListener(){
                @Override
                public void onItemClick(LinearListView parent, View view, int position, long id) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (movie != null){
            getReviewsFromAPI(movie.getId());
            getTrailerFromAPI(movie.getId());
        }
    }
}
