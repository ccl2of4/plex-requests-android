package ccl2of4.plexrequests.model.request;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RequestService {

    @GET("requests")
    public Call<List<Request>> getRequests();

    @POST("requests")
    public Call<Void> addRequest(@Body Request request);

    @DELETE("requests/{requestId}")
    public Call<Void> deleteRequest(@Path("requestId") String requestId);

}
