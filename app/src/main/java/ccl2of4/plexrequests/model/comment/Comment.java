package ccl2of4.plexrequests.model.comment;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by connor on 4/3/16.
 */
public class Comment {

    @SerializedName("request_id")
    private String requestId;

    private String content;

    private Date date;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
