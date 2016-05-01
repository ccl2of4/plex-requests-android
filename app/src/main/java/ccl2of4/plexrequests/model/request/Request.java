package ccl2of4.plexrequests.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ccl2of4.plexrequests.model.comment.Comment;

/**
 * Created by connor on 4/3/16.
 */
public class Request {

    @SerializedName("request_id")
    @Expose(serialize = false)
    private String requestId;
    private String type;
    private String name;
    private String date;

    @Expose(serialize = false)
    private List<Comment> comments;

    @Expose(serialize = false)
    private String summary;

    @SerializedName("poster_path")
    private String posterPath;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isMovie() {
        return "movie".equalsIgnoreCase(getType());
    }

}
