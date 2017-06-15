package challenge.github.alc.com.popularmoveapp2.networkUtill;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gino Osahon on 12/06/2017.
 */
public class InitRetrofit {

    public static String BASE_URL = "https://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;

    public ApiCallService buildRetrofit(){

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        ApiCallService apiCalls = retrofit.create(ApiCallService.class);

        return apiCalls;
    }
}
