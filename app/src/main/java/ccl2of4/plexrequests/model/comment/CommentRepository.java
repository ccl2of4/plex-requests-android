package ccl2of4.plexrequests.model.comment;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by connor on 4/3/16.
 */
public interface CommentRepository {

    @POST("requests/{requestId}/comments")
    public void addComment(@Path("requestId") String requestId, @Body Comment comment);

    @DELETE("requests/{requestId}/comments/{commentId}")
    public void deleteComment(@Path("commentId") String commentId);

}
