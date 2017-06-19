package challenge.github.alc.com.popularmoveapp2.networkUtill;

import challenge.github.alc.com.popularmoveapp2.model.MovieResponse;
import challenge.github.alc.com.popularmoveapp2.model.Review;
import challenge.github.alc.com.popularmoveapp2.model.ReviewResponse;
import challenge.github.alc.com.popularmoveapp2.model.Trailer;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Gino Osahon on 07/06/2017.
 */
public interface ApiCallService {

    @GET("movie/top_rated")
    Call<MovieResponse> getMovies(@Query("api_key") String apiKey, @Query("q") String params);

    @GET("movie/{id}/reviews")
    Call<ReviewResponse> getMovieReviews(@Path("id") String movieId, @Query("q") String param, @Query("api_key") String apiKey);

    @GET("movie/videos")
    Call<Trailer> getMovieTrailer(@Query("q") String params, @Query("api_key") String apiKey, @Query("q") String param);
}
