package xyz.udacitypopularmoviesi.Interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import xyz.udacitypopularmoviesi.Models.MovieListResponseBean;

/**
 * Created by vikra on 8/27/2016.
 */
public interface ApiInterface {
    @GET("movie/top_rated")
    Call<MovieListResponseBean> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MovieListResponseBean> getPopularMovies(@Query("api_key") String apiKey);
}