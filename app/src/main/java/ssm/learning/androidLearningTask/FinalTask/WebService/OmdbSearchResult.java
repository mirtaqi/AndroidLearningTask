
package ssm.learning.androidLearningTask.FinalTask.WebService;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OmdbSearchResult {

    @SerializedName("Search")
    @Expose
    private List<MovieInfo> movieInfos = null;
    @SerializedName("totalResults")
    @Expose
    private String totalResults;
    @SerializedName("Response")
    @Expose
    private String response;
    @SerializedName("Error")
    @Expose
    private String error;


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<MovieInfo> getMovieInfos() {
        return movieInfos;
    }

    public void setMovieInfos(List<MovieInfo> movieInfos) {
        this.movieInfos = movieInfos;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

}
