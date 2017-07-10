package challenge.github.alc.com.popularmoveapp2.networkUtill;

import challenge.github.alc.com.popularmoveapp2.model.MovieResponse;
import challenge.github.alc.com.popularmoveapp2.model.ReviewResponse;
import challenge.github.alc.com.popularmoveapp2.model.VideoResponse;
import challenge.github.alc.com.popularmoveapp2.model.Videos;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Gino Osahon on 07/06/2017.
 */
public interface ApiCallService {

    @GET("movie/{sort}")
    Call<MovieResponse> getMovies(@Path("sort") String params, @Query("api_key") String key);

    @GET("movie/{id}/reviews")
    Call<ReviewResponse> getMovieReviews(@Path("id") String movieId, @Query("q") String params, @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<VideoResponse> getMovieTrailer(@Path("id") String movieId, @Query("q") String param, @Query("api_key") String apiKey);
}
