package challenge.github.alc.com.popularmoveapp2.networkUtill;

import challenge.github.alc.com.popularmoveapp2.model.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Gino Osahon on 07/06/2017.
 */
public interface ApiCallService {

    @GET("movie/top_rated")
    Call<MovieResponse> getMovies(@Query("api_key") String apiKey, @Query("q") String params);
}
