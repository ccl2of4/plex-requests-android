package ccl2of4.plexrequests.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by connor on 4/3/16.
 */
public class Request {

    private String type;
    private String name;
    private String date;
    private String summary;

    @SerializedName("poster_path")
    private String posterPath;

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


}
