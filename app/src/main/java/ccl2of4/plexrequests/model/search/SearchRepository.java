package ccl2of4.plexrequests.model.search;

import java.util.List;

import ccl2of4.plexrequests.model.request.Request;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by connor on 4/3/16.
 */
public interface SearchRepository {

    @GET("moviesearch")
    Call<List<Request>> searchMovies(@Query("query") String query);

    @GET("tvsearch")
    Call<List<Request>> searchTV(@Query("query") String query);

}
